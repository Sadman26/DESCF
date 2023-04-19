package com.example.snake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class MainActivity  extends AppCompatActivity {

    DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("admin").child("rescue").push();
    DatabaseReference db2= FirebaseDatabase.getInstance().getReference().child("rescue");
    private static final int PICK_FIlE = 1;
    ArrayList<Uri> FileList=new ArrayList<Uri>();
    EditText rescue_snk_id,rescue_name,rescue_auth_name,rescue_loc;
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Rescue Page");
        setContentView(R.layout.activity_main);
        rescue_snk_id=findViewById(R.id.rescue_snk_id);
        rescue_name=findViewById(R.id.rescue_name);
        rescue_auth_name=findViewById(R.id.rescue_auth_name);
        rescue_loc=findViewById(R.id.rescue_loc);
    }

    public void chooseFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_FIlE);
    }

    @Override
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
        if(rescue_snk_id.getText().toString().isEmpty()){
            rescue_snk_id.setError("Please Enter Snake ID");
            rescue_snk_id.requestFocus();
            return;
        }
        if(rescue_name.getText().toString().isEmpty()){
            rescue_name.setError("Please Enter Rescuer Name");
            rescue_name.requestFocus();
            return;
        }
        if(rescue_auth_name.getText().toString().isEmpty()){
            rescue_auth_name.setError("Please Enter  Authoriser Name");
            rescue_auth_name.requestFocus();
            return;
        }
        if(rescue_loc.getText().toString().isEmpty()){
            rescue_loc.setError("Please Enter Snake Location");
            rescue_loc.requestFocus();
            return;
        }
        if(FileList.size()==0){
            Toast.makeText(this, "Please Select Files", Toast.LENGTH_SHORT).show();
            return;
        }
        String currtimeDate=java.text.DateFormat.getDateTimeInstance().format(java.util.Calendar.getInstance().getTime());
        String randomid= java.util.UUID.randomUUID().toString().substring(0, 5);
        for(int j=0;j<FileList.size();j++){
            Uri PerFile=FileList.get(j);
            StorageReference folder= FirebaseStorage.getInstance().getReference().child("rescue").child(randomid);
            StorageReference filename=folder.child("(rescuecode: "+randomid+")"+PerFile.getLastPathSegment());
            filename.putFile(PerFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Snake model=new Snake(rescue_snk_id.getText().toString(),rescue_name.getText().toString(),rescue_auth_name.getText().toString(),rescue_loc.getText().toString(),String.valueOf(uri),currtimeDate,randomid);
                            historymodel model2=new historymodel(rescue_snk_id.getText().toString(),randomid);
                            db.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    counter++;
                                    if(counter==FileList.size()){
                                        Toast.makeText(MainActivity.this, "Successfully Rescued!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            db2.child(randomid).setValue(model2);
                        }
                    });
                }
            });

        }
        Intent intent = new Intent(MainActivity.this, rescuecode.class);
        intent.putExtra("randomid",randomid);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homee:
                Intent intent = new Intent(MainActivity.this,choose.class);
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