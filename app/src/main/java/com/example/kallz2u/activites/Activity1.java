package com.example.kallz2u.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.databinding.Activity1Binding;



public class Activity1 extends AppCompatActivity {
    private Activity1Binding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageButton4.setOnClickListener(view -> startActivity(new Intent(
                Activity1.this,ThirdActivity.class)));

        binding.imageButton6.setOnClickListener(view -> startActivity(new Intent(
                Activity1.this,FifthActivity.class)));

    }
}
