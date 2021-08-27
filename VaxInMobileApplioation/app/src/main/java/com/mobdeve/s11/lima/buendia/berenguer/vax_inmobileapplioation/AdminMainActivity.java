package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class AdminMainActivity extends AppCompatActivity {
    private CalendarView cvScheduler;
    private ImageView ivAdminSettings;
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

        this.ivAdminSettings = findViewById(R.id.ivAdminMainSettings);
        this.ivAdminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        this.cvScheduler.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month%12)+1 +"/"+dayOfMonth+"/"+year;
                String secondDate = (month%12)+2 + "/"+dayOfMonth+"/"+year;
                Intent intent = new Intent(AdminMainActivity.this, AdminDateSelected.class);
                intent.putExtra("DateSelected", dateConverter(date));
                intent.putExtra("SeconDoseDate", dateConverter(secondDate));
                startActivity(intent);
            }
        });
//        this.initRecyclerView();

    }

    private void initRecyclerView(){


        this.adminMainManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.rvUsers.setLayoutManager(adminMainManager);

        this.usersAdapter = new UsersAdapter(this.usersArrayList);
        this.rvUsers.setAdapter(this.usersAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users user = dataSnapshot.getValue(Users.class);
                    if(user.isRegistered && !user.isScheduled && !user.isAdmin){
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

    public String dateConverter(String d) {
        String finaldate = "";
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
            Date date2 = formatter1.parse(d);
            SimpleDateFormat formatter2 = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            finaldate = formatter2.format(date2);
        }
        catch (Exception e) {
        }
        return finaldate;
    }
}
