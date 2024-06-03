package com.example.projectuas_petshop.ui.admin.pet;

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
import com.example.projectuas_petshop.databinding.ActivityListPetAdminBinding;
import com.example.projectuas_petshop.model.delete.deletePet.DeletePet;
import com.example.projectuas_petshop.model.select.selectPet.PetDataSelect;
import com.example.projectuas_petshop.model.select.selectPet.PetSelect;
import com.example.projectuas_petshop.model.adapter.AdapterListPetAdmin;
import com.example.projectuas_petshop.ui.admin.AdminActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPetAdminActivity extends AppCompatActivity {

    private ActivityListPetAdminBinding binding;

    AdapterListPetAdmin adapterListPetAdmin;
    ApiInterface apiInterface;
    private Handler handler = new Handler();
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

        binding.fabPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(ListPetAdminActivity.this, AddPetActivity.class);
                startActivity(intent);
            }
        });
        startRepeatedTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatedTask();
    }

    private void startRepeatedTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
                handler.postDelayed(this, 3000);
            }
        }, 10000);
    }

    private void stopRepeatedTask() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }

    public void loadData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PetSelect> call = apiInterface.getPetData();
        call.enqueue(new Callback<PetSelect>() {
            @Override
            public void onResponse(Call<PetSelect> call, Response<PetSelect> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    PetSelect petSelect = response.body();
                    List<PetDataSelect> dataList = petSelect.getData();

                    ArrayList<PetDataSelect> filteredList = new ArrayList<>();

                    for (PetDataSelect data : dataList) {
                        if (data.getType().equals("Dog")) {
                            filteredList.add(data);
                        }
                    }

                    adapterListPetAdmin = new AdapterListPetAdmin(getApplicationContext(), (ArrayList<PetDataSelect>) dataList);
                    binding.listPetAdmin.setAdapter(adapterListPetAdmin);
                    adapterListPetAdmin.setOnItemClickListener(new AdapterListPetAdmin.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int idPet) {
                            showPopupMenu(view, position, idPet);
                        }
                    });
                } else {
                    Toast.makeText(ListPetAdminActivity.this, "Response error", Toast.LENGTH_SHORT).show();
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
                if (item.getItemId() == R.id.hapus) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListPetAdminActivity.this);
                    builder.setTitle("Apakah Ingin Menghapus Data?");
                    builder.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deletePet(id_pet);
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

    private void deletePet(int id_pet) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DeletePet> call = apiInterface.deletePet(id_pet);

        call.enqueue(new Callback<DeletePet>() {
            @Override
            public void onResponse(Call<DeletePet> call, Response<DeletePet> response) {
                if (response.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListPetAdminActivity.this);
                    builder.setTitle("Sukses");
                    builder.setMessage("Data Berhasil Dihapus");
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
                    Toast.makeText(ListPetAdminActivity.this, "Failed to delete pet: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeletePet> call, Throwable t) {
                Toast.makeText(ListPetAdminActivity.this, "Failed to delete pet: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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