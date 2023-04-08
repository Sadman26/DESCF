package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class release extends AppCompatActivity {
    EditText releasebyname,releaseloc;
    ImageView releaseimgbtn;
    Button releasebtn;
    String releasecode;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        getSupportActionBar().setTitle("Release Page");
        releasecode = getIntent().getStringExtra("releasecode");
        releasebyname = findViewById(R.id.releasebyname);
        releaseloc = findViewById(R.id.releaseloc);
        releaseimgbtn = findViewById(R.id.releaseimgbtn);

    }
}