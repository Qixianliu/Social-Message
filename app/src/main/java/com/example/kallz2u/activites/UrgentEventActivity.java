package com.example.kallz2u.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kallz2u.R;

public class UrgentEventActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_event);
        textView=findViewById(R.id.txtWhat);

        String what=getIntent().getStringExtra("What");

        textView.setText(what);
    }
}