package com.example.projectuas_petshop.ui.admin.food;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityAddFoodBinding;
import com.example.projectuas_petshop.model.insert.Insert;
import com.example.projectuas_petshop.model.FileUtils;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodActivity extends AppCompatActivity {
    private ActivityAddFoodBinding binding;
    ApiInterface apiInterface;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarAddFoodAdmin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.btnSelectImageFood.setOnClickListener(v -> openFileChooser());

        binding.btnAddFood.setOnClickListener(v -> {
            String selectType = binding.optionTypeFood.getText().toString().trim();
            String name = binding.etNameFood.getText().toString().trim();
            String priceString = binding.etPriceFood.getText().toString().trim();

            if (binding.optionTypeFood.getText().toString().isEmpty() || name.isEmpty() || priceString.isEmpty()) {
                Toast.makeText(AddFoodActivity.this, getString(R.string.please_fill_in_all_field_completely), Toast.LENGTH_SHORT).show();
            } else if (imageUri == null) {
                Toast.makeText(AddFoodActivity.this, getString(R.string.select_image_first), Toast.LENGTH_SHORT).show();
            } else {
                int price = Integer.parseInt(priceString);
                addFood(selectType, name, price, imageUri);
            }
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
            binding.imgSelectImageFood.setImageURI(imageUri);
        }
    }

    private void addFood(String type, String name, int price, Uri imageUri) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        File file = new File(FileUtils.getPath(this, imageUri));
        RequestBody typeBody = RequestBody.create(type, MediaType.parse("text/plain"));
        RequestBody nameBody = RequestBody.create(name, MediaType.parse("text/plain"));
        RequestBody priceBody = RequestBody.create(String.valueOf(price), MediaType.parse("text/plain"));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<Insert> insertFoodCall = apiInterface.insertFoodResponse(typeBody, nameBody, priceBody, imageBody);
        insertFoodCall.enqueue(new Callback<Insert>() {
            @Override
            public void onResponse(@NonNull Call<Insert> call, @NonNull Response<Insert> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddFoodActivity.this);
                    builder.setTitle(getString(R.string.success));
                    builder.setMessage(getString(R.string.data_added_successfully));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveToList();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(AddFoodActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Insert> call, @NonNull Throwable t) {
                Toast.makeText(AddFoodActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void moveToList() {
        Intent intent = new Intent(AddFoodActivity.this, ListFoodAdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}