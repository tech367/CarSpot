package com.example.carspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.carspot.databinding.ActivityOffersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity {

    private ActivityOffersBinding bindingA;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("OFFER");

    private ArrayList<Offer> offerArrayList;

    private OfferAdapter offerAdapter;

    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingA = ActivityOffersBinding.inflate(getLayoutInflater());
        setContentView(bindingA.getRoot());


        offerArrayList =  new ArrayList<>();
        offerAdapter = new OfferAdapter(this, R.layout.offers_list_item, offerArrayList);

        bindingA.ewOffers.setAdapter(offerAdapter);

        bindingA.ewOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(bindingA.ewOffers.getChildAt(position), "translationX", 70f, 0f);
                animation.setDuration(200);
                animation.start();

                Intent intent = new Intent(OffersActivity.this, CurrentOfferActivity.class);
                String offer_number = String.valueOf(offerArrayList.get(position).getId());
                String car_name = offerArrayList.get(position).getOfferedCarName();
                String user_name = offerArrayList.get(position).getPersonName();
                intent.putExtra("offerNumber", offer_number );
                intent.putExtra("carName", car_name);
                intent.putExtra("userName", user_name);
                intent.putExtra("numberOfOffer", position);

                startActivity(intent);
            }
        });



    getDataFromDB();






    }

    private void getDataFromDB(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (offerArrayList.size()>0) offerArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Offer offer = ds.getValue(Offer.class);
                    id = ds.getKey();
                    assert offer != null;
                    offerArrayList.add(offer);
                }

                offerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.addValueEventListener(valueEventListener);
    }
}