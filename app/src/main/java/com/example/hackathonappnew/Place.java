package com.example.hackathonappnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Place extends AppCompatActivity {
    Spinner PlaceSpinner, NumberSpinner;
    ArrayList<String> routesSpinner, numbersList;
    ArrayAdapter<String> RouteAdapter, NumberAdapter;
    DatabaseReference routeref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        PlaceSpinner = findViewById(R.id.spinerplace);
        NumberSpinner = findViewById(R.id.numberspinner);

        // Initialize ArrayLists
        routesSpinner = new ArrayList<>();
        numbersList = new ArrayList<>();

        // Initialize Adapters
        RouteAdapter = new ArrayAdapter<>(Place.this, R.layout.spinner_layout, routesSpinner);
        RouteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PlaceSpinner.setAdapter(RouteAdapter);

        NumberAdapter = new ArrayAdapter<>(Place.this, android.R.layout.simple_spinner_item, numbersList);
        NumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NumberSpinner.setAdapter(NumberAdapter);

        // Firebase database reference
        routeref = FirebaseDatabase.getInstance().getReference("places");

        // Populate spinner with numbers from 1 to 20
        for (int i = 1; i <= 10; i++) {
            numbersList.add(String.valueOf(i));
        }
        NumberAdapter.notifyDataSetChanged();

        // Populate spinner with data from Firebase
        routeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                routesSpinner.clear();
                routesSpinner.add("Select Your Route");
                for (DataSnapshot item : snapshot.getChildren()) {
                    routesSpinner.add(item.getKey());
                }
                RouteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }
}
