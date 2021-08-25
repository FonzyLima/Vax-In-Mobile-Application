package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private Spinner spVenue;
    private ImageButton ibAddUser;
    private RecyclerView rvLocations;
    private RecyclerView.LayoutManager adminMainManager;

    private UsersAdapter usersAdapter;
    private ArrayList<Users> usersArrayList;
    private Intent incomingIntent;
    private String date;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference databaseReference = database.getReference().child("Users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_date);

        usersArrayList = new ArrayList<>();

        this.ibAddUser = findViewById(R.id.ib_date_addusers);
        this.spVenue = findViewById(R.id.spinner_date_venue);
        this.tvDateSelected = findViewById(R.id.tv_date_date);
        incomingIntent = getIntent();
        this.date = incomingIntent.getStringExtra("DateSelected");
        this.tvDateSelected.setText(date);

        this.ibAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDateSelected.this, AdminAddtoSched.class);
                intent.putExtra("DateSelected", date);
                startActivity(intent);
            }
        });
        this.initRecyclerView();

    }

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
                        if(user.firstSchedule == date && user.vacSite == spVenue.getSelectedItem().toString()){
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

