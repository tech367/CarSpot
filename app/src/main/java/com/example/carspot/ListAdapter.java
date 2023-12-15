package com.example.carspot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Car> {

    private Context mContext;
    private int mResource;


    private StorageReference storageReference;
    private FirebaseStorage storage = FirebaseStorage.getInstance();







    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Car> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView car_image = convertView.findViewById(R.id.car_image);
        TextView car_name = convertView.findViewById(R.id.car_model);
        TextView car_year = convertView.findViewById(R.id.car_year_value);
        TextView car_reach = convertView.findViewById(R.id.car_reach_value);
        TextView car_power = convertView.findViewById(R.id.car_power_value);


        storageReference = storage.getReference().child("images/"+getItem(position).getName().toString()+".jpeg");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(car_image);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                car_image.setImageResource(R.drawable.white_car_square);
            }
        });






            car_name.setText(getItem(position).getName());
            car_year.setText(getItem(position).getYear());
            car_reach.setText(getItem(position).getReach());
            car_power.setText(getItem(position).getPower());



            return convertView;
    }




}

