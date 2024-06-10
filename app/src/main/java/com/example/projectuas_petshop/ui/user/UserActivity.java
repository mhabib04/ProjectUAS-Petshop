package com.example.projectuas_petshop.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.databinding.ActivityUserBinding;
import com.example.projectuas_petshop.model.SessionManager;
import com.example.projectuas_petshop.ui.user.accessories.ListAccessoriesUserActivity;
import com.example.projectuas_petshop.ui.user.food.ListFoodUserActivity;
import com.example.projectuas_petshop.ui.user.pet.ListPetUserActivity;

import java.util.HashMap;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        HashMap<String, String> userDetail = sessionManager.getUserDetail();
        String image = userDetail.get(SessionManager.IMAGE);
        if (image != null && !image.isEmpty()) {
            byte[] imageBytes = Base64.decode(image.substring(image.indexOf(",") + 1), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            binding.imgProfile.setImageBitmap(bitmap);
        } else {
            binding.imgProfile.setImageResource(R.drawable.account);
        }

        binding.btnCatPet.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListPetUserActivity.class);
            intent.putExtra("category", "Cat");
            startActivity(intent);
        });

        binding.btnDogPet.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListPetUserActivity.class);
            intent.putExtra("category", "Dog");
            startActivity(intent);
        });

        binding.btnBirdPet.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListPetUserActivity.class);
            intent.putExtra("category", "Bird");
            startActivity(intent);
        });

        binding.btnFishPet.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListPetUserActivity.class);
            intent.putExtra("category", "Fish");
            startActivity(intent);
        });

        binding.foodCat.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListFoodUserActivity.class);
            intent.putExtra("category", "Cat");
            startActivity(intent);
        });

        binding.foodDog.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListFoodUserActivity.class);
            intent.putExtra("category", "Dog");
            startActivity(intent);
        });

        binding.foodBird.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListFoodUserActivity.class);
            intent.putExtra("category", "Bird");
            startActivity(intent);
        });

        binding.foodFish.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListFoodUserActivity.class);
            intent.putExtra("category", "Fish");
            startActivity(intent);
        });

        binding.btnAccessories.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListAccessoriesUserActivity.class);
            startActivity(intent);
        });

        binding.imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ProfileUserActivity.class);
            startActivity(intent);
        });

    }
}