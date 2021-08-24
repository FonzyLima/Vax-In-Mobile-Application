package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserVaccinationProfileActivity extends AppCompatActivity {
    private TextView tvName, tvBday, tvSex, tvEmail, tvNumber, tvPriority;
    private TextView tvHousenum, tvStreet, tvBarangay, tvCity;
    private TextView tvSite, tvFirstDate, tvFirstTime, tvSecondDate, tvSecondTime;

    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private DatabaseReference databaseReference;
    private String currUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vaccination_profile);

        tvName = findViewById(R.id.tv_vaxprof_personalinfo_name_text);
        tvBday = findViewById(R.id.tv_vaxprof_personalinfo_birthday_text);
        tvSex = findViewById(R.id.tv_vaxprof_personalinfo_gender_text);
        tvEmail = findViewById(R.id.tv_vaxprof_personalinfo_email_text);
        tvNumber = findViewById(R.id.tv_vaxprof_personalinfo_phone_text);
        tvPriority = findViewById(R.id.tv_vaxprof_personalinfo_priority_text);

        tvHousenum = findViewById(R.id.tv_vaxprof_address_number_text);
        tvStreet = findViewById(R.id.tv_vaxprof_address_street_text);
        tvBarangay = findViewById(R.id.tv_vaxprof_address_barangay_text);
        tvCity = findViewById(R.id.tv_vaxprof_address_city_text);

        tvSite = findViewById(R.id.tv_vaxprof_vaxsched_site_text);
        tvFirstDate = findViewById(R.id.tv_vaxprof_vaxsched_date_text1);
        tvFirstTime = findViewById(R.id.tv_vaxprof_vaxsched_time_text1);
        tvSecondDate = findViewById(R.id.tv_vaxprof_vaxsched_date_text2);
        tvSecondTime = findViewById(R.id.tv_vaxprof_vaxsched_time_text2);

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        currUserID = currUser.getUid();

        databaseReference.child(currUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);
                Log.e("HELLO","I AM HEREEE");

                if(userProfile != null){
                    tvName.setText(userProfile.firstname + " "+ userProfile.lastname);
                    tvBday.setText(userProfile.bday);
                    tvSex.setText(userProfile.sex);
                    tvEmail.setText(userProfile.email);
                    tvNumber.setText(userProfile.phone);
                    tvPriority.setText(userProfile.priority);

                    tvHousenum.setText(userProfile.houseNum);
                    tvStreet.setText(userProfile.street);
                    tvBarangay.setText(userProfile.barangay);
                    tvCity.setText(userProfile.city);

                    tvSite.setText(userProfile.vacSite);
                    tvFirstDate.setText(userProfile.firstSchedule);
                    tvFirstTime.setText(userProfile.firstTime);
                    tvSecondDate.setText(userProfile.secondSchedule);
                    tvSecondTime.setText(userProfile.secondTime);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserVaccinationProfileActivity.this,"WEWWW",Toast.LENGTH_LONG).show();
            }
        });





    }
}