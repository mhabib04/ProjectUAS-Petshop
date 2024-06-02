package com.example.projectuas_petshop.ui.admin.pet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityAddPetBinding;
import com.example.projectuas_petshop.model.insert.insertPet.PetInsert;

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

                if (binding.autoCompleteText.getText().toString().isEmpty() || breed.isEmpty() || priceString.isEmpty() || ageString.isEmpty()){
                    Toast.makeText(AddPetActivity.this, "Selesaikan pengisian", Toast.LENGTH_SHORT).show();
                } else {
                    int price = Integer.parseInt(priceString);
                    int age = Integer.parseInt(ageString);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPetActivity.this);
                    builder.setTitle("Sukses");
                    builder.setMessage("Data Berhasil Disimpan");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveToList();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
    private void moveToList() {
        Intent intent = new Intent(AddPetActivity.this, ListPetAdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}