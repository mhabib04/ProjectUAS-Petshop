package com.example.projectuas_petshop.ui.admin.accessories;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.projectuas_petshop.databinding.ActivityListAccessoriesAdminBinding;
import com.example.projectuas_petshop.model.adapter.AdapterListAccessoriesAdmin;
import com.example.projectuas_petshop.model.delete.Delete;
import com.example.projectuas_petshop.model.select.selectAccessories.AccessoriesDataSelect;
import com.example.projectuas_petshop.model.select.selectAccessories.AccessoriesSelect;
import com.example.projectuas_petshop.ui.admin.AdminActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAccessoriesAdminActivity extends AppCompatActivity {
    private ActivityListAccessoriesAdminBinding binding;
    AdapterListAccessoriesAdmin adapterListAccessoriesAdmin;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListAccessoriesAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listAccessoriesAdmin.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(binding.toolbarListAccessoriesAdmin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();

        binding.fabAccessories.setOnClickListener(v -> {
            Intent intent = new Intent(ListAccessoriesAdminActivity.this, AddAccessoriesActivity.class);
            startActivity(intent);
        });
    }

    public void loadData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AccessoriesSelect> accessoriesSelectCall = apiInterface.getAccessoriesData();
        accessoriesSelectCall.enqueue(new Callback<AccessoriesSelect>() {
            @Override
            public void onResponse(Call<AccessoriesSelect> call, Response<AccessoriesSelect> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    AccessoriesSelect accessories = response.body();
                    List<AccessoriesDataSelect> dataList = accessories.getData();

                    adapterListAccessoriesAdmin = new AdapterListAccessoriesAdmin(getApplicationContext(), (ArrayList<AccessoriesDataSelect>) dataList);
                    binding.listAccessoriesAdmin.setAdapter(adapterListAccessoriesAdmin);
                    adapterListAccessoriesAdmin.setOnItemClickListener(new AdapterListAccessoriesAdmin.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int id_accessories) {
                            showPopupMenu(view, position, id_accessories);
                        }
                    });
                } else {
                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.empty_data), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessoriesSelect> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ListAccessoriesAdminActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showPopupMenu(View view, int position, int id_accessories) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.delete) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListAccessoriesAdminActivity.this);
                    builder.setTitle(getString(R.string.delete_data));
                    builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteAccessories(id_accessories);
                            loadData();
                        }
                    });
                    builder.setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (item.getItemId() == R.id.edit) {
                    Intent intent = new Intent(ListAccessoriesAdminActivity.this, EditAccessoriesActivity.class);
                    intent.putExtra("id_accessories", id_accessories);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void deleteAccessories(int id_accessories) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Delete> deleteAccessoriesCall = apiInterface.deleteAccessories(id_accessories);

        deleteAccessoriesCall.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (response.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListAccessoriesAdminActivity.this);
                    builder.setTitle(getString(R.string.success));
                    builder.setMessage(getString(R.string.data_deleted_successfully));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();loadData();
                } else {
                    Toast.makeText(ListAccessoriesAdminActivity.this, getString(R.string.failed_to_delete_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Delete> call, Throwable t) {
                Toast.makeText(ListAccessoriesAdminActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListAccessoriesAdminActivity.this, AdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}