package com.example.snake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class release extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText releasebyname,releaseloc,releaseauthorbyname;
    ImageView releaseimgbtn;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Uri selectedImage;
    ProgressDialog dialog;
    Button releasebtn;
    String releasecode;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        dialog = new ProgressDialog(this);
        getSupportActionBar().setTitle("Release Page");
        releasebyname = findViewById(R.id.releasebyname);
        releaseauthorbyname = findViewById(R.id.releaseauthorbyname);
        releaseloc = findViewById(R.id.releaseloc);
        releaseimgbtn = findViewById(R.id.releaseimgbtn);
    }
}