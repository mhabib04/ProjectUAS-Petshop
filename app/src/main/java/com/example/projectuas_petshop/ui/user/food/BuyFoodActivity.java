package com.example.projectuas_petshop.ui.user.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityBuyFoodBinding;
import com.example.projectuas_petshop.model.adapter.AdapterListFoodUser;
import com.example.projectuas_petshop.model.select.getFood.GetFood;
import com.example.projectuas_petshop.model.select.getFood.GetFoodData;
import com.example.projectuas_petshop.model.select.getPet.GetPet;
import com.example.projectuas_petshop.model.select.getPet.GetPetData;
import com.example.projectuas_petshop.model.select.selectFoodByType.FoodDataSelectByType;
import com.example.projectuas_petshop.model.select.selectFoodByType.FoodSelectByType;
import com.example.projectuas_petshop.model.transaction.Transaction;
import com.example.projectuas_petshop.ui.SessionManager;
import com.example.projectuas_petshop.ui.user.pet.BuyPetActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyFoodActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private ActivityBuyFoodBinding binding;
    ApiInterface apiInterface;
    int id_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id_food = getIntent().getIntExtra("id_food", 0);

        getData(id_food);

        sessionManager = new SessionManager(this);
        HashMap<String, String> userDetail = sessionManager.getUserDetail();
        String id_user = userDetail.get(SessionManager.ID_USER);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String transactionDate = sdf.format(new Date());

        binding.buttonBuy.setOnClickListener(v -> {
            transactionBuy(Integer.parseInt(id_user), id_food, transactionDate);
        });
    }

    public void getData(int id_food) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetFood> call = apiInterface.getFood(id_food);
        call.enqueue(new Callback<GetFood>() {
            @Override
            @SuppressLint("SetTextI18n")
            public void onResponse(Call<GetFood> call, Response<GetFood> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    GetFood getFood = response.body();
                    List<GetFoodData> data = getFood.getData();
                    binding.txtTypeFood.setText(data.get(0).getType());
                    binding.txtNameFood.setText(data.get(0).getName());
                    byte[] imageBytes = Base64.decode(data.get(0).getImage().substring(data.get(0).getImage().indexOf(",") + 1), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    binding.imgFood.setImageBitmap(bitmap);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    int price = data.get(0).getPrice();
                    binding.txtPriceFood.setText(": " + formatRupiah.format((double)price));
                } else {
                    Toast.makeText(BuyFoodActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFood> call, Throwable t) {
                Toast.makeText(BuyFoodActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void transactionBuy(int id_user, int id_food, String date){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Transaction> call = apiInterface.transaction(id_user, null, id_food, date);
        call.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    Toast.makeText(BuyFoodActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BuyFoodActivity.this, "gagal cok", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Toast.makeText(BuyFoodActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}