package com.example.projectuas_petshop.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.databinding.ActivityAdminBinding;
import com.example.projectuas_petshop.ui.LoginActivity;
import com.example.projectuas_petshop.ui.SessionManager;
import com.example.projectuas_petshop.ui.admin.accessories.ListAccessoriesAdminActivity;
import com.example.projectuas_petshop.ui.admin.food.ListFoodAdminActivity;
import com.example.projectuas_petshop.ui.admin.pet.ListPetAdminActivity;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.btnHewan.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListPetAdminActivity.class);
            startActivity(intent);
        });

        binding.btnMakanan.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListFoodAdminActivity.class);
            startActivity(intent);
        });

        binding.btnAksesoris.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListAccessoriesAdminActivity.class);
            startActivity(intent);
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutSession();
                moveToLogin();
            }
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}