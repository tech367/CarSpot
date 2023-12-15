package com.example.carspot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.carspot.databinding.ActivityCurrentOfferBinding;
import com.example.carspot.databinding.ActivityOffersBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CurrentOfferActivity extends AppCompatActivity {

    private ActivityCurrentOfferBinding binding;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("OFFER");

    private DatabaseReference RefDes = database.getReference("DESICION");

    private NotificationChannel channel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrentOfferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String car_name = intent.getStringExtra("carName");
        String offer_number = intent.getStringExtra("offerNumber");
        String user_name = intent.getStringExtra("userName");


        binding.carName.setText(car_name);
        binding.userName.setText(user_name);
        binding.offerNumber.setText(offer_number);

        channel = new NotificationChannel("test_channel", "test_description", NotificationManager.IMPORTANCE_MAX);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        binding.buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CurrentOfferActivity.this, "Заявка пользователя одобрена", Toast.LENGTH_SHORT).show();
                OfferDesicion des = new OfferDesicion(true);
                myRef.child(car_name).removeValue();
                RefDes.child(car_name).setValue(des);
                String mailto = "mailto:" + user_name +
                        "?cc=" +
                        "&subject=" + Uri.encode("Car Spot") +
                        "&body=" + Uri.encode("Заявка одобрена!");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(CurrentOfferActivity.this, "Error to open email app", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        binding.buttonDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CurrentOfferActivity.this, "Заявка пользователя отклонена", Toast.LENGTH_SHORT).show();
                myRef.child(car_name).removeValue();
                OfferDesicion des = new OfferDesicion(false);
                RefDes.child(car_name).setValue(des);

                String mailto = "mailto:" + user_name +
                        "?cc=" +
                        "&subject=" + Uri.encode("Car Spot") +
                        "&body=" + Uri.encode("Заявка отклонена!");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(CurrentOfferActivity.this, "Error to open email app", Toast.LENGTH_SHORT).show();
                }
                    

                finish();
            }
        });




    }
}