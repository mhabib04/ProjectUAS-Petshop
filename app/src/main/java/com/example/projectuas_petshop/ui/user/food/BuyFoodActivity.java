package com.example.projectuas_petshop.ui.user.food;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityBuyFoodBinding;
import com.example.projectuas_petshop.model.select.getFood.GetFood;
import com.example.projectuas_petshop.model.select.getFood.GetFoodData;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyFoodActivity extends AppCompatActivity {
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

        binding.buttonBuy.setOnClickListener(v -> {
            Toast.makeText(BuyFoodActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
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

}