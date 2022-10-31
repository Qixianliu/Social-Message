package com.example.kallz2u.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kallz2u.R;

public class AddNewGroupActivity extends AppCompatActivity {
    private ImageButton imageButton89;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);
        imageButton89 = findViewById(R.id.imageButton89);
        imageButton89.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
