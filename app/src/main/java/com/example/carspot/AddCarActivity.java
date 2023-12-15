package com.example.carspot;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


import com.example.carspot.databinding.ActivityAddCarBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AddCarActivity extends AppCompatActivity {


    private ActivityAddCarBinding binding;
    private Uri imageUri;
    private StorageReference storageReference;
    //private String fileName;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


       binding.imageViewSelectCar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               selectImage();
           }
       });


       binding.pushCarBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressDialog = new ProgressDialog(AddCarActivity.this);
               progressDialog.setMessage("Загрузка информации...");
               progressDialog.setCancelable(false);
               progressDialog.show();

               uploadImage();
               pushData();
               finish();
           }
       });



    }

    private void uploadImage() {
        storageReference = FirebaseStorage.getInstance().getReference("images/"
                +binding.editTextCarName.getText().toString()+".jpeg");

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddCarActivity.this, "Uploaded succesfully", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCarActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    private void pushData(){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("CAR");
        Car car = new Car(binding.editTextCarName.getText().toString(), binding.editTextCarYear.getText().toString(),
                binding.editTextCarReach.getText().toString(), binding.editTextCarPower.getText().toString(),
                binding.editTextCarDescription.getText().toString());
        databaseReference.push().setValue(car);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            binding.imageViewSelectCar.setImageURI(imageUri);
        }
    }
}


