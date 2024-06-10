package com.example.projectuas_petshop.ui.user.accessories;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityBuyAccessoriesBinding;
import com.example.projectuas_petshop.model.select.getAccessories.GetAccessories;
import com.example.projectuas_petshop.model.select.getAccessories.GetAccessoriesData;
import com.example.projectuas_petshop.ui.user.SuccesBuyActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyAccessoriesActivity extends AppCompatActivity {
    private ActivityBuyAccessoriesBinding binding;
    ApiInterface apiInterface;
    int id_accessories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyAccessoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id_accessories = getIntent().getIntExtra("id_accessories", 0);

        getData(id_accessories);

        binding.buttonBuy.setOnClickListener( v-> {
            Intent intent = new Intent(this, SuccesBuyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        });
    }

    public void getData(int id_accessories) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetAccessories> call = apiInterface.getAccessories(id_accessories);
        call.enqueue(new Callback<GetAccessories>() {
            @Override
            @SuppressLint("SetTextI18n")
            public void onResponse(Call<GetAccessories> call, Response<GetAccessories> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    GetAccessories getAccessories = response.body();
                    List<GetAccessoriesData> data = getAccessories.getData();
                    binding.txtNameAccessoriesBuy.setText(data.get(0).getName());
                    byte[] imageBytes = Base64.decode(data.get(0).getImage().substring(data.get(0).getImage().indexOf(",") + 1), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    binding.imgAccessoriesBuy.setImageBitmap(bitmap);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    int price = data.get(0).getPrice();
                    binding.txtPriceAccessoriesBuy.setText(formatRupiah.format((double)price));
                } else {
                    Toast.makeText(BuyAccessoriesActivity.this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAccessories> call, Throwable t) {
                Toast.makeText(BuyAccessoriesActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}