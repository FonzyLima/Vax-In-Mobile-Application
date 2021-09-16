package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class AdminMainActivity extends AppCompatActivity {
    private CalendarView cvScheduler;
    private ImageView ivAdminSettings;
    private Button btnConfirmDose;
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
        // Requests Sending SMS permission from user's device
        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);

        // Initializing components
        this.cvScheduler = findViewById(R.id.cv_scheduler);
        usersArrayList = new ArrayList<>();

        /*
        Redirects to AdminSettings activity
         */
        this.ivAdminSettings = findViewById(R.id.ivAdminMainSettings);
        this.ivAdminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminSettingsActivity.class);
                startActivity(intent);
            }
        });

        /*
        Redirects to DateSelected with the selected date
         */
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

        /*
        Redirects to ConfirmDoses activity
         */
        this.btnConfirmDose = findViewById(R.id.btn_main_confirmdose);
        this.btnConfirmDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, ConfirmDosesActivity.class);
                startActivity(intent);
            }
        });

    }

    /*
    Converts given parameter string to our custom format
    Returns formatted date string
     */
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
