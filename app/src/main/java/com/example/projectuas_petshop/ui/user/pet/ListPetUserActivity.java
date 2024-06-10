package com.example.projectuas_petshop.ui.user.pet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityListPetUserBinding;
import com.example.projectuas_petshop.model.adapter.AdapterListPetUser;
import com.example.projectuas_petshop.model.select.selectPetByType.PetDataSelectByType;
import com.example.projectuas_petshop.model.select.selectPetByType.PetSelectByType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPetUserActivity extends AppCompatActivity {

    private ActivityListPetUserBinding binding;
    AdapterListPetUser adapterListPetUser;
    ApiInterface apiInterface;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListPetUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listPetUser.setLayoutManager(new GridLayoutManager(this, 2));

        setSupportActionBar(binding.toolbarListPetUser);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category = getIntent().getStringExtra("category");
        if(category.equals("Cat")){
            binding.titleListPetUser.setText(getString(R.string.cat));
        } else if(category.equals("Dog")){
            binding.titleListPetUser.setText(getString(R.string.dog));
        } else if(category.equals("Fish")){
            binding.titleListPetUser.setText(getString(R.string.fish));
        } else if(category.equals("Bird")){
            binding.titleListPetUser.setText(getString(R.string.bird));
        }

        loadData(category);
    }

    public void loadData(String type) {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PetSelectByType> call = apiInterface.petSelectByType(type);
        call.enqueue(new Callback<PetSelectByType>() {
            @Override
            public void onResponse(Call<PetSelectByType> call, Response<PetSelectByType> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    PetSelectByType petSelectByType = response.body();
                    List<PetDataSelectByType> dataList = petSelectByType.getData();

                    adapterListPetUser = new AdapterListPetUser(getApplicationContext(), (ArrayList<PetDataSelectByType>) dataList);
                    binding.listPetUser.setAdapter(adapterListPetUser);
                    adapterListPetUser.setOnItemClickListener(new AdapterListPetUser.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int idPet) {
                            Intent intent = new Intent(ListPetUserActivity.this, BuyPetActivity.class);
                            intent.putExtra("id_pet", idPet);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(ListPetUserActivity.this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PetSelectByType> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ListPetUserActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}