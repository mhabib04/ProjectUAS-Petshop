package com.example.projectuas_petshop.ui;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;
    private ActivityMainBinding binding;
    String usernameMain, nameMain;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        usernameMain = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        nameMain = sessionManager.getUserDetail().get(SessionManager.ROLE);

        binding.etMainUsername.setText("Username : @" + usernameMain);
        binding.etMainName.setText("Name : " + nameMain);

        binding.btnLogout.setOnClickListener(v -> {
            sessionManager.logoutSession();
            moveToLogin();
        });

    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}