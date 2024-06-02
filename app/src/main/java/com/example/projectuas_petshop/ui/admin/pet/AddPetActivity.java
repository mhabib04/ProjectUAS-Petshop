package com.example.projectuas_petshop.ui.admin.pet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityAddPetBinding;
import com.example.projectuas_petshop.model.insertPet.PetInsert;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPetActivity extends AppCompatActivity {

    private ActivityAddPetBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectType = binding.autoCompleteText.getText().toString().trim();
                String breed = binding.etBreed.getText().toString().trim();
                String priceString = binding.etPrice.getText().toString().trim();
                String ageString = binding.etAge.getText().toString().trim();
                int price = Integer.parseInt(priceString);
                int age = Integer.parseInt(ageString);
                if (binding.autoCompleteText.getText().toString().isEmpty() || breed.isEmpty() || priceString.isEmpty() || ageString.isEmpty()){
                    Toast.makeText(AddPetActivity.this, "Selesaikan pengisian", Toast.LENGTH_SHORT).show();
                } else {
                    addPet(selectType, breed, price, age);
                }

            }
        });

    }

    private void addPet(String type, String breed, int price, int age) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PetInsert> insertAnimalCall = apiInterface.insertPetResponse(type,breed, price, age);
        insertAnimalCall.enqueue(new Callback<PetInsert>() {
            @Override
            public void onResponse(@NonNull Call<PetInsert> call, @NonNull Response<PetInsert> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(AddPetActivity.this, "Data berhasil ditambah", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(AddAnimalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPetActivity.this, ListPetAdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddPetActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PetInsert> call, @NonNull Throwable t) {
                Toast.makeText(AddPetActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}