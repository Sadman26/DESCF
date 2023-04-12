package com.example.snake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class release extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("rescue");
    TextView loll;
    EditText releasebyname,releaseloc,releaseauthorbyname;
    ImageView releaseimgbtn;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Uri selectedImage;
    ProgressDialog dialog;
    Button releasebtn;
    String snake_id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            releaseimgbtn.setImageURI(selectedImage);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Releasing...");
        dialog.setCancelable(false);
        getSupportActionBar().setTitle("Release Page");
        loll = findViewById(R.id.loll);
        loll.setText("Release Code: "+getIntent().getStringExtra("code"));
        releasebyname = findViewById(R.id.releasebyname);
        releaseauthorbyname = findViewById(R.id.releaseauthorbyname);
        releaseloc = findViewById(R.id.releaseloc);
        releasebtn = findViewById(R.id.releasebtn);
        releaseimgbtn = findViewById(R.id.releaseimgbtn);

        database.getReference().child("rescue").child(getIntent().getStringExtra("code")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snake_id = snapshot.child("snakeid").getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        releaseimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });


        releasebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String currtimeDate=java.text.DateFormat.getDateTimeInstance().format(java.util.Calendar.getInstance().getTime());

                if (releasebyname.getText().toString().isEmpty()) {
                    releasebyname.setError("Enter Rescuers Name");
                    return;
                }
                if (releaseauthorbyname.getText().toString().isEmpty()) {
                    releaseauthorbyname.setError("Enter  Authorised Name");
                    return;
                }
                if (releaseloc.getText().toString().isEmpty()) {
                    releaseloc.setError("Enter Location");
                    return;
                }
                dialog.show();
                if(selectedImage != null) {
                    String finalrelease=snake_id+"("+currtimeDate+" )";
                    StorageReference reference = storage.getReference().child("release").child(finalrelease);
                    reference.putFile(selectedImage).addOnSuccessListener(taskSnapshot -> {
                        reference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String url = uri.toString();
                            String name = releasebyname.getText().toString();
                            String auth_name = releaseauthorbyname.getText().toString();
                            String loc = releaseloc.getText().toString();
                            Snake model = new Snake(snake_id,name, auth_name, loc, url,currtimeDate);
                            String uid=database.getReference().push().getKey();
                            database.getReference().child("admin").child("release").child(uid).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Toast.makeText(release.this, "Successfully Released! ❤ 😊", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(release.this, choose.class);
                                    startActivity(intent);
                                }
                            });
                            database.getReference().child("rescue").child(getIntent().getStringExtra("code")).removeValue();
                        });
                    });
                } else {
                    String name = releasebyname.getText().toString();
                    String auth_name = releaseauthorbyname.getText().toString();
                    String loc = releaseloc.getText().toString();
                    Snake model = new Snake(snake_id,name, auth_name, loc, "No Image",currtimeDate);
                    String uid=database.getReference().push().getKey();
                    database.getReference().child("admin").child("release").child(uid).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            Toast.makeText(release.this, "Successfully Released! ❤", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(release.this, choose.class);
                            startActivity(intent);
                        }
                    });
                    database.getReference().child("rescue").child(getIntent().getStringExtra("code")).removeValue();
                }


            }
        });















































    }
}