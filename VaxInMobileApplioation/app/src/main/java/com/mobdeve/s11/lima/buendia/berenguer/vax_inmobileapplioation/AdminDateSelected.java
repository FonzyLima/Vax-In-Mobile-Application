package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class AdminDateSelected extends AppCompatActivity {
    private TextView tvDateSelected;
    private RecyclerView rvLocations;
    private RecyclerView.LayoutManager adminMainManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_date);

        this.tvDateSelected = findViewById(R.id.tv_date);


        Intent incomingIntent = getIntent();
        this.tvDateSelected.setText(incomingIntent.getStringExtra("DateSelected"));

        this.rvLocations = findViewById(R.id.rv_date_userrows);
        this.rvLocations.setLayoutManager(adminMainManager);

    }
}
