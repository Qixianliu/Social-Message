package com.example.kallz2u.activites;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.databinding.ActivityTermsAndConditionsBinding;

public class Terms_and_conditionsActivity extends AppCompatActivity {
    private ActivityTermsAndConditionsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsAndConditionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageButton128.setOnClickListener(view -> onBackPressed());
    }
}
