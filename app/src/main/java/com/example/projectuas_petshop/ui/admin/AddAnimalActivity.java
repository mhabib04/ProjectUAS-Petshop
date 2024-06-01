package com.example.projectuas_petshop.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityAddAnimalBinding;
import com.example.projectuas_petshop.model.insertAnimal.DataAnimal;
import com.example.projectuas_petshop.model.insertAnimal.InsertAnimal;
import com.example.projectuas_petshop.model.register.Register;
import com.example.projectuas_petshop.ui.LoginActivity;
import com.example.projectuas_petshop.ui.RegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAnimalActivity extends AppCompatActivity {

    private ActivityAddAnimalBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAnimalBinding.inflate(getLayoutInflater());
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
                    Toast.makeText(AddAnimalActivity.this, "Selesaikan pengisian", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    addAnimal(selectType, breed, price, age);
                }

            }
        });

    }

    private void addAnimal(String type, String breed, int price, int age) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<InsertAnimal> insertAnimalCall = apiInterface.insertAnimalResponse(type,breed, price, age);
        insertAnimalCall.enqueue(new Callback<InsertAnimal>() {
            @Override
            public void onResponse(@NonNull Call<InsertAnimal> call, @NonNull Response<InsertAnimal> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(AddAnimalActivity.this, "Data berhasil ditambah", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(AddAnimalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAnimalActivity.this, ListAnimalAdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String message = response.body().getMessage();
                    Toast.makeText(AddAnimalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<InsertAnimal> call, @NonNull Throwable t) {
                Toast.makeText(AddAnimalActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}