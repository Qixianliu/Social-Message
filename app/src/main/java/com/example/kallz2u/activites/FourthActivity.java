package com.example.kallz2u.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kallz2u.databinding.ActivityFourthBinding;


public class FourthActivity extends AppCompatActivity {
    private ActivityFourthBinding binding;
    String nonWhat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFourthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageButton.setOnClickListener(view -> onBackPressed());
        binding.imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Need a chat";
                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
                intent.putExtra("nonWhat",""+nonWhat);
                startActivity(intent);
            }
        });
        binding.imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Child Minding";
                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
                intent.putExtra("nonWhat",""+nonWhat);
                startActivity(intent);
            }
        });
        binding.imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Gardening";
                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
                intent.putExtra("nonWhat",""+nonWhat);
                startActivity(intent);
            }
        });
        binding.imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Go to Doctor";
                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
                intent.putExtra("nonWhat",""+nonWhat);
                startActivity(intent);
            }
        });
        binding.imageButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Walk the Dog";
                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
                intent.putExtra("nonWhat",""+nonWhat);
                startActivity(intent);
            }
        });
        binding.imageButton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonWhat = "Car Breakdown";
                Intent intent = new Intent(getApplicationContext(),NonUrgentEventActivity.class);
                intent.putExtra("nonWhat",""+nonWhat);
                startActivity(intent);
            }
        });
    }
}
