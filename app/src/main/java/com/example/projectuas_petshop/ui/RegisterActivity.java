package com.example.projectuas_petshop.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityRegisterBinding;
import com.example.projectuas_petshop.model.register.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    String usernameRegister, passwordRegister, nameRegister;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(v -> {
            usernameRegister = binding.etUsernameRegister.getText().toString().trim();
            passwordRegister = binding.etPasswordRegister.getText().toString().trim();
            nameRegister = binding.etNameRegister.getText().toString().trim();
            if(usernameRegister.isEmpty() || nameRegister.isEmpty() || passwordRegister.isEmpty()){
                Toast.makeText(RegisterActivity.this, getString(R.string.please_fill_in_all_field_completely), Toast.LENGTH_SHORT).show();
            } else {
                register(usernameRegister, passwordRegister, nameRegister);
            }
        });

        binding.tvAlreadyHaveAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void register(String username, String password, String name) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> registerCall = apiInterface.registerResponse(username,password, name, "user");
        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(@NonNull Call<Register> call, @NonNull Response<Register> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    String message = response.body().getMessage();
                    Toast.makeText(RegisterActivity.this, getString(R.string.account) + " " + message + " " + getString(R.string.successfully), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String message = response.body().getMessage();
                    Toast.makeText(RegisterActivity.this, getString(R.string.account) + " " + message + " " + getString(R.string.is_already_in_use), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Register> call, @NonNull Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}