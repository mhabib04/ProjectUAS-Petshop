package com.example.projectuas_petshop.ui.admin.food;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.databinding.ActivityEditFoodBinding;

public class EditFoodActivity extends AppCompatActivity {
    private ActivityEditFoodBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}