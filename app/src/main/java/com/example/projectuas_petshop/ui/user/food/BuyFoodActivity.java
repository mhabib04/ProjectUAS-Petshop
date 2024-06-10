package com.example.projectuas_petshop.ui.user.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityBuyFoodBinding;
import com.example.projectuas_petshop.model.select.getFood.GetFood;
import com.example.projectuas_petshop.model.select.getFood.GetFoodData;
import com.example.projectuas_petshop.ui.user.SuccesBuyActivity;

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

        binding.buttonBuy.setOnClickListener( v-> {
            Intent intent = new Intent(this, SuccesBuyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        });
    }

    public void getData(int id_food) {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetFood> call = apiInterface.getFood(id_food);
        call.enqueue(new Callback<GetFood>() {
            @Override
            @SuppressLint("SetTextI18n")
            public void onResponse(Call<GetFood> call, Response<GetFood> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    GetFood getFood = response.body();
                    List<GetFoodData> data = getFood.getData();
                    binding.txtNameFoodBuy.setText(data.get(0).getName());
                    byte[] imageBytes = Base64.decode(data.get(0).getImage().substring(data.get(0).getImage().indexOf(",") + 1), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    binding.imgFoodBuy.setImageBitmap(bitmap);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    int price = data.get(0).getPrice();
                    binding.txtPriceFoodBuy.setText(formatRupiah.format((double)price));
                } else {
                    Toast.makeText(BuyFoodActivity.this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFood> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(BuyFoodActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}