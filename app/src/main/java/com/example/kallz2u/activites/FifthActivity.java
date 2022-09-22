package com.example.kallz2u.activites;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.databinding.ActivityFifthBinding;
import com.example.kallz2u.databinding.ActivityThirdBinding;



public class FifthActivity extends AppCompatActivity {
    private ActivityFifthBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFifthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
