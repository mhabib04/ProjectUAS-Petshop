package com.example.projectuas_petshop.ui.user.pet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityListPetUserBinding;
import com.example.projectuas_petshop.model.adapter.AdapterListPetAdmin;
import com.example.projectuas_petshop.model.adapter.AdapterListPetUser;
import com.example.projectuas_petshop.model.select.selectPet.PetDataSelect;
import com.example.projectuas_petshop.model.select.selectPet.PetSelect;
import com.example.projectuas_petshop.model.select.selectPetByType.PetDataSelectByType;
import com.example.projectuas_petshop.model.select.selectPetByType.PetSelectByType;
import com.example.projectuas_petshop.ui.admin.food.ListFoodAdminActivity;
import com.example.projectuas_petshop.ui.admin.pet.ListPetAdminActivity;
import com.example.projectuas_petshop.ui.user.food.BuyFoodActivity;
import com.example.projectuas_petshop.ui.user.food.ListFoodUserActivity;

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

        loadData(category);

    }

    public void loadData(String type) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PetSelectByType> call = apiInterface.petSelectByType(type);
        call.enqueue(new Callback<PetSelectByType>() {
            @Override
            public void onResponse(Call<PetSelectByType> call, Response<PetSelectByType> response) {
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
                    Toast.makeText(ListPetUserActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PetSelectByType> call, Throwable t) {
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