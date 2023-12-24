package com.example.carspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;

public class TabsActivity extends AppCompatActivity {

    RelativeLayout cars_layout;
    RelativeLayout information_layout;
    RelativeLayout settings_layout;
    RelativeLayout add_car_layout;

    RelativeLayout show_offer_layout;

    ImageView log_out;


    boolean out;

    private FirebaseAuth mAuth;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference RefDes = database.getReference("DESICION");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        mAuth = FirebaseAuth.getInstance();




        log_out = findViewById(R.id.imageViewLogOut);
        cars_layout = findViewById(R.id.car_card);
        information_layout = findViewById(R.id.information_card);
        settings_layout = findViewById(R.id.account_card);
        add_car_layout = findViewById(R.id.addCar_layout);
        show_offer_layout = findViewById(R.id.show_offers_layout);



        if (mAuth.getCurrentUser().getEmail().toString().equals("anna@gmail.com")
                || mAuth.getCurrentUser().getEmail().toString().equals("admin@gmail.com")
                || mAuth.getCurrentUser().getEmail().toString().equals("postikaartem4@gmail.com")) {

            information_layout.setVisibility(View.GONE);
            settings_layout.setVisibility(View.GONE);
            add_car_layout.setVisibility(View.VISIBLE);
            show_offer_layout.setVisibility(View.VISIBLE);
        } else {
            information_layout.setVisibility(View.VISIBLE);
            settings_layout.setVisibility(View.VISIBLE);
            add_car_layout.setVisibility(View.GONE);
            show_offer_layout.setVisibility(View.GONE);
        }

        cars_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(cars_layout, "translationX", 70f, 0f);
                animation.setDuration(250);
                animation.start();
                startActivity(new Intent(TabsActivity.this, CarsActivity.class));
            }
        });

        information_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(information_layout, "translationX", 70f, 0f);
                animation.setDuration(200);
                animation.start();
                startActivity(new Intent(TabsActivity.this, InformationActivity.class));
            }
        });


        settings_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(settings_layout, "translationX", 70f, 0f);
                animation.setDuration(200);
                animation.start();
                startActivity(new Intent(TabsActivity.this, SettingsActivity.class));
            }
        });

        add_car_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(add_car_layout, "translationX", 70f, 0f);
                animation.setDuration(200);
                animation.start();
                startActivity(new Intent(TabsActivity.this, AddCarActivity.class));
            }
        });

        show_offer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(show_offer_layout, "translationX", 70f, 0f);
                animation.setDuration(200);
                animation.start();
                startActivity(new Intent(TabsActivity.this, OffersActivity.class));
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TabsActivity.this, LogInActivity.class));
                finish();
            }
        });

    }


}