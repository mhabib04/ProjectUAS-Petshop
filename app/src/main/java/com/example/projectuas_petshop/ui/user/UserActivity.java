package com.example.projectuas_petshop.ui.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.databinding.ActivityUserBinding;
import com.example.projectuas_petshop.ui.user.food.ListFoodUserActivity;
import com.example.projectuas_petshop.ui.user.pet.ListPetUserActivity;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCats.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListPetUserActivity.class);
            intent.putExtra("category", "Cat");
            startActivity(intent);
        });

        binding.btnDogs.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListPetUserActivity.class);
            intent.putExtra("category", "Dog");
            startActivity(intent);
        });

        binding.btnBirds.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListPetUserActivity.class);
            intent.putExtra("category", "Bird");
            startActivity(intent);
        });

        binding.btnFishs.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListPetUserActivity.class);
            intent.putExtra("category", "Fish");
            startActivity(intent);
        });

        binding.btnFoodCat.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListFoodUserActivity.class);
            intent.putExtra("category", "Cat");
            startActivity(intent);
        });

        binding.btnFoodDog.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListFoodUserActivity.class);
            intent.putExtra("category", "Dog");
            startActivity(intent);
        });

        binding.btnFoodBird.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListFoodUserActivity.class);
            intent.putExtra("category", "Bird");
            startActivity(intent);
        });

        binding.btnFoodFish.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListFoodUserActivity.class);
            intent.putExtra("category", "Fish");
            startActivity(intent);
        });

    }
}