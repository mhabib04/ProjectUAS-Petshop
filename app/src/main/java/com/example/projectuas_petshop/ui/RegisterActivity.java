package com.example.projectuas_petshop.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityRegisterBinding;
import com.example.projectuas_petshop.model.register.Register;
import com.example.projectuas_petshop.ui.admin.FileUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    String usernameRegister, passwordRegister, nameRegister;
    ApiInterface apiInterface;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSelectImage.setOnClickListener(v -> {
            openFileChooser();
        });

        binding.btnRegister.setOnClickListener(v -> {
            usernameRegister = binding.etUsernameRegister.getText().toString().trim();
            passwordRegister = binding.etPasswordRegister.getText().toString().trim();
            nameRegister = binding.etNameRegister.getText().toString().trim();
            if(usernameRegister.isEmpty() || nameRegister.isEmpty() || passwordRegister.isEmpty()){
                Toast.makeText(RegisterActivity.this, getString(R.string.please_fill_in_all_field_completely), Toast.LENGTH_SHORT).show();
            } else if (imageUri == null || binding.imgSelectImage.getDrawable() == null) {
                Toast.makeText(RegisterActivity.this, "Select Image First", Toast.LENGTH_SHORT).show();
            } else {
                register(usernameRegister, passwordRegister, nameRegister, imageUri);
            }
        });

        binding.tvAlreadyHaveAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && data!=null){
            imageUri = data.getData();
            binding.imgSelectImage.setImageURI(imageUri);
        }
    }

    private void register(String username, String password, String name, Uri imageUri) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        File file = new File(FileUtils.getPath(this, imageUri));
        RequestBody usernameBody = RequestBody.create(username, MediaType.parse("text/plain"));
        RequestBody nameBody = RequestBody.create(name, MediaType.parse("text/plain"));
        RequestBody passwordBody = RequestBody.create(password, MediaType.parse("text/plain"));
        RequestBody roleBody = RequestBody.create("user", MediaType.parse("text/plain"));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<Register> registerCall = apiInterface.registerResponse(usernameBody,passwordBody, nameBody, roleBody, imageBody);
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