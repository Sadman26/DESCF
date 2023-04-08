package com.example.snake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class givecode extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText givecode;
    ImageView givecodeimgbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_givecode);
        DatabaseReference rescueRef = FirebaseDatabase.getInstance().getReference("rescue");
        givecode = findViewById(R.id.givecodetxt);
        givecodeimgbtn = findViewById(R.id.givecodebtn);
        givecodeimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rescueRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Retrieve the children nodes
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            String childKey = childSnapshot.getKey();
                            // Compare each child node with the user input
                            if (childKey.equals(givecode.getText().toString())) {
                                // If a match is found, start the desired activity or intent
                                Intent intent = new Intent(givecode.this, release.class);

                                startActivity(intent);
                                break;
                            }
                            else{
                                // If no match is found, display a toast message
                                Toast.makeText(givecode.this, "Upps No Rescue!☺️", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}