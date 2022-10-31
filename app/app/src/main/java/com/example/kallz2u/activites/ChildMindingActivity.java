package com.example.kallz2u.activites;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.databinding.Activity1Binding;
import com.example.kallz2u.databinding.ActivityChildMindingBinding;




public class ChildMindingActivity extends AppCompatActivity {
    private ActivityChildMindingBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildMindingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
