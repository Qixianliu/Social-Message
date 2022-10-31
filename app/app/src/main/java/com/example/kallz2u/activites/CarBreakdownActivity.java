package com.example.kallz2u.activites;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kallz2u.databinding.ActivityCarBreakdownBinding;

public class CarBreakdownActivity extends AppCompatActivity {

    private ActivityCarBreakdownBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarBreakdownBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}