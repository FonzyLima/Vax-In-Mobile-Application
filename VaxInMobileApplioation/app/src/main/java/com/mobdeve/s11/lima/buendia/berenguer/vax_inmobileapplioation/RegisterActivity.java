package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etRegisterFirstName, etRegisterMiddleName, etRegisterLastName, etRegisterPhone, etRegisterEmail, etRegisterPassword, etRegisterConfirm, etRegisterBday;
    private Button btnRegisterRegister;
    private TextView tvRegisterLogin;
    private Spinner spinnerSex;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initializes components
        mAuth = FirebaseAuth.getInstance();

        etRegisterFirstName = findViewById(R.id.et_register_firstname);
        etRegisterMiddleName = findViewById(R.id.et_register_middlename);
        etRegisterLastName = findViewById(R.id.et_register_lastname);
        etRegisterPhone = findViewById(R.id.et_register_phone);
        etRegisterEmail = findViewById(R.id.et_register_email);
        etRegisterPassword = findViewById(R.id.et_register_password);
        etRegisterConfirm = findViewById(R.id.et_register_confirm_password);
        etRegisterBday = findViewById(R.id.et_register_age);

        spinnerSex = findViewById(R.id.spinner_register_sex);

        // Button to validate and register a user
        btnRegisterRegister = findViewById(R.id.btn_register_register);
        btnRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegistration();
                }

        });

        // Redirects to MainActivity
        tvRegisterLogin = findViewById(R.id.tv_register_login);
        tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    };

    /*
    Validates user's inputs and registers if all valid
     */
    private void validateRegistration(){
        String firstname, middlename, lastname, phone, email, password, confirmpass, sex, bday;

        firstname = etRegisterFirstName.getText().toString().trim();
        middlename = etRegisterMiddleName.getText().toString().trim();
        lastname = etRegisterLastName.getText().toString().trim();
        phone = etRegisterPhone.getText().toString().trim();
        email = etRegisterEmail.getText().toString().trim();
        password = etRegisterPassword.getText().toString().trim();
        confirmpass = etRegisterConfirm.getText().toString().trim();
        sex = spinnerSex.getSelectedItem().toString().trim();
        bday = etRegisterBday.getText().toString().trim();

        if(firstname.isEmpty()){
            etRegisterFirstName.setError("Please enter your first name.");
            etRegisterFirstName.requestFocus();
            return;
        }

        if(lastname.isEmpty()){
            etRegisterLastName.setError("Please enter your last name.");
            etRegisterLastName.requestFocus();
            return;
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etRegisterEmail.setError("Please enter a valid email.");
            etRegisterEmail.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            etRegisterPhone.setError("Please enter your phone number.");
            etRegisterPhone.requestFocus();
            return;
        }

        if(password.isEmpty() || password.length() < 6){
            etRegisterPassword.setError("Please enter a valid password.");
            etRegisterPassword.requestFocus();
            return;
        }

        if(confirmpass.isEmpty() || !confirmpass.equals(password)){
            etRegisterConfirm.setError("Passwords do not match.");
            etRegisterConfirm.requestFocus();
            return;
        }
        if(bday.isEmpty()){
            etRegisterBday.setError("Please enter valid date");
            etRegisterBday.requestFocus();
        }


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Users user = new Users(firstname,middlename, lastname,email, phone, sex,bday,FirebaseAuth.getInstance().getUid());
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
                            currUser.sendEmailVerification();
                            mAuth.signOut();
                        }
                    });
                    FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"Account Successfully Registered. Please Verify your email",Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this,"FAIL",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(RegisterActivity.this,"Email is already Registered",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
