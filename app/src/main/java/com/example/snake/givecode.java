package com.example.snake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        //set title
        getSupportActionBar().setTitle("Give Rescued Code");
        DatabaseReference rescueRef = FirebaseDatabase.getInstance().getReference("rescue");
        givecode = findViewById(R.id.givecodetxt);
        givecodeimgbtn = findViewById(R.id.givecodebtn);
        givecodeimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rescueRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Set a flag to keep track of whether a matching code was found
                        boolean codeFound = false;

                        // Retrieve the children nodes
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            String childKey = childSnapshot.getKey();
                            if (childKey.equals(givecode.getText().toString())) {
                                codeFound = true;
                                Toast.makeText(givecode.this, "Welcome ❤ !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(givecode.this, release.class);
                                intent.putExtra("code", givecode.getText().toString());
                                startActivity(intent);
                                break;
                            }
                        }

                        // Display "No rescue found" message only if no matching code was found
                        if (!codeFound) {
                            Toast.makeText(givecode.this, "Upps No Rescue!☺️", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homee:
                Intent intent = new Intent(givecode.this,choose.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);
        return true;
    }
}