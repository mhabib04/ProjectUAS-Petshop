package com.example.projectuas_petshop.ui.user.accessories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityListAccessoriesUserBinding;
import com.example.projectuas_petshop.model.adapter.AdapterListAccessoriesUser;
import com.example.projectuas_petshop.model.select.selectAccessories.AccessoriesDataSelect;
import com.example.projectuas_petshop.model.select.selectAccessories.AccessoriesSelect;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAccessoriesUserActivity extends AppCompatActivity {

    private ActivityListAccessoriesUserBinding binding;
    AdapterListAccessoriesUser adapterListAccessoriesUser;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListAccessoriesUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listAccessoriesUser.setLayoutManager(new GridLayoutManager(this, 2));

        setSupportActionBar(binding.toolbarListAccessoriesUser);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();
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

                    adapterListAccessoriesUser = new AdapterListAccessoriesUser(getApplicationContext(), (ArrayList<AccessoriesDataSelect>) dataList);
                    binding.listAccessoriesUser.setAdapter(adapterListAccessoriesUser);
                    adapterListAccessoriesUser.setOnItemClickListener(new AdapterListAccessoriesUser.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int idAccessories) {
                            Intent intent = new Intent(ListAccessoriesUserActivity.this, BuyAccessoriesActivity.class);
                            intent.putExtra("id_accessories", idAccessories);
                            startActivity(intent);
                        }
                    });
                } else {
                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.empty_data), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessoriesSelect> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ListAccessoriesUserActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}