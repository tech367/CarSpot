package com.example.carspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarsActivity extends AppCompatActivity {

    ListView car_list_view;


    DatabaseReference mDataBase;

    String CAR_KEY = "CAR";

    ArrayList<Car> arrayCarList;

    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);

        car_list_view = findViewById(R.id.car_listview);
        mDataBase = FirebaseDatabase.getInstance().getReference(CAR_KEY);
        arrayCarList = new ArrayList<>();
        listAdapter = new ListAdapter(this, R.layout.car_listview_item, arrayCarList);
        car_list_view.setAdapter(listAdapter);

        getDataFromDB();



        car_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(car_list_view.getChildAt(position), "translationX", 70f, 0f);
                animation.setDuration(200);
                animation.start();
                String str = arrayCarList.get(position).getName();
                String description = arrayCarList.get(position).getDescription();
                Intent intent = new Intent(CarsActivity.this, CurrentCarActivity.class);
                intent.putExtra("car_name", str);
                intent.putExtra("description", description);
                startActivity(intent);





            }
        });



    }
    private void getDataFromDB(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (arrayCarList.size()>0) arrayCarList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Car car = ds.getValue(Car.class);
                    assert car !=null;
                    arrayCarList.add(car);
                }

                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(valueEventListener);
    }





}
