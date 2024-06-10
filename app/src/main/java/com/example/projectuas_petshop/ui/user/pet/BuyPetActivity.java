package com.example.projectuas_petshop.ui.user.pet;

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
import com.example.projectuas_petshop.databinding.ActivityBuyPetBinding;
import com.example.projectuas_petshop.model.select.getPet.GetPet;
import com.example.projectuas_petshop.model.select.getPet.GetPetData;
import com.example.projectuas_petshop.ui.LoginActivity;
import com.example.projectuas_petshop.ui.user.ProfileUserActivity;
import com.example.projectuas_petshop.ui.user.SuccesBuyActivity;

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

        setSupportActionBar(binding.toolbarBuyPet);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id_pet = getIntent().getIntExtra("id_pet", 0);

        getData(id_pet);

        binding.buttonBuy.setOnClickListener( v-> {
            Intent intent = new Intent(this, SuccesBuyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        });

    }

    public void getData(int id_pet) {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetPet> call = apiInterface.getPet(id_pet);
        call.enqueue(new Callback<GetPet>() {
            @Override
            @SuppressLint("SetTextI18n")
            public void onResponse(Call<GetPet> call, Response<GetPet> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    GetPet getPet = response.body();
                    List<GetPetData> data = getPet.getData();
                    String type = data.get(0).getType();
                    switch (type) {
                        case "Cat":
                            binding.txtTypePetBuy.setText(getString(R.string.cat));
                            break;
                        case "Dog":
                            binding.txtTypePetBuy.setText(getString(R.string.dog));
                            break;
                        case "Bird":
                            binding.txtTypePetBuy.setText(getString(R.string.bird));
                            break;
                        case "Fish":
                            binding.txtTypePetBuy.setText(getString(R.string.fish));
                            break;
                    }
                    binding.txtBreedPetBuy.setText(data.get(0).getBreed());
                    byte[] imageBytes = Base64.decode(data.get(0).getImage().substring(data.get(0).getImage().indexOf(",") + 1), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    binding.imgPetBuy.setImageBitmap(bitmap);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    int price = data.get(0).getPrice();
                    binding.txtPricePetBuy.setText(formatRupiah.format((double)price));
                    binding.txtAgePetBuy.setText(data.get(0).getAge() + " " + getString(R.string.month));
                } else {
                    Toast.makeText(BuyPetActivity.this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPet> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(BuyPetActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}