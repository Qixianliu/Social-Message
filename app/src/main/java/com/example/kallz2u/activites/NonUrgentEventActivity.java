package com.example.kallz2u.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kallz2u.R;

public class NonUrgentEventActivity extends AppCompatActivity {
    ImageButton imageButton;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_urgent_event);
        textView=findViewById(R.id.txtNon);
        imageButton=findViewById(R.id.imageButton);
        imageButton.setOnClickListener(view -> onBackPressed());
        String nonWhat = getIntent().getStringExtra("nonWhat");

        textView.setText(nonWhat);
    }
}