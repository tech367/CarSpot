package com.example.carspot;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class TestActivity extends AppCompatActivity {

    StorageReference reference;
    Bitmap bitImage;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        img = findViewById(R.id.imageView322);


        reference = FirebaseStorage.getInstance().getReference("images/bmw z4.jpg");

        File localFile;

        {
            try {
                localFile = File.createTempFile("tempfile",".jpg");
                reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        bitImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        //img.setImageBitmap(bitImage);

                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}