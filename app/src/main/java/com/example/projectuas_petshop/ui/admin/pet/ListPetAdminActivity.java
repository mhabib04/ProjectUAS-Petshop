package com.example.projectuas_petshop.ui.admin.pet;

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
import com.example.projectuas_petshop.databinding.ActivityListPetAdminBinding;
import com.example.projectuas_petshop.model.selectPet.PetDataSelect;
import com.example.projectuas_petshop.model.selectPet.PetSelect;
import com.example.projectuas_petshop.model.adapter.AdapterListPetAdmin;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }

    public void loadData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PetSelect> call = apiInterface.getPetData();
        call.enqueue(new Callback<PetSelect>() {
            @Override
            public void onResponse(Call<PetSelect> call, Response<PetSelect> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PetSelect petSelect = response.body();
                    List<PetDataSelect> dataList = petSelect.getData();

                    adapterListPetAdmin = new AdapterListPetAdmin(getApplicationContext(), (ArrayList<PetDataSelect>) dataList);
                    binding.listPetAdmin.setAdapter(adapterListPetAdmin);
                    adapterListPetAdmin.setOnItemClickListener(new AdapterListPetAdmin.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            showPopupMenu(view, position);
                        }
                    });
                } else {
                    Toast.makeText(ListPetAdminActivity.this, "Response error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PetSelect> call, Throwable t) {
                Toast.makeText(ListPetAdminActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ListPetAdminActivity.this, "hapus", Toast.LENGTH_SHORT).show();
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