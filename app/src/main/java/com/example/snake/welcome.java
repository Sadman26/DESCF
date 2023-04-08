package com.example.snake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class welcome extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText loginkey;
    ImageView loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        loginkey = findViewById(R.id.loginkey);
        loginbtn = findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference().child("admin").child("key").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(welcome.this, "Login Successful 💚", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(welcome.this, choose.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(welcome.this, "Upps Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}