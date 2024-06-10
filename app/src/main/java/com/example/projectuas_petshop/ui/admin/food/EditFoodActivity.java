package com.example.projectuas_petshop.ui.admin.food;

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
import com.example.projectuas_petshop.databinding.ActivityEditFoodBinding;
import com.example.projectuas_petshop.model.select.getFood.GetFood;
import com.example.projectuas_petshop.model.select.getFood.GetFoodData;
import com.example.projectuas_petshop.model.update.Update;
import com.example.projectuas_petshop.model.FileUtils;

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

public class EditFoodActivity extends AppCompatActivity {
    private ActivityEditFoodBinding binding;
    ApiInterface apiInterface;
    private Uri imageUri;
    private boolean imageChanged = false;
    int id_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id_food = getIntent().getIntExtra("id_food", 0);
        getData(id_food);

        setSupportActionBar(binding.toolbarEditFoodAdmin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.btnSelectImageFood.setOnClickListener(v -> openFileChooser());

        binding.btnUpdateFood.setOnClickListener(v -> {
            String selectType = binding.optionTypeFood.getText().toString().trim();
            String name = binding.etNameFood.getText().toString().trim();
            String priceString = binding.etPriceFood.getText().toString().trim();

            if (selectType.isEmpty() || name.isEmpty() || priceString.isEmpty()) {
                Toast.makeText(EditFoodActivity.this, getString(R.string.please_fill_in_all_field_completely), Toast.LENGTH_SHORT).show();
            } else {
                int price = Integer.parseInt(priceString);

                if (imageChanged) {
                    updateFood(id_food, selectType, name, price, imageUri);
                } else if (binding.imgSelectImageFood.getDrawable() != null) {
                    binding.imgSelectImageFood.setDrawingCacheEnabled(true);
                    binding.imgSelectImageFood.buildDrawingCache(true);
                    Bitmap bitmap = binding.imgSelectImageFood.getDrawingCache();

                    File file = new File(getCacheDir(), "image.png");
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    updateFood(id_food, selectType, name, price, Uri.fromFile(file));
                } else {
                    Toast.makeText(EditFoodActivity.this, getString(R.string.select_image_first), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.option_types, android.R.layout.simple_dropdown_item_1line);
        binding.optionTypeFood.setAdapter(adapter);
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
            binding.imgSelectImageFood.setImageURI(imageUri);
            imageChanged = true;
        }
    }

    public void getData(int id_food) {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetFood> getFoodCall = apiInterface.getFood(id_food);
        getFoodCall.enqueue(new Callback<GetFood>() {
            @Override
            @SuppressLint({"SetTextI18n", "ResourceType"})
            public void onResponse(Call<GetFood> call, Response<GetFood> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    GetFood getFood = response.body();
                    List<GetFoodData> data = getFood.getData();
                    binding.optionTypeFood.setText(data.get(0).getType(), false);
                    binding.etNameFood.setText(data.get(0).getName());
                    byte[] imageBytes = Base64.decode(data.get(0).getImage().substring(data.get(0).getImage().indexOf(",") + 1), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    binding.imgSelectImageFood.setImageBitmap(bitmap);
                    String price = String.valueOf(data.get(0).getPrice());
                    binding.etPriceFood.setText(price);
                } else {
                    Toast.makeText(EditFoodActivity.this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFood> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(EditFoodActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFood(int id_food, String type, String name, int price, Uri imageUri) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String filePath = FileUtils.getPath(this, imageUri);

        if (filePath == null) {
            Toast.makeText(this, getString(R.string.failded_to_get_the_path_of_the_uri), Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(filePath);
        RequestBody idFoodBody = RequestBody.create(String.valueOf(id_food), MediaType.parse("text/plain"));
        RequestBody typeBody = RequestBody.create(type, MediaType.parse("text/plain"));
        RequestBody nameBody = RequestBody.create(name, MediaType.parse("text/plain"));
        RequestBody priceBody = RequestBody.create(String.valueOf(price), MediaType.parse("text/plain"));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<Update> updateFoodCall = apiInterface.updateFood(idFoodBody, typeBody, nameBody, priceBody, imageBody);
        updateFoodCall.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(@NonNull Call<Update> call, @NonNull Response<Update> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditFoodActivity.this);
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
                    Toast.makeText(EditFoodActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Update> call, @NonNull Throwable t) {
                Toast.makeText(EditFoodActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveToList() {
        Intent intent = new Intent(EditFoodActivity.this, ListFoodAdminActivity.class);
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