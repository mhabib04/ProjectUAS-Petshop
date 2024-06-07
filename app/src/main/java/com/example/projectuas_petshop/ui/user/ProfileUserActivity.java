package com.example.projectuas_petshop.ui.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.databinding.ActivityProfileUserBinding;
import com.example.projectuas_petshop.ui.LoginActivity;
import com.example.projectuas_petshop.ui.SessionManager;
import com.example.projectuas_petshop.ui.admin.AdminActivity;

import java.util.HashMap;

public class ProfileUserActivity extends AppCompatActivity {

    private ActivityProfileUserBinding binding;
    SessionManager sessionManager;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileUserBinding.inflate(getLayoutInflater());
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

        binding.name.setText(userDetail.get(SessionManager.NAME));
        binding.username.setText("@" + userDetail.get(SessionManager.USERNAME));

        binding.btnLogout.setOnClickListener(v -> {
            sessionManager.logoutSession();
            moveToLogin();
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(ProfileUserActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}