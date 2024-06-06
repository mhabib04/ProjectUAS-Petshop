package com.example.projectuas_petshop.ui.admin.accessories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.api.ApiInterface;
import com.example.projectuas_petshop.databinding.ActivityListAccessoriesAdminBinding;
import com.example.projectuas_petshop.model.adapter.AdapterListAccessoriesAdmin;
import com.example.projectuas_petshop.model.adapter.AdapterListFoodAdmin;
import com.example.projectuas_petshop.ui.admin.food.AddFoodActivity;
import com.example.projectuas_petshop.ui.admin.food.ListFoodAdminActivity;

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

        binding.fabAccessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(ListAccessoriesAdminActivity.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });
    }
}