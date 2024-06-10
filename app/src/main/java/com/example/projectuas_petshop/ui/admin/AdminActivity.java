package com.example.projectuas_petshop.ui.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.databinding.ActivityAdminBinding;
import com.example.projectuas_petshop.ui.LoginActivity;
import com.example.projectuas_petshop.model.SessionManager;
import com.example.projectuas_petshop.ui.admin.accessories.ListAccessoriesAdminActivity;
import com.example.projectuas_petshop.ui.admin.food.ListFoodAdminActivity;
import com.example.projectuas_petshop.ui.admin.pet.ListPetAdminActivity;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.petAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListPetAdminActivity.class);
            startActivity(intent);
        });

        binding.foodAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListFoodAdminActivity.class);
            startActivity(intent);
        });

        binding.accessoriesAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListAccessoriesAdminActivity.class);
            startActivity(intent);
        });

        binding.btnLogout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
            builder.setTitle(getString(R.string.are_you_sure_you_want_to_log_out));
            builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sessionManager.logoutSession();
                    moveToLogin();
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
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}