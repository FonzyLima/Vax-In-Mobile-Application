package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminAddtoSched extends AppCompatActivity {
    private TextView tvDate, tvVenue;
    private Spinner spVenue;
    private RecyclerView rvAddtoSchedUserRow;
    private RecyclerView.LayoutManager adminAddManager;

    private UsersAdapter usersAdapter;
    private ArrayList<Users> usersArrayList;
    private Intent incomingIntent;
    private String date, venue;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference databaseReference = database.getReference().child("Users");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_addtosched);

        this.usersArrayList = new ArrayList<>();
        incomingIntent = getIntent();
        this.date = incomingIntent.getStringExtra("DateSelected");
        this.venue = incomingIntent.getStringExtra("VenueSelected");
        this.tvDate = findViewById(R.id.tv_addtosched_date);
        this.tvDate.setText(this.date);
        this.tvVenue = findViewById(R.id.tv_addtosched_venue);
        this.tvVenue.setText(this.venue);
        this.initRecyclerView();

    }

    private void initRecyclerView(){
        this.rvAddtoSchedUserRow = findViewById(R.id.rv_addtosched_userrow);
        this.adminAddManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        this.rvAddtoSchedUserRow.setLayoutManager(this.adminAddManager);

        this.usersAdapter = new UsersAdapter(this.usersArrayList);
        this.rvAddtoSchedUserRow.setAdapter(usersAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    if (user.isRegistered && !user.isScheduled && !user.isAdmin) {
                        usersArrayList.add(user);

                    }

                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
