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
import android.widget.Toast;

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

public class AdminAddtoSched extends AppCompatActivity {
    private TextView tvDate, tvTime, tvVenue;
    private Spinner spFilter;
    private Button btnAddtoSched;
    private ImageButton ibBack;

    private RecyclerView rvAddtoSchedUserRow;
    private RecyclerView.LayoutManager adminAddManager;

    private UsersAddAdapter usersAddAdapter;
    private ArrayList<Users> usersArrayList;
    private Intent incomingIntent;
    private String date, secondDate, time, venue;
    private int i;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference databaseReference = database.getReference().child("Users");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_addtosched);
        this.usersArrayList = new ArrayList<>();

        incomingIntent = getIntent();
        this.date = incomingIntent.getStringExtra("DateSelected");
        this.secondDate = incomingIntent.getStringExtra("SeconDoseDate");
        this.venue = incomingIntent.getStringExtra("VenueSelected");
        this.time = incomingIntent.getStringExtra("TimeSelected");

        this.tvDate = findViewById(R.id.tv_addtosched_date);
        this.tvDate.setText(this.date);
        this.tvVenue = findViewById(R.id.tv_addtosched_venue);
        this.tvVenue.setText(this.venue);
        this.tvTime = findViewById(R.id.tv_addtosched_time);
        this.tvTime.setText(this.time);
        this.initRecyclerView();

        /*
        Filters the list of registered users by their priority level
        */
        this.spFilter = findViewById(R.id.spinner_addtosched_filter);
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
                                if (user.isRegistered && !user.isScheduled && !user.isAdmin && user.priority.equals(chosenFilter)) {
                                    usersArrayList.add(user);
                                }

                            }
                            usersAddAdapter.notifyDataSetChanged();
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
                                if (user.isRegistered && !user.isScheduled && !user.isAdmin) {
                                    usersArrayList.add(user);
                                }

                            }
                            usersAddAdapter.notifyDataSetChanged();
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
        Assigns all of the selected users to the selected Vaccine Site and Schedule
         */
        this.btnAddtoSched = findViewById(R.id.btn_addtosched);
        this.btnAddtoSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(i=0;i<usersArrayList.size();i++){
                    if(usersArrayList.get(i).isSelected){

                        HashMap hashMap = new HashMap();
                        hashMap.put("isScheduled", true);
                        hashMap.put("firstSchedule",date);
                        hashMap.put("firstTime",time);
                        hashMap.put("secondSchedule",secondDate);
                        hashMap.put("secondTime",time);
                        hashMap.put("vacSite",venue);

                        databaseReference.child(usersArrayList.get(i).uID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){


                                }
                            };

                        });
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                sendSms(usersArrayList.get(i).phone,usersArrayList.get(i).firstname.toUpperCase(Locale.ROOT) + " "+usersArrayList.get(i).lastname.toUpperCase(Locale.ROOT), date, secondDate, time, venue);

                            }
                            else{
                                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);

                            }
                        }
                        usersAddAdapter.notifyItemChanged(i);
                    }
                }
                Intent intent = new Intent(AdminAddtoSched.this, AdminDateSelected.class);
                intent.putExtra("DateSelected",date);
                startActivity(intent);
                finish();
            };

        });

        /*
        Returns to DateSelected Activity
         */
        this.ibBack = findViewById(R.id.ib_addtosched_back);
        this.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddtoSched.this,AdminDateSelected.class);
                intent.putExtra("DateSelected",date);
                intent.putExtra("TimeSelected", time);
                startActivity(intent);
                finish();
            }
        });
    }
    /*
    Initializes the recycler view with the default user list
     */
    private void initRecyclerView(){
        this.rvAddtoSchedUserRow = findViewById(R.id.rv_addtosched_userrow);
        this.adminAddManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        this.rvAddtoSchedUserRow.setLayoutManager(this.adminAddManager);

        this.usersAddAdapter = new UsersAddAdapter(this.usersArrayList);
        this.rvAddtoSchedUserRow.setAdapter(usersAddAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    if (user.isRegistered && !user.isScheduled && !user.isAdmin) {
                        usersArrayList.add(user);

                    }

                }
                usersAddAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
    Sends a personalized sms message to the selected user using their registered phone number
     */
    private void sendSms(String number,String name, String firstDate, String secondDate, String time, String venue){
        String message = "Good day " + name + "!\n\nYour vaccination schedule is shown below.\n\nDate: " + firstDate + "\nTime: " + time + "\nVenue: " + venue;

        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,message, null, null);

            Log.e("TEXT","WORKED TEXT");
        }
        catch (Exception e) {
            Log.e("TEXT","DIDNT WORK");
        }
    }

}
