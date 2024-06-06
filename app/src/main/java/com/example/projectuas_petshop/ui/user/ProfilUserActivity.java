package com.example.projectuas_petshop.ui.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.databinding.ActivityProfilUserBinding;

public class ProfilUserActivity extends AppCompatActivity {

    private ActivityProfilUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}