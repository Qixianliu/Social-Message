package com.example.kallz2u.activites;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.databinding.ActivityChildMindingBinding;
import com.example.kallz2u.databinding.ActivityThirdBinding;



public class ThirdActivity extends AppCompatActivity {
    private ActivityThirdBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
