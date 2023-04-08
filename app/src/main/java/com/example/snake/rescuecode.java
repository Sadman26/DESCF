package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class rescuecode extends AppCompatActivity {
    TextView rescuecodex;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescuecode);
        getSupportActionBar().setTitle("Rescue Code");
        String code= getIntent().getStringExtra("randomid");
        rescuecodex = findViewById(R.id.rescuecodex);
        rescuecodex.setText(code);
    }
}