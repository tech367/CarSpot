package com.example.carspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.carspot.databinding.AuthScreenBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private AuthScreenBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AuthScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("USER");




    }

    private void createUser() {
        if (!TextUtils.isEmpty(binding.editTextNameAuth.getText().toString()) && !TextUtils.isEmpty(binding.editTextNumberAuth.getText().toString()) &&
                !TextUtils.isEmpty(binding.editTextTextEmailAddress.getText().toString()) && !TextUtils.isEmpty(binding.editTextTextPassword.getText().toString())){
            mAuth.createUserWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(),
                    binding.editTextTextPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {


                    Toast.makeText(AuthScreenActivity.this, "Регистрация завершена!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AuthScreenActivity.this, LogInActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AuthScreenActivity.this, "Что-то пошло не так...", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickAuth(View view){
        reference = database.getReference("USER");
        User cUser = new User(binding.editTextNameAuth.getText().toString(),
                binding.editTextNumberAuth.getText().toString(),
                binding.editTextTextPassword.getText().toString(),
                binding.editTextTextEmailAddress.getText().toString());
        reference.push().setValue(cUser);
        createUser();

    }

    public void onClickHaveAcc(View view){
        startActivity(new Intent(AuthScreenActivity.this, LogInActivity.class));
        finish();
    }



}