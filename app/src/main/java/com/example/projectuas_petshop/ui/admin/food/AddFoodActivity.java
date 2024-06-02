package com.example.projectuas_petshop.ui.admin.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityAddFoodBinding;
import com.example.projectuas_petshop.model.insertFood.FoodInsert;
import com.example.projectuas_petshop.model.insertPet.PetInsert;
import com.example.projectuas_petshop.ui.admin.pet.AddPetActivity;
import com.example.projectuas_petshop.ui.admin.pet.ListPetAdminActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodActivity extends AppCompatActivity {

    private ActivityAddFoodBinding binding;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectType = binding.autoCompleteText.getText().toString().trim();
                String breed = binding.etName.getText().toString().trim();
                String priceString = binding.etPrice.getText().toString().trim();
                int price = Integer.parseInt(priceString);
                if (binding.autoCompleteText.getText().toString().isEmpty() || breed.isEmpty() || priceString.isEmpty()){
                    Toast.makeText(AddFoodActivity.this, "Selesaikan pengisian", Toast.LENGTH_SHORT).show();
                } else {
                    addFood(selectType, breed, price);
                }

            }
        });
    }

    private void addFood(String type, String name, int price) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FoodInsert> insertFoodCall = apiInterface.insertFoodResponse(type,name, price);
        insertFoodCall.enqueue(new Callback<FoodInsert>() {
            @Override
            public void onResponse(@NonNull Call<FoodInsert> call, @NonNull Response<FoodInsert> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(AddFoodActivity.this, "Data berhasil ditambah", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(AddAnimalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddFoodActivity.this, ListPetAdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddFoodActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FoodInsert> call, @NonNull Throwable t) {
                Toast.makeText(AddFoodActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}