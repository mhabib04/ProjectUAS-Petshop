package com.example.projectuas_petshop.ui.admin.food;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityListFoodAdminBinding;
import com.example.projectuas_petshop.model.adapter.AdapterListFoodAdmin;
import com.example.projectuas_petshop.model.delete.deleteFood.DeleteFood;
import com.example.projectuas_petshop.model.select.selectFood.FoodSelect;
import com.example.projectuas_petshop.model.select.selectFood.FoodDataSelect;
import com.example.projectuas_petshop.ui.admin.AdminActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFoodAdminActivity extends AppCompatActivity {

    private ActivityListFoodAdminBinding binding;

    AdapterListFoodAdmin adapterListFoodAdmin;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listFoodAdmin.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(binding.toolbarListFoodAdmin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();

        binding.fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(ListFoodAdminActivity.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });
    }


    public void loadData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FoodSelect> call = apiInterface.getFoodData();
        call.enqueue(new Callback<FoodSelect>() {
            @Override
            public void onResponse(Call<FoodSelect> call, Response<FoodSelect> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    FoodSelect pet = response.body();
                    List<FoodDataSelect> dataList = pet.getData();

                    adapterListFoodAdmin = new AdapterListFoodAdmin(getApplicationContext(), (ArrayList<FoodDataSelect>) dataList);
                    binding.listFoodAdmin.setAdapter(adapterListFoodAdmin);
                    adapterListFoodAdmin.setOnItemClickListener(new AdapterListFoodAdmin.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int id_food) {
                            showPopupMenu(view, position, id_food);
                        }
                    });
                } else {
                    Toast.makeText(ListFoodAdminActivity.this, "Data kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodSelect> call, Throwable t) {
                Toast.makeText(ListFoodAdminActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showPopupMenu(View view, int position, int id_food) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.hapus) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListFoodAdminActivity.this);
                    builder.setTitle("Apakah Ingin Menghapus Data?");
                    builder.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteFood(id_food);
                        }
                    });
                    builder.setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                return false;
            }
        });
    }

    private void deleteFood(int id_food) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DeleteFood> call = apiInterface.deleteFood(id_food);

        call.enqueue(new Callback<DeleteFood>() {
            @Override
            public void onResponse(Call<DeleteFood> call, Response<DeleteFood> response) {
                if (response.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListFoodAdminActivity.this);
                    builder.setTitle("Sukses");
                    builder.setMessage("Data Berhasil Dihapus");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();loadData();
                } else {
                    Toast.makeText(ListFoodAdminActivity.this, "Failed to delete food: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteFood> call, Throwable t) {
                Toast.makeText(ListFoodAdminActivity.this, "Failed to delete food: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListFoodAdminActivity.this, AdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        super.onBackPressed(); // Call the super method
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Calls the onBackPressed method
        return true; // Return true to indicate that the navigation up action was handled
    }
}