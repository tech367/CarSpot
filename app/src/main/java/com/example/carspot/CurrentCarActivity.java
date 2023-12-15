package com.example.carspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.carspot.databinding.ActivityCurrentCarBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class CurrentCarActivity extends AppCompatActivity {

    private ActivityCurrentCarBinding binding;

    private StorageReference storageReference;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("OFFER");

    private FirebaseAuth mAuth;
    private FirebaseUser cUser;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrentCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        Intent intent = getIntent();
        String car_name = intent.getStringExtra("car_name");
        String description = intent.getStringExtra("description");
        binding.carDescription.setText(description);
        binding.textViewcarName.setText(car_name);

        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();


        storageReference = storage.getReference().child("images/"+car_name+".jpeg");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(binding.imageViewCurrentCar);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.imageViewCurrentCar.setImageResource(R.drawable.white_car_square);
            }
        });

        binding.buttonCreateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = myRef.push().getKey();
                Offer offer = new Offer(id,car_name.toString(),cUser.getEmail());
                myRef.child(car_name.toString()).setValue(offer);
                finish();
                Toast.makeText(CurrentCarActivity.this, "Заявка отправлена!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}