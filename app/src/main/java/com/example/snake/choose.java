package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choose extends AppCompatActivity {
    Button rescuebtn,releasebtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        getSupportActionBar().setTitle("Welcome 🐍");
        rescuebtn = findViewById(R.id.rescubtn);
        releasebtn = findViewById(R.id.relesbtn);
        rescuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(choose.this,MainActivity.class);
                startActivity(intent);
            }
        });
        releasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(choose.this,givecode.class);
                startActivity(intent);
            }
        });
    }
}