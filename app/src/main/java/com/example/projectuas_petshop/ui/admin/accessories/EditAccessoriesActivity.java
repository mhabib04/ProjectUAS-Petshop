package com.example.projectuas_petshop.ui.admin.accessories;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityEditAccessoriesBinding;
import com.example.projectuas_petshop.model.select.getAccessories.GetAccessories;
import com.example.projectuas_petshop.model.select.getAccessories.GetAccessoriesData;
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

public class EditAccessoriesActivity extends AppCompatActivity {
    private ActivityEditAccessoriesBinding binding;
    ApiInterface apiInterface;
    private Uri imageUri;
    private boolean imageChanged = false;
    int id_accessories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAccessoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id_accessories = getIntent().getIntExtra("id_accessories", 0);
        getData(id_accessories);

        setSupportActionBar(binding.toolbarEditAccessoriesAdmin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.btnSelectImageAccessories.setOnClickListener(v -> {
            openFileChooser();
        });

        binding.btnUpdateAccessories.setOnClickListener(v -> {
            String name = binding.etNameAccessories.getText().toString().trim();
            String priceString = binding.etPriceAccessories.getText().toString().trim();

            if (name.isEmpty() || priceString.isEmpty()) {
                Toast.makeText(EditAccessoriesActivity.this, getString(R.string.please_fill_in_all_field_completely), Toast.LENGTH_SHORT).show();
            } else {
                int price = Integer.parseInt(priceString);

                if (imageChanged) {
                    updateAccessories(id_accessories, name, price, imageUri);
                } else if (binding.imgUploadAccessories.getDrawable() != null) {
                    binding.imgUploadAccessories.setDrawingCacheEnabled(true);
                    binding.imgUploadAccessories.buildDrawingCache(true);
                    Bitmap bitmap = binding.imgUploadAccessories.getDrawingCache();

                    File file = new File(getCacheDir(), "image.png");
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    updateAccessories(id_accessories, name, price, Uri.fromFile(file));
                } else {
                    Toast.makeText(EditAccessoriesActivity.this, getString(R.string.select_image_first), Toast.LENGTH_SHORT).show();
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

        if (requestCode == 1 && data != null) {
            imageUri = data.getData();
            binding.imgUploadAccessories.setImageURI(imageUri);
            imageChanged = true;
        }
    }

    public void getData(int id_accessories) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetAccessories> getAccessoriesCall = apiInterface.getAccessories(id_accessories);
        getAccessoriesCall.enqueue(new Callback<GetAccessories>() {
            @Override
            @SuppressLint({"SetTextI18n", "ResourceType"})
            public void onResponse(Call<GetAccessories> call, Response<GetAccessories> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    GetAccessories getAccessories = response.body();
                    List<GetAccessoriesData> data = getAccessories.getData();
                    binding.etNameAccessories.setText(data.get(0).getName());
                    byte[] imageBytes = Base64.decode(data.get(0).getImage().substring(data.get(0).getImage().indexOf(",") + 1), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    binding.imgUploadAccessories.setImageBitmap(bitmap);
                    String price = String.valueOf(data.get(0).getPrice());
                    binding.etPriceAccessories.setText(price);
                } else {
                    Toast.makeText(EditAccessoriesActivity.this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAccessories> call, Throwable t) {
                Toast.makeText(EditAccessoriesActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAccessories(int id_accessories, String name, int price, Uri imageUri) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String filePath = FileUtils.getPath(this, imageUri);

        if (filePath == null) {
            Toast.makeText(this, getString(R.string.failded_to_get_the_path_of_the_uri), Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(filePath);
        RequestBody idAccessoriesBody = RequestBody.create(String.valueOf(id_accessories), MediaType.parse("text/plain"));
        RequestBody nameBody = RequestBody.create(name, MediaType.parse("text/plain"));
        RequestBody priceBody = RequestBody.create(String.valueOf(price), MediaType.parse("text/plain"));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<Update> updateAccessories = apiInterface.updateAccessories(idAccessoriesBody, nameBody, priceBody, imageBody);
        updateAccessories.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(@NonNull Call<Update> call, @NonNull Response<Update> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditAccessoriesActivity.this);
                    builder.setTitle(getString(R.string.success));
                    builder.setMessage(getString(R.string.data_updated_successfully));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveToList();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(EditAccessoriesActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Update> call, @NonNull Throwable t) {
                Toast.makeText(EditAccessoriesActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveToList() {
        Intent intent = new Intent(EditAccessoriesActivity.this, ListAccessoriesAdminActivity.class);
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