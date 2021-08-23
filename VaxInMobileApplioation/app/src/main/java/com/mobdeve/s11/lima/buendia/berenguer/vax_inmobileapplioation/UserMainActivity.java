package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserMainActivity extends AppCompatActivity {
    private ImageView ivProgressCircle, btnRegisVaccine, btnProfile;

    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private DatabaseReference databaseReference;
    private String currUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        ivProgressCircle = findViewById(R.id.iv_progress_circle);
        btnRegisVaccine = findViewById(R.id.iv_registervax_bg);
        btnProfile = findViewById(R.id.iv_vaxprof_bg);

        btnRegisVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, UserRegisterVaccineActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, UserVaccinationProfileActivity.class);
                startActivity(intent);
            }
        });

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        currUserID = currUser.getUid();

        databaseReference.child(currUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);
                Log.e("HELLO","I AM HEREEE");

                if(userProfile != null){
                    boolean isRegistered = userProfile.isRegistered;
                    boolean isFirstDose = userProfile.isFirstDose;
                    boolean isComplete = userProfile.isComplete;

                    if(!isRegistered){
                        ivProgressCircle.setImageResource(R.drawable.progress_circle_0);
                    }
                    else if(!isFirstDose){
                        ivProgressCircle.setImageResource(R.drawable.progress_circle_1);
                    }
                    else if(!isComplete){
                        ivProgressCircle.setImageResource(R.drawable.progress_circle_2);
                    }
                    else{
                        ivProgressCircle.setImageResource(R.drawable.progress_circle_3);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserMainActivity.this,"WEWWW",Toast.LENGTH_LONG).show();
            }
        });

    }
}