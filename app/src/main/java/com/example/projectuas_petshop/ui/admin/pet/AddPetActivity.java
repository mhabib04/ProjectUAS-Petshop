package com.example.projectuas_petshop.ui.admin.pet;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityAddPetBinding;
import com.example.projectuas_petshop.model.insert.insertPet.PetInsert;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPetActivity extends AppCompatActivity {

    private ActivityAddPetBinding binding;
    ApiInterface apiInterface;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectType = binding.autoCompleteText.getText().toString().trim();
                String breed = binding.etBreed.getText().toString().trim();
                String priceString = binding.etPrice.getText().toString().trim();
                String ageString = binding.etAge.getText().toString().trim();

                if (binding.autoCompleteText.getText().toString().isEmpty() || breed.isEmpty() || priceString.isEmpty() || ageString.isEmpty()) {
                    Toast.makeText(AddPetActivity.this, "Selesaikan pengisian", Toast.LENGTH_SHORT).show();
                } else if (imageUri == null) {
                    Toast.makeText(AddPetActivity.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    int price = Integer.parseInt(priceString);
                    int age = Integer.parseInt(ageString);
                    addPet(selectType, breed, price, age, imageUri);
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
            binding.imgUploadPet.setImageURI(imageUri);
        }
    }

    private void addPet(String type, String breed, int price, int age, Uri imageUri) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        File file = new File(FileUtils.getPath(this, imageUri));
        RequestBody typeBody = RequestBody.create(type, MediaType.parse("text/plain"));
        RequestBody breedBody = RequestBody.create(breed, MediaType.parse("text/plain"));
        RequestBody priceBody = RequestBody.create(String.valueOf(price), MediaType.parse("text/plain"));
        RequestBody ageBody = RequestBody.create(String.valueOf(age), MediaType.parse("text/plain"));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<PetInsert> insertAnimalCall = apiInterface.insertPetResponse(typeBody,breedBody, priceBody, ageBody, body);
        insertAnimalCall.enqueue(new Callback<PetInsert>() {
            @Override
            public void onResponse(@NonNull Call<PetInsert> call, @NonNull Response<PetInsert> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPetActivity.this);
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
                    Toast.makeText(AddPetActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PetInsert> call, @NonNull Throwable t) {
                Toast.makeText(AddPetActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void moveToList() {
        Intent intent = new Intent(AddPetActivity.this, ListPetAdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}