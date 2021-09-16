package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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


public class AdminDateSelected extends AppCompatActivity {
    private TextView tvDateSelected;
    private Spinner spVenue, spTime;
    private ImageButton ibAddUser, ibEditUser;
    private RecyclerView rvLocations;
    private RecyclerView.LayoutManager adminMainManager;

    private UsersAdapter usersAdapter;
    private ArrayList<Users> usersArrayList;
    private Intent incomingIntent;
    private String date, time, secondDate, venue;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference databaseReference = database.getReference().child("Users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_date);

        usersArrayList = new ArrayList<>();

        this.ibAddUser = findViewById(R.id.ib_date_addusers);
        this.ibEditUser = findViewById(R.id.ib_date_editusers);
        this.spVenue = findViewById(R.id.spinner_date_venue);
        this.spTime = findViewById(R.id.spinner_date_time);
        this.tvDateSelected = findViewById(R.id.tv_date_date);
        incomingIntent = getIntent();
        this.date = incomingIntent.getStringExtra("DateSelected");
        this.secondDate = incomingIntent.getStringExtra("SeconDoseDate");
        this.tvDateSelected.setText(date);

        this.initRecyclerView();

        /*
        Filters the users to the ones who are scheduled to be vaccinated at the selected site
         */
        spVenue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                venue = spVenue.getSelectedItem().toString();
                if(position != 0){
                    usersArrayList.clear();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Users user = dataSnapshot.getValue(Users.class);
                                if (user.isRegistered && !user.isScheduled && !user.isAdmin && user.vacSite.equals(venue)) {
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        Filters the users to the ones who are scheduled to be vaccinated at the selected site and selected time
         */
        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time = spTime.getSelectedItem().toString();
                if(position != 0){
                    usersArrayList.clear();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Users user = dataSnapshot.getValue(Users.class);
                                if (user.isRegistered && !user.isScheduled && !user.isAdmin && user.vacSite.equals(venue) && user.firstTime.equals(time) || user.secondTime.equals(time)) {
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        Redirects user to AdminAddtoSched activity while passing the selected date, venue, and time.
         */
        this.ibAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminDateSelected.this, AdminAddtoSched.class);
                intent.putExtra("DateSelected", date);
                intent.putExtra("TimeSelected", time);
                intent.putExtra("SeconDoseDate", secondDate);
                intent.putExtra("VenueSelected", venue);
                startActivity(intent);
            }
        });

        this.ibEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminDateSelected.this, AdminEditSchedActivity.class);
                intent.putExtra("DateSelected", date);
                intent.putExtra("TimeSelected", time);
                intent.putExtra("SeconDoseDate", secondDate);
                intent.putExtra("VenueSelected", venue);
                startActivity(intent);
            }
        });




    }

    /*
    Initializes recyclerview with default list of users who are scheduled to be vaccinated on the selected date
     */
    private void initRecyclerView() {

        this.rvLocations = findViewById(R.id.rv_date_userrow);
        this.adminMainManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.rvLocations.setLayoutManager(this.adminMainManager);

        this.usersAdapter = new UsersAdapter(this.usersArrayList);
        this.rvLocations.setAdapter(usersAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    if (user.isRegistered && user.isScheduled && !user.isAdmin) {
                        if(user.firstSchedule == date || user.secondSchedule == date){
                            usersArrayList.add(user);
                        }

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

