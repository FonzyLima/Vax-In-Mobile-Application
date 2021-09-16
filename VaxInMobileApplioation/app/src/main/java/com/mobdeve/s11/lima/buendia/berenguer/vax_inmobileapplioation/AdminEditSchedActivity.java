package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class AdminEditSchedActivity extends AppCompatActivity {
    private TextView tvDate, tvTime, tvVenue;
    private Spinner spFilter;
    private Button btnDeleteSched;
    private ImageButton ibBack;

    private RecyclerView rvDelSchedRow;
    private RecyclerView.LayoutManager adminDelManager;

    private UsersDeleteAdapter usersDeleteAdapter;
    private ArrayList<Users> usersArrayList;
    private Intent incomingIntent;
    private String date, time, venue;

    private String delUsersFirstName, delUsersMiddleName, delUsersLastName, delUsersNumber;
    private int i;

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
        this.btnDeleteSched = findViewById(R.id.btn_editsched);
        this.btnDeleteSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(i=0;i<usersArrayList.size();i++){
                    if(usersArrayList.get(i).isSelected){
                        HashMap hashMap = new HashMap();
                        if(usersArrayList.get(i).isFirstDose){
                            hashMap.put("secondSchedule","TBA");
                            hashMap.put("secondTime","TBA");
                            hashMap.put("vacSite","TBA");

                            databaseReference.child(usersArrayList.get(i).uID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(AdminEditSchedActivity.this, AdminDateSelected.class);
                                        intent.putExtra("DateSelected",date);
                                        startActivity(intent);
                                        finish();

                                    }
                                };

                            });
                            usersDeleteAdapter.notifyItemChanged(i);

                        }
                        else{
                            hashMap.put("isScheduled",false);
                            hashMap.put("firstSchedule","TBA");
                            hashMap.put("firstTime","TBA");
                            hashMap.put("secondSchedule","TBA");
                            hashMap.put("secondTime","TBA");
                            hashMap.put("vacSite","TBA");

                            databaseReference.child(usersArrayList.get(i).uID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(AdminEditSchedActivity.this, AdminDateSelected.class);
                                        intent.putExtra("DateSelected",date);
                                        startActivity(intent);
                                        finish();

                                    }
                                };

                            });
                            usersDeleteAdapter.notifyItemChanged(i);
                        }

                    }
                }
            };

        });

        this.initRecyclerview();

        this.spFilter = findViewById(R.id.spinner_editsched_filter);
        this.spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenFilter = spFilter.getSelectedItem().toString().substring(0,2);
                if(position != 0){
                    usersArrayList.clear();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Users user = dataSnapshot.getValue(Users.class);
                                if (user.isRegistered && user.isScheduled && !user.isAdmin && user.priority.equals(chosenFilter)) {
                                    if((user.firstSchedule.equals(date) && user.firstTime.equals(time)) || (user.secondSchedule.equals(date) && user.secondTime.equals(time))){
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
                else{
                    usersArrayList.clear();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Users user = dataSnapshot.getValue(Users.class);
                                if (user.isRegistered && user.isScheduled && !user.isAdmin) {
                                    if((user.firstSchedule.equals(date) && user.firstTime.equals(time)) || (user.secondSchedule.equals(date) && user.secondTime.equals(time))){
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.ibBack = findViewById(R.id.ib_editsched_back);
        this.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminEditSchedActivity.this, AdminDateSelected.class);
                intent.putExtra("DateSelected",date);
                intent.putExtra("TimeSelected", time);
                startActivity(intent);
                finish();
            }
        });





    }
    private void sendSms(String number,String name){
        String message = "Good day " + name + "!\n\nYour vaccine schedule has been removed.";

        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,message, null, null);

            Log.e("TEXT","WORKED TEXT");
        }
        catch (Exception e) {
            Log.e("TEXT","DIDNT WORK");
        }
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
                        if((user.firstSchedule.equals(date) && user.firstTime.equals(time)) || (user.secondSchedule.equals(date) && user.secondTime.equals(time))){
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
