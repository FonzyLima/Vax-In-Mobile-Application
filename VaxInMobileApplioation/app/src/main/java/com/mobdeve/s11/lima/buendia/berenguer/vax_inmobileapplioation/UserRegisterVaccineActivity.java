package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;

public class UserRegisterVaccineActivity extends AppCompatActivity {
    private EditText etFName, etLName, etMName, etEmail, etNumber,
                     etBday, etHousenum, etStreet, etBarangay, etCity;
    private Spinner spPrioGroup, spSex;
    private Button btnRegisterVaccine;

    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private DatabaseReference databaseReference;
    private String currUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_vaccine);

        spPrioGroup = findViewById(R.id.spinner_rv_priority);
        etFName = findViewById(R.id.et_rv_firstname);
        etLName = findViewById(R.id.et_rv_lastname);
        etMName = findViewById(R.id.et_rv_middlename);
        etEmail = findViewById(R.id.et_rv_email);
        etNumber = findViewById(R.id.et_rv_phone);
        etBday = findViewById(R.id.et_rv_age);
        spSex = findViewById(R.id.spinner_rv_gender);
        etHousenum = findViewById(R.id.et_rv_addressnum);
        etStreet = findViewById(R.id.et_rv_street);
        etBarangay = findViewById(R.id.et_rv_barangay);
        etCity = findViewById(R.id.et_rv_city);

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        currUserID = currUser.getUid();

        databaseReference.child(currUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);
                Log.e("HELLO","I AM HEREEE");

                if(userProfile != null){
                    etFName.setText(userProfile.firstname);
                    etFName.setEnabled(false);
                    etLName.setText(userProfile.lastname);
                    etLName.setEnabled(false);
                    etEmail.setText(userProfile.email);
                    etEmail.setEnabled(false);
                    etNumber.setText(userProfile.phone);
                    etNumber.setEnabled(false);
                    etBday.setText(userProfile.bday);
                    etBday.setEnabled(false);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserRegisterVaccineActivity.this,"WEWWW",Toast.LENGTH_LONG).show();
            }
        });


        btnRegisterVaccine = findViewById(R.id.btn_rv_register);
        btnRegisterVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserVaccine();
            }
        });

    }

    public void registerUserVaccine(){
        String priority, fName, lName, mName, email, number, bday, sex, houseNum, street, barangay, city;

        priority = spPrioGroup.getSelectedItem().toString();
        fName = etFName.getText().toString().trim();
        lName = etLName.getText().toString().trim();
        mName = etMName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        number = etNumber.getText().toString().trim();
        bday = etBday.getText().toString().trim();
        sex = spSex.getSelectedItem().toString();
        houseNum = etHousenum.getText().toString().trim();
        street = etStreet.getText().toString().trim();
        barangay = etBarangay.getText().toString().trim();
        city = etCity.getText().toString().trim();

        if(houseNum.isEmpty()){
            etHousenum.setError("Please enter your house number");
            etHousenum.requestFocus();
            return;
        }
        if(street.isEmpty()){
            etStreet.setError("Please enter your street");
            etStreet.requestFocus();
            return;
        }
        if(barangay.isEmpty()){
            etBarangay.setError("Please enter your barangay");
            etBarangay.requestFocus();
            return;
        }
        if(city.isEmpty()){
            etCity.setError("Please enter your city");
            etCity.requestFocus();
            return;
        }

        HashMap hashMap = new HashMap();
        hashMap.put("priority",priority);
        hashMap.put("isRegistered",true);
        hashMap.put("houseNum",houseNum);
        hashMap.put("street",street);
        hashMap.put("barangay",barangay);
        hashMap.put("city",city);

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        currUserID = currUser.getUid();

        databaseReference.child(currUserID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){

                    RegisteredUsers registeredUser = new RegisteredUsers(priority,fName,lName,mName,email,number,bday,sex,houseNum,street,barangay,city);
                    FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("RegisteredUsers")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(registeredUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UserRegisterVaccineActivity.this,"You have successfully registered for vaccination!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UserRegisterVaccineActivity.this, UserMainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }
            }
        });





    }
}