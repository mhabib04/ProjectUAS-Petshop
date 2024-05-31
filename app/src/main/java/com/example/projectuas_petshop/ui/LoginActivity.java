package com.example.projectuas_petshop.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityLoginBinding;
import com.example.projectuas_petshop.model.login.Login;
import com.example.projectuas_petshop.model.login.LoginData;
import com.example.projectuas_petshop.ui.admin.AdminActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    String usernameLogin, passwordLogin;
    ApiInterface apiInterface;
    SessionManager sessionManager;
    private boolean isPasswordVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> {
            usernameLogin = binding.etUsernameLogin.getText().toString().trim();
            passwordLogin = binding.etPasswordLogin.getText().toString().trim();
            if(usernameLogin.isEmpty() || passwordLogin.isEmpty()){
                Toast.makeText(LoginActivity.this, getString(R.string.please_fill_in_all_field_completely), Toast.LENGTH_SHORT).show();
            } else {
                login(usernameLogin, passwordLogin);
            }
        });

        binding.tvCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.btnShowHidePassword.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                binding.etPasswordLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.btnShowHidePassword.setImageResource(R.drawable.show_password);
            } else {
                binding.etPasswordLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.btnShowHidePassword.setImageResource(R.drawable.password);
            }
            binding.etPasswordLogin.setSelection(binding.etPasswordLogin.getText().length());
        });

    }

    private void login(String username, String password) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Login> loginCall = apiInterface.loginResponse(username, password);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    sessionManager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getLoginData();
                    sessionManager.createLoginSession(loginData);

                    HashMap<String, String> userDetail = sessionManager.getUserDetail();
                    String role = userDetail.get(SessionManager.ROLE);

                    if ("admin".equals(role)) {
                        Toast.makeText(LoginActivity.this, response.body().getLoginData().getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                    } else if ("user".equals(role)) {
                        Toast.makeText(LoginActivity.this, response.body().getLoginData().getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    String message = response.body().getMessage();
                    if (message.equals(getString(R.string.username_not_registered))){
                        Toast.makeText(LoginActivity.this, getString(R.string.username_not_registered), Toast.LENGTH_SHORT).show();
                    } else if (message.endsWith(getString(R.string.incorrect_password))) {
                        Toast.makeText(LoginActivity.this, getString(R.string.incorrect_password), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}