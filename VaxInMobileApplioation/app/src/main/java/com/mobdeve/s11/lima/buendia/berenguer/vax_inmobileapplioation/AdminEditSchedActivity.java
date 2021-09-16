package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AdminEditSchedActivity extends AppCompatActivity {
    private TextView tvDate, tvTime, tvVenue;
    private Spinner spFilter;

    private RecyclerView rvDelSchedRow;
    private RecyclerView.LayoutManager adminDelManager;

    private UsersDeleteAdapter usersDeleteAdapter;
    private ArrayList<Users> usersArrayList;
    private Intent incomingIntent;
    private String date, time, venue;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference databaseReference = database.getReference().child("Users");

    /**
     * {@inheritDoc}
     * <p>
     * Perform initialization of all fragments.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_editsched);

        this.usersArrayList = new ArrayList<>();
        this.incomingIntent = getIntent();

        this.date = incomingIntent.getStringExtra("DateSelected");
        this.venue = incomingIntent.getStringExtra("VenueSelected");
        this.time = incomingIntent.getStringExtra("TimeSelected");

        this.tvDate = findViewById(R.id.tv_editsched_date);
        this.tvDate.setText(this.date);
        this.tvVenue = findViewById(R.id.tv_editsched_venue);
        this.tvVenue.setText(this.venue);
        this.tvTime = findViewById(R.id.tv_editsched_time);
        this.tvTime.setText(this.time);
        this.initRecyclerview();

//        this.spFilter = findViewById(R.id.spinner_editsched_filter);
//        this.spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String chosenFilter = spFilter.getSelectedItem().toString().substring(0,2);
//                if(position != 0){
//                    usersArrayList.clear();
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                Users user = dataSnapshot.getValue(Users.class);
//                                if (user.isRegistered && !user.isScheduled && !user.isAdmin && user.priority.equals(chosenFilter)) {
//                                    usersArrayList.add(user);
//                                }
//
//                            }
//                            usersDeleteAdapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                }
//                else{
//                    usersArrayList.clear();
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                Users user = dataSnapshot.getValue(Users.class);
//                                if (user.isRegistered && !user.isScheduled && !user.isAdmin) {
//                                    usersArrayList.add(user);
//                                }
//
//                            }
//                            usersDeleteAdapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });





    }

    private void initRecyclerview(){
        this.rvDelSchedRow = findViewById(R.id.rv_editsched_userrow);
        this.adminDelManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        this.rvDelSchedRow.setLayoutManager(this.adminDelManager);

        this.usersDeleteAdapter = new UsersDeleteAdapter(this.usersArrayList);
        this.rvDelSchedRow.setAdapter(usersDeleteAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    if (user.isRegistered && user.isScheduled && !user.isAdmin) {
                        if(user.firstSchedule.equals(date) || user.secondSchedule.equals(date)){
                            usersArrayList.add(user);
                        }


                    }

                }
                usersDeleteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
