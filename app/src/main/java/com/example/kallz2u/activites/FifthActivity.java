package com.example.kallz2u.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.databinding.ActivityFifthBinding;



public class FifthActivity extends AppCompatActivity {
    private ActivityFifthBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFifthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FifthActivity.this,LostActivity.class));
            }
        });
        binding.imageButton45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FifthActivity.this,COVID19Activity.class));
            }
        });
        binding.imageButton46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FifthActivity.this,unwellActivity.class));
            }
        });
        binding.imageButton55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FifthActivity.this,otherActivity.class));
            }
        });
    }
}
