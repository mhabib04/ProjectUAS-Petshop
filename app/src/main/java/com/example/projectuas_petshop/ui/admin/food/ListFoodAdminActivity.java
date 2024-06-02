package com.example.projectuas_petshop.ui.admin.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityListFoodAdminBinding;
import com.example.projectuas_petshop.model.adapter.AdapterListFoodAdmin;
import com.example.projectuas_petshop.model.selectFood.FoodSelect;
import com.example.projectuas_petshop.model.selectFood.FoodDataSelect;

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

    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }

    public void loadData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FoodSelect> call = apiInterface.getFoodData();
        call.enqueue(new Callback<FoodSelect>() {
            @Override
            public void onResponse(Call<FoodSelect> call, Response<FoodSelect> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FoodSelect pet = response.body();
                    List<FoodDataSelect> dataList = pet.getData();

                    adapterListFoodAdmin = new AdapterListFoodAdmin(getApplicationContext(), (ArrayList<FoodDataSelect>) dataList);
                    binding.listFoodAdmin.setAdapter(adapterListFoodAdmin);
                    adapterListFoodAdmin.setOnItemClickListener(new AdapterListFoodAdmin.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            showPopupMenu(view, position);
                        }
                    });
                } else {
                    Toast.makeText(ListFoodAdminActivity.this, "Response error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodSelect> call, Throwable t) {
                Toast.makeText(ListFoodAdminActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.hapus) {
                    Toast.makeText(ListFoodAdminActivity.this, "hapus", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}