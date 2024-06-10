package com.example.projectuas_petshop.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.model.SessionManager;
import com.example.projectuas_petshop.ui.admin.AdminActivity;
import com.example.projectuas_petshop.ui.user.UserActivity;

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
                    Intent intent = new Intent(SplashScreenActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else if(!sessionManager.isLoggedIn()){
                    Intent intent = new Intent(SplashScreenActivity.this, StartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_SCREEN_DELAY);

    }
}