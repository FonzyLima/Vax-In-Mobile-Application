package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

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

public class AdminMainActivity extends AppCompatActivity {
    private CalendarView cvScheduler;
    private RecyclerView rvUsers;
    private RecyclerView.LayoutManager adminMainManager;
    private UsersAdapter usersAdapter;
    private ArrayList<Users> usersArrayList;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference databaseReference = database.getReference().child("Users");


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        this.cvScheduler = findViewById(R.id.cv_scheduler);
        usersArrayList = new ArrayList<>();

        this.cvScheduler.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = month+1 +"/"+dayOfMonth+"/"+year;
                Intent intent = new Intent(AdminMainActivity.this, AdminDateSelected.class);
                intent.putExtra("DateSelected", date);
                startActivity(intent);
            }
        });
        this.initRecyclerView();

    }

    private void initRecyclerView(){
        this.rvUsers = findViewById(R.id.rv_main_applications);

        this.adminMainManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.rvUsers.setLayoutManager(adminMainManager);

        this.usersAdapter = new UsersAdapter(this.usersArrayList);
        this.rvUsers.setAdapter(this.usersAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users user = dataSnapshot.getValue(Users.class);
                    if(!user.isRegistered){
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
