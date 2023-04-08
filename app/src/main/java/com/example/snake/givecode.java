package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class givecode extends AppCompatActivity {
    EditText givecode;
    ImageView givecodeimgbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_givecode);
        givecode = findViewById(R.id.givecodetxt);
        givecodeimgbtn = findViewById(R.id.givecodebtn);
        givecodeimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(givecode==null){
                    givecode.setError("Please enter the code");
                }
                if(givecode.getText().toString().equals("")){
                    givecode.setError("Please enter the code");
                }
                if(givecode.getText().toString().equals("1234")){
                    Intent intent = new Intent(givecode.this,release.class);
                    startActivity(intent);
                }
            }
        });
    }
}