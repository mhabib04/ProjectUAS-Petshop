package com.example.projectuas_petshop.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.databinding.ActivityAdminBinding;
import com.example.projectuas_petshop.ui.LoginActivity;
import com.example.projectuas_petshop.ui.MainActivity;
import com.example.projectuas_petshop.ui.SessionManager;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.daftarKucing.setOnClickListener(v -> {
            Intent intent = new Intent(this, DaftarActivity.class);
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