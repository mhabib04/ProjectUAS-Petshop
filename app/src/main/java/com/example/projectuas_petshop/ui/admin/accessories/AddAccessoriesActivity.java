package com.example.projectuas_petshop.ui.admin.accessories;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityAddAccessoriesBinding;
import com.example.projectuas_petshop.model.insert.Insert;
import com.example.projectuas_petshop.ui.admin.FileUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAccessoriesActivity extends AppCompatActivity {

    private ActivityAddAccessoriesBinding binding;
    ApiInterface apiInterface;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAccessoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.btnUpload.setOnClickListener(v -> {
            openFileChooser();
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etName.getText().toString().trim();
                String priceString = binding.etPrice.getText().toString().trim();

                if (name.isEmpty() || priceString.isEmpty()) {
                    Toast.makeText(AddAccessoriesActivity.this, "Selesaikan pengisian", Toast.LENGTH_SHORT).show();
                } else if (imageUri == null) {
                    Toast.makeText(AddAccessoriesActivity.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    int price = Integer.parseInt(priceString);
                    addAccessories(name, price, imageUri);
                }
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
            binding.imgUploadAccessories.setImageURI(imageUri);
        }
    }

    private void addAccessories(String name, int price, Uri imageUri) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        File file = new File(FileUtils.getPath(this, imageUri));
        RequestBody nameBody = RequestBody.create(name, MediaType.parse("text/plain"));
        RequestBody priceBody = RequestBody.create(String.valueOf(price), MediaType.parse("text/plain"));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<Insert> insertAccessoriesCall = apiInterface.insertAccessoriesResponse(nameBody, priceBody, body);
        insertAccessoriesCall.enqueue(new Callback<Insert>() {
            @Override
            public void onResponse(@NonNull Call<Insert> call, @NonNull Response<Insert> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAccessoriesActivity.this);
                    builder.setTitle("Sukses");
                    builder.setMessage("Data Berhasil Disimpan");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveToList();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(AddAccessoriesActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Insert> call, @NonNull Throwable t) {
                Toast.makeText(AddAccessoriesActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveToList() {
        Intent intent = new Intent(AddAccessoriesActivity.this, ListAccessoriesAdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}