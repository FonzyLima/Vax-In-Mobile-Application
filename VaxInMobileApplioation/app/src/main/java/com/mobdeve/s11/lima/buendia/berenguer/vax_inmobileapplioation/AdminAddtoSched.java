package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Spinner spVenue;
    private Button btnAddtoSched;

    private RecyclerView rvAddtoSchedUserRow;
    private RecyclerView.LayoutManager adminAddManager;

    private UsersAddAdapter usersAddAdapter;
    private ArrayList<Users> usersArrayList;
    private Intent incomingIntent;
    private String date,secondDate, time, venue;
    String addUsersFirstName, addUsersMiddleName, addUsersLastName, addUsersNumber;
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
        this.secondDate = incomingIntent.getStringExtra("SeconDate");
        this.venue = incomingIntent.getStringExtra("VenueSelected");
        this.time = incomingIntent.getStringExtra("TimeSelected");

        this.tvDate = findViewById(R.id.tv_addtosched_date);
        this.tvDate.setText(this.date);
        this.tvVenue = findViewById(R.id.tv_addtosched_venue);
        this.tvVenue.setText(this.venue);
        this.tvTime = findViewById(R.id.tv_addtosched_time);
        this.tvTime.setText(this.time);
        this.initRecyclerView();
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

                        addUsersFirstName = usersArrayList.get(i).firstname;
                        addUsersMiddleName = usersArrayList.get(i).middlename;
                        addUsersLastName = usersArrayList.get(i).lastname;
                        addUsersNumber = usersArrayList.get(i).phone;

                        databaseReference.child(usersArrayList.get(i).uID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                        if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                            sendSms(addUsersNumber,addUsersFirstName.toUpperCase(Locale.ROOT) + " " + addUsersMiddleName.toUpperCase(Locale.ROOT)+" "+addUsersLastName.toUpperCase(Locale.ROOT), date, secondDate, time, venue);
                                            Intent intent = new Intent(AdminAddtoSched.this, AdminDateSelected.class);
                                            intent.putExtra("DateSelected",date);
                                            startActivity(intent);

                                        }
                                        else{
                                            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);

                                        }
                                    }

                                }
                            };

                        });
                        usersAddAdapter.notifyItemChanged(i);
                    }
                }
            }
        });

    }

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

    private void sendSms(String number,String name, String firstDate, String secondDate, String time, String venue){
        String message = "[VACCINATION SCHEDULE]\n\nGood day" + name + "!\n\nThanks for registering for your COVID-19 vaccine. Your vaccination schedule is shown below. Please refer to the following details:\n\nDate:" + firstDate + "\nTime:" + time + "\nVenue:" + venue + "\n\nPlease bring a valid ID, ballpen, and hand sanitizer. Come on your designated time and don't forget to wear face mask and face shield.\n\nStay safe!";

        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("09162477077",null,message, null, null);

            Log.e("TEXT","WORKED TEXT");
        }
        catch (Exception e) {
            Log.e("TEXT","DIDNT WORK");
        }
    }

}
