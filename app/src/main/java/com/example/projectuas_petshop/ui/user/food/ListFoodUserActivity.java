package com.example.projectuas_petshop.ui.user.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiClient;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityListFoodUserBinding;
import com.example.projectuas_petshop.databinding.ActivityListPetUserBinding;
import com.example.projectuas_petshop.model.adapter.AdapterListFoodUser;
import com.example.projectuas_petshop.model.adapter.AdapterListPetUser;
import com.example.projectuas_petshop.model.select.selectFoodByType.FoodDataSelectByType;
import com.example.projectuas_petshop.model.select.selectFoodByType.FoodSelectByType;
import com.example.projectuas_petshop.model.select.selectPetByType.PetDataSelectByType;
import com.example.projectuas_petshop.model.select.selectPetByType.PetSelectByType;
import com.example.projectuas_petshop.ui.user.pet.ListPetUserActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFoodUserActivity extends AppCompatActivity {

    private ActivityListFoodUserBinding binding;
    AdapterListFoodUser adapterListFoodUser;
    ApiInterface apiInterface;
    String category;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listFoodUser.setLayoutManager(new GridLayoutManager(this, 2));

        setSupportActionBar(binding.toolbarListFoodUser);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category = getIntent().getStringExtra("category");
        if(category.equals("Cat")){
            binding.titleListFoodUser.setText(getString(R.string.food)  + " " + getString(R.string.cat));
        } else if(category.equals("Dog")){
            binding.titleListFoodUser.setText(getString(R.string.food)  + " " + getString(R.string.dog));
        } else if(category.equals("Fish")){
            binding.titleListFoodUser.setText(getString(R.string.food)  + " " + getString(R.string.fish));
        } else if(category.equals("Bird")){
            binding.titleListFoodUser.setText(getString(R.string.food)  + " " + getString(R.string.bird));
        }
        loadData(category);
    }
    public void loadData(String type) {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FoodSelectByType> call = apiInterface.foodSelectByType(type);
        call.enqueue(new Callback<FoodSelectByType>() {
            @Override
            public void onResponse(Call<FoodSelectByType> call, Response<FoodSelectByType> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    FoodSelectByType foodSelectByType = response.body();
                    List<FoodDataSelectByType> dataList = foodSelectByType.getData();

                    adapterListFoodUser = new AdapterListFoodUser(getApplicationContext(), (ArrayList<FoodDataSelectByType>) dataList);
                    binding.listFoodUser.setAdapter(adapterListFoodUser);
                    adapterListFoodUser.setOnItemClickListener(new AdapterListFoodUser.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int idFood) {
                            Intent intent = new Intent(ListFoodUserActivity.this, BuyFoodActivity.class);
                            intent.putExtra("id_food", idFood);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(ListFoodUserActivity.this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodSelectByType> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ListFoodUserActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}