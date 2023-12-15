package com.example.carspot;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.Resource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OfferAdapter extends ArrayAdapter<Offer> {
    private Context mContext;
    private int mResource;


    private StorageReference storageReference;
    private FirebaseStorage storage = FirebaseStorage.getInstance();



    public OfferAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Offer> offersList) {
        super(context, resource, offersList);
        this.mContext = context;
        this.mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView car_image = convertView.findViewById(R.id.imageViewOfferedCar);
        TextView offered_car = convertView.findViewById(R.id.textViewOfferedCar);
        TextView offer_id = convertView.findViewById(R.id.textViewOfferNumber);
        TextView user_name = convertView.findViewById(R.id.textViewUserName);

        storageReference = storage.getReference().child("images/"+getItem(position).getOfferedCarName().toString()+".jpeg");


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

        offered_car.setText(getItem(position).getOfferedCarName());
        offer_id.setText(getItem(position).getId());
        user_name.setText(getItem(position).getPersonName());


        return convertView;
    }
}
