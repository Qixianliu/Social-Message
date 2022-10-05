package com.example.kallz2u.activites;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.Fragment.*;

import com.example.kallz2u.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.MenuHome);
    }

    HomeFragment homeFragment = new HomeFragment();
    GroupFragment groupFragment = new GroupFragment();
    ContactFragment contactFragment = new ContactFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.MenuGroup:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, groupFragment).commit();
                return true;

            case R.id.MenuContact:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, contactFragment).commit();
                return true;

            case R.id.MenuProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                return true;
        }
        return false;
    }
}