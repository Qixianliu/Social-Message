package com.example.kallz2u.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kallz2u.databinding.ActivityFourthBinding;


public class FourthActivity extends AppCompatActivity {
    private ActivityFourthBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFourthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourthActivity.this,ChatActivity.class));
            }
        });
        binding.imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourthActivity.this,ChildMindingActivity.class));
            }
        });
        binding.imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourthActivity.this,GardeningActivity.class));
            }
        });
        binding.imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourthActivity.this, DoctorActivity.class));
            }
        });
        binding.imageButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourthActivity.this, WalkthedogActivity.class));
            }
        });
        binding.imageButton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourthActivity.this,CarBreakdownActivity.class));
            }
        });
    }
}
