package com.example.kallz2u.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.databinding.ActivityFifthBinding;



public class FifthActivity extends AppCompatActivity {
    private ActivityFifthBinding binding;
    String what;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFifthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageButton.setOnClickListener(view -> onBackPressed());

        binding.imageButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = "I'm Lost";
                Intent intent = new Intent(getApplicationContext(),UrgentEventActivity.class);
                intent.putExtra("What",""+what);
                startActivity(intent);
            }
        });
        binding.imageButton45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = "COVID-19";
                Intent intent = new Intent(getApplicationContext(),UrgentEventActivity.class);
                intent.putExtra("What",""+what);
                startActivity(intent);
            }
        });
        binding.imageButton46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = "I'm unwell";
                Intent intent = new Intent(getApplicationContext(),UrgentEventActivity.class);
                intent.putExtra("What",""+what);
                startActivity(intent);
            }
        });
        binding.imageButton55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = "Others";
                Intent intent = new Intent(getApplicationContext(),UrgentEventActivity.class);
                intent.putExtra("What",""+what);
                startActivity(intent);
            }
        });
    }
}
