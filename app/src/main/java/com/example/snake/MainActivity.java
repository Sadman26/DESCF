package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    EditText rescue_snk_id,rescue_name,rescue_auth_name,rescue_loc;
    String randomid;
    ImageView rescue_photo;
    Button rescuebtn;
    Uri selectedImage;
    ProgressDialog dialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Rescue Page");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading...");
        dialog.setCancelable(false);
        rescuebtn = findViewById(R.id.rescuebtn);
        rescue_snk_id = findViewById(R.id.rescue_snk_id);
        rescue_name = findViewById(R.id.rescue_name);
        rescue_auth_name = findViewById(R.id.rescue_auth_name);
        rescue_loc = findViewById(R.id.rescue_loc);
        rescue_photo = findViewById(R.id.rescue_photo);
        rescue_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });
        rescuebtn.setOnClickListener(new View.OnClickListener() {
            String currtimeDate=java.text.DateFormat.getDateTimeInstance().format(java.util.Calendar.getInstance().getTime());
            @Override
            public void onClick(View view) {
                if (rescue_snk_id.getText().toString().isEmpty()) {
                    rescue_snk_id.setError("Enter Snake ID");
                    return;
                }
                if (rescue_name.getText().toString().isEmpty()) {
                    rescue_name.setError("Enter Snake Name");
                    return;
                }
                if (rescue_auth_name.getText().toString().isEmpty()) {
                    rescue_auth_name.setError("Enter  Authorised Name");
                    return;
                }
                dialog.show();
                if(selectedImage != null) {
                    StorageReference reference = storage.getReference().child("rescue").child(currtimeDate);
                    reference.putFile(selectedImage).addOnSuccessListener(taskSnapshot -> {
                        reference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String url = uri.toString();
                            String id = rescue_snk_id.getText().toString();
                            String name = rescue_name.getText().toString();
                            String auth_name = rescue_auth_name.getText().toString();
                            String loc = rescue_loc.getText().toString();
                            String randomid= java.util.UUID.randomUUID().toString().substring(0, 4);
                            Snake model = new Snake(id, name, auth_name, loc, url,currtimeDate,randomid);
                            String uid=database.getReference().push().getKey();
                            //database.getReference().child("release").child(randomid)
                            database.getReference().child("rescue").child(id).child(uid).setValue(model).addOnSuccessListener(aVoid -> {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "Rescue Added Successfully", Toast.LENGTH_SHORT).show();
                            });
                        });
                    });
                } else {
                    String id = rescue_snk_id.getText().toString();
                    String name = rescue_name.getText().toString();
                    String auth_name = rescue_auth_name.getText().toString();
                    String loc = rescue_loc.getText().toString();
                    String randomid= java.util.UUID.randomUUID().toString().substring(0, 4);
                    Snake model = new Snake(id, name, auth_name, loc, "No Image",currtimeDate,randomid);
                    String uid=database.getReference().push().getKey();
                    database.getReference().child("rescue").child(uid).setValue(model).addOnSuccessListener(aVoid -> {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Rescue Added Successfully ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, rescuecode.class);
                        intent.putExtra("randomid",randomid);
                        startActivity(intent);
                    });
                }


            }
        });
    }
}