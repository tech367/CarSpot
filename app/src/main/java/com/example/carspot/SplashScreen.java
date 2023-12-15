package com.example.carspot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser cUser;

    private Handler handler;

    @Override
    protected void onStart() {
        super.onStart();
        if (cUser != null)
            cUser.reload();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);




        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();
        handler = new Handler();




       if (cUser == null){
           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                 //  Toast.makeText(SplashScreen.this, "ok", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(SplashScreen.this, AuthScreenActivity.class));
               }
           } ,1500);
        } else {
            Toast.makeText(this, cUser.getEmail(), Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  //  Toast.makeText(SplashScreen.this, "ok", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashScreen.this, TabsActivity.class));
                }
            }, 1500);
        }

    }









}