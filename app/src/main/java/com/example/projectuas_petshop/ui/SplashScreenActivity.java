package com.example.projectuas_petshop.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.ui.admin.AdminActivity;

import java.util.HashMap;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager = new SessionManager(this);

        HashMap<String, String> userDetail = sessionManager.getUserDetail();
        String role = userDetail.get(SessionManager.ROLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("admin".equals(role)) {
                    Intent intent = new Intent(SplashScreenActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                } else if ("user".equals(role)) {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if(!sessionManager.isLoggedIn()){
                    moveToLogin();
                }
            }
        }, SPLASH_SCREEN_DELAY);

    }

    private void moveToLogin() {
        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}