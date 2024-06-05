package com.example.projectuas_petshop.ui.admin.pet;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityEditPetBinding;
import com.example.projectuas_petshop.model.select.getPet.GetPet;
import com.example.projectuas_petshop.model.select.getPet.GetPetData;
import com.example.projectuas_petshop.model.update.Update;
import com.example.projectuas_petshop.ui.admin.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPetActivity extends AppCompatActivity {

    private ActivityEditPetBinding binding;
    ApiInterface apiInterface;
    private Uri imageUri;
    private boolean imageChanged = false;  // Flag to track if the image has been changed
    int id_pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id_pet = getIntent().getIntExtra("id_pet", 0);
        getData(id_pet);

        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        binding.btnUpdate.setOnClickListener(v -> {
            String selectType = binding.autoCompleteText.getText().toString().trim();
            String breed = binding.etBreed.getText().toString().trim();
            String priceString = binding.etPrice.getText().toString().trim();
            String ageString = binding.etAge.getText().toString().trim();

            if (selectType.isEmpty() || breed.isEmpty() || priceString.isEmpty() || ageString.isEmpty()) {
                Toast.makeText(EditPetActivity.this, "Selesaikan pengisian", Toast.LENGTH_SHORT).show();
            } else {
                int price = Integer.parseInt(priceString);
                int age = Integer.parseInt(ageString);

                if (imageChanged) {
                    updatePet(id_pet, selectType, breed, price, age, imageUri);
                } else if (binding.imgUploadPet.getDrawable() != null) {
                    binding.imgUploadPet.setDrawingCacheEnabled(true);
                    binding.imgUploadPet.buildDrawingCache(true);
                    Bitmap bitmap = binding.imgUploadPet.getDrawingCache();

                    File file = new File(getCacheDir(), "image.png");
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    updatePet(id_pet, selectType, breed, price, age, Uri.fromFile(file));
                } else {
                    Toast.makeText(EditPetActivity.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.option_pet, android.R.layout.simple_dropdown_item_1line);
        binding.autoCompleteText.setAdapter(adapter);
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

        if (requestCode == 1 && data != null) {
            imageUri = data.getData();
            binding.imgUploadPet.setImageURI(imageUri);
            imageChanged = true;
        }
    }

    public void getData(int id_pet) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetPet> call = apiInterface.getPet(id_pet);
        call.enqueue(new Callback<GetPet>() {
            @Override
            @SuppressLint({"SetTextI18n", "ResourceType"})
            public void onResponse(Call<GetPet> call, Response<GetPet> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    GetPet getPet = response.body();
                    List<GetPetData> data = getPet.getData();
                    binding.autoCompleteText.setText(data.get(0).getType(), false);
                    binding.etBreed.setText(data.get(0).getBreed());
                    byte[] imageBytes = Base64.decode(data.get(0).getImage().substring(data.get(0).getImage().indexOf(",") + 1), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    binding.imgUploadPet.setImageBitmap(bitmap);
                    String price = String.valueOf(data.get(0).getPrice());
                    String age = String.valueOf(data.get(0).getAge());
                    binding.etPrice.setText(price);
                    binding.etAge.setText(age);
                } else {
                    Toast.makeText(EditPetActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPet> call, Throwable t) {
                Toast.makeText(EditPetActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePet(int id_pet, String type, String breed, int price, int age, Uri imageUri) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String filePath = FileUtils.getPath(this, imageUri);

        if (filePath == null) {
            Toast.makeText(this, "Gagal mendapatkan path dari URI", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(filePath);
        RequestBody idPetBody = RequestBody.create(String.valueOf(id_pet), MediaType.parse("text/plain"));
        RequestBody typeBody = RequestBody.create(type, MediaType.parse("text/plain"));
        RequestBody breedBody = RequestBody.create(breed, MediaType.parse("text/plain"));
        RequestBody priceBody = RequestBody.create(String.valueOf(price), MediaType.parse("text/plain"));
        RequestBody ageBody = RequestBody.create(String.valueOf(age), MediaType.parse("text/plain"));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<Update> updatePet = apiInterface.updatePet(idPetBody, typeBody, breedBody, priceBody, ageBody, body);
        updatePet.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(@NonNull Call<Update> call, @NonNull Response<Update> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditPetActivity.this);
                    builder.setTitle("Sukses");
                    builder.setMessage("Data Berhasil Diupdate");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveToList();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (response.body() != null) {
                    Toast.makeText(EditPetActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditPetActivity.this, "Response tidak valid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Update> call, @NonNull Throwable t) {
                Toast.makeText(EditPetActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void moveToList() {
        Intent intent = new Intent(EditPetActivity.this, ListPetAdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}
