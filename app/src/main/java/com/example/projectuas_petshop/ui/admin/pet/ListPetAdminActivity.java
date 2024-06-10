package com.example.projectuas_petshop.ui.admin.pet;

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
import com.example.projectuas_petshop.databinding.ActivityListPetAdminBinding;
import com.example.projectuas_petshop.model.delete.Delete;
import com.example.projectuas_petshop.model.select.selectPet.PetDataSelect;
import com.example.projectuas_petshop.model.select.selectPet.PetSelect;
import com.example.projectuas_petshop.model.adapter.AdapterListPetAdmin;
import com.example.projectuas_petshop.ui.admin.AdminActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPetAdminActivity extends AppCompatActivity {
    private ActivityListPetAdminBinding binding;
    AdapterListPetAdmin adapterListPetAdmin;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListPetAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listPetAdmin.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(binding.toolbarListPetAdmin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();

        binding.fabPet.setOnClickListener(v -> {
            Intent intent = new Intent(ListPetAdminActivity.this, AddPetActivity.class);
            startActivity(intent);
        });
    }


    public void loadData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PetSelect> petSelectCall = apiInterface.getPetData();
        petSelectCall.enqueue(new Callback<PetSelect>() {
            @Override
            public void onResponse(Call<PetSelect> call, Response<PetSelect> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    PetSelect petSelect = response.body();
                    List<PetDataSelect> dataList = petSelect.getData();

                    adapterListPetAdmin = new AdapterListPetAdmin(getApplicationContext(), (ArrayList<PetDataSelect>) dataList);
                    binding.listPetAdmin.setAdapter(adapterListPetAdmin);
                    adapterListPetAdmin.setOnItemClickListener(new AdapterListPetAdmin.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int idPet) {
                            showPopupMenu(view, position, idPet);
                        }
                    });
                } else {
                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.empty_data), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PetSelect> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ListPetAdminActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopupMenu(View view, int position, int id_pet) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.delete) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListPetAdminActivity.this);
                    builder.setTitle(getString(R.string.delete_data));
                    builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deletePet(id_pet);
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
                    Intent intent = new Intent(ListPetAdminActivity.this, EditPetActivity.class);
                    intent.putExtra("id_pet", id_pet);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void deletePet(int id_pet) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Delete> deletePetCall = apiInterface.deletePet(id_pet);
        deletePetCall.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (response.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListPetAdminActivity.this);
                    builder.setTitle(getString(R.string.success));
                    builder.setMessage(getString(R.string.data_deleted_successfully));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    loadData();
                } else {
                    Toast.makeText(ListPetAdminActivity.this, getString(R.string.failed_to_delete_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Delete> call, Throwable t) {
                Toast.makeText(ListPetAdminActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListPetAdminActivity.this, AdminActivity.class);
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