package com.example.projectuas_petshop.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.databinding.ActivityAddAnimalBinding;

public class AddAnimalActivity extends AppCompatActivity {

    private ActivityAddAnimalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAnimalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String select;
                if (binding.autoCompleteText.getText().toString().isEmpty()){
                    Toast.makeText(AddAnimalActivity.this, "Choose", Toast.LENGTH_SHORT).show();
                } else {
                    select = binding.autoCompleteText.getText().toString();
                    binding.txtSelect.setText(select);
                    Toast.makeText(AddAnimalActivity.this, binding.autoCompleteText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}