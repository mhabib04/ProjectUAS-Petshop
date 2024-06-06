package com.example.projectuas_petshop.ui.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.databinding.ActivityUserBinding;
import com.example.projectuas_petshop.ui.LoginActivity;
import com.example.projectuas_petshop.ui.SessionManager;
import com.example.projectuas_petshop.ui.admin.AdminActivity;
import com.example.projectuas_petshop.ui.user.food.ListFoodUserActivity;
import com.example.projectuas_petshop.ui.user.pet.ListPetUserActivity;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

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

        binding.imgProfile.setOnClickListener(v -> {
            sessionManager.logoutSession();
            moveToLogin();
        });

    }

    private void moveToLogin() {
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}