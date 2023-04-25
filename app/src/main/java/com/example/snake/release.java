package com.example.snake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class release extends AppCompatActivity {
    FirebaseDatabase database=FirebaseDatabase.getInstance();

    DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("admin").child("release").push();
    DatabaseReference db2= FirebaseDatabase.getInstance().getReference().child("rescue");
    private static final int PICK_FIlE = 1;
    ArrayList<Uri> FileList=new ArrayList<Uri>();
    EditText releasebyname,releaseauthorbyname,releaseloc;
    String snake_id;
    TextView loll;
    int counter=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        //set title
        getSupportActionBar().setTitle("Release Page");
        releaseloc=findViewById(R.id.releaseloc);
        loll=findViewById(R.id.loll);
        loll.setText("Release Code: "+getIntent().getStringExtra("code"));
        releasebyname=findViewById(R.id.releasebyname);
        releaseauthorbyname=findViewById(R.id.releaseauthorbyname);
        database.getReference().child("rescue").child(getIntent().getStringExtra("code")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object snakeIdObj = snapshot.child("snakeid").getValue();
                if(snakeIdObj!=null){
                    snake_id=snakeIdObj.toString();
                }
                else{

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void chooseFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_FIlE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_FIlE && resultCode == RESULT_OK) {
            if(data.getClipData() != null) {
                // Multiple files selected
                int count = data.getClipData().getItemCount();
                int i = 0;
                while(i < count) {
                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    FileList.add(fileUri);
                    i++;
                }
                Toast.makeText(this, "You have selected " + FileList.size() + " files", Toast.LENGTH_SHORT).show();
            } else if(data.getData() != null) {
                // Single file selected
                Uri fileUri = data.getData();
                FileList.add(fileUri);
                Toast.makeText(this, "You have selected 1 file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void uploadFile(View view) {
        if(releasebyname.getText().toString().isEmpty()){
            releasebyname.setError("Please Enter Your Name");
            releasebyname.requestFocus();
            return;
        }
        if(releaseauthorbyname.getText().toString().isEmpty()){
            releaseauthorbyname.setError("Please Enter Authorizer Name");
            releaseauthorbyname.requestFocus();
            return;
        }
        if(releaseloc.getText().toString().isEmpty()){
            releaseloc.setError("Please Enter Location");
            releaseloc.requestFocus();
            return;
        }
        if(FileList.size()==0){
            Toast.makeText(this, "Please Select a File", Toast.LENGTH_SHORT).show();
            return;
        }
        String currtimeDate=java.text.DateFormat.getDateTimeInstance().format(java.util.Calendar.getInstance().getTime());
        for(int j=0;j<FileList.size();j++){
            Uri PerFile=FileList.get(j);
            StorageReference folder= FirebaseStorage.getInstance().getReference().child("release").child(getIntent().getStringExtra("code"));
            StorageReference filename=folder.child("(releasecode: "+getIntent().getStringExtra("code")+")"+PerFile.getLastPathSegment());
            filename.putFile(PerFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Snake model=new Snake(snake_id,releasebyname.getText().toString(),releaseauthorbyname.getText().toString(),releaseloc.getText().toString(),String.valueOf(uri),currtimeDate,getIntent().getStringExtra("code"));
                            db.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    counter++;
                                    if(counter==FileList.size()){
                                        Toast.makeText(release.this, "Successfully Released!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            db2.child(getIntent().getStringExtra("code")).removeValue();
                        }
                    });
                }
            });
        }
        Intent intent=new Intent(release.this,choose.class);
        startActivity(intent);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homee:
                Intent intent = new Intent(release.this,choose.class);
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