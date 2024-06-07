package com.example.projectuas_petshop.ui.user.pet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityBuyPetBinding;
import com.example.projectuas_petshop.model.select.getPet.GetPet;
import com.example.projectuas_petshop.model.select.getPet.GetPetData;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyPetActivity extends AppCompatActivity {
    private ActivityBuyPetBinding binding;
    ApiInterface apiInterface;
    int id_pet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyPetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id_pet = getIntent().getIntExtra("id_pet", 0);

        getData(id_pet);

        binding.buttonBuy.setOnClickListener( v-> {
            Toast.makeText(BuyPetActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
        });

    }

    public void getData(int id_pet) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetPet> call = apiInterface.getPet(id_pet);
        call.enqueue(new Callback<GetPet>() {
            @Override
            @SuppressLint("SetTextI18n")
            public void onResponse(Call<GetPet> call, Response<GetPet> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    GetPet getPet = response.body();
                    List<GetPetData> data = getPet.getData();
                    binding.txtTypePet.setText(data.get(0).getType());
                    binding.txtBreedPet.setText(data.get(0).getBreed());
                    byte[] imageBytes = Base64.decode(data.get(0).getImage().substring(data.get(0).getImage().indexOf(",") + 1), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    binding.imgPet.setImageBitmap(bitmap);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    int price = data.get(0).getPrice();
                    binding.txtPricePet.setText(": " + formatRupiah.format((double)price));
                    binding.txtAgePet.setText(": " + data.get(0).getAge() + " Month");
                } else {
                    Toast.makeText(BuyPetActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPet> call, Throwable t) {
                Toast.makeText(BuyPetActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}