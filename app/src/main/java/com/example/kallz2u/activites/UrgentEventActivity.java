package com.example.kallz2u.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kallz2u.R;

public class UrgentEventActivity extends AppCompatActivity {
    ImageButton imageButton;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_event);
        textView=findViewById(R.id.txtWhat);
        imageButton=findViewById(R.id.imageButton);
        imageButton.setOnClickListener(view -> onBackPressed());
        String what=getIntent().getStringExtra("What");

        textView.setText(what);
    }
}