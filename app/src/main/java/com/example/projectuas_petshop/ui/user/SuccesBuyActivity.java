package com.example.projectuas_petshop.ui.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.databinding.ActivitySuccesBuyBinding;

public class SuccesBuyActivity extends AppCompatActivity {

    private ActivitySuccesBuyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccesBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        });
    }
}