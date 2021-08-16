package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText etRegisterName, etRegisterUsername, etRegisterPhone, etRegisterEmail, etRegisterPassword, etRegisterConfirm;
    private Button btnRegisterRegister;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        etRegisterName = findViewById(R.id.et_register_name);
        etRegisterUsername = findViewById(R.id.et_register_username);
        etRegisterPhone = findViewById(R.id.et_register_phone);
        etRegisterEmail = findViewById(R.id.et_register_email);
        etRegisterPassword = findViewById(R.id.et_register_password);
        etRegisterConfirm = findViewById(R.id.et_register_confirm_password);

        btnRegisterRegister = findViewById(R.id.btn_register_register);

        btnRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegistration();
            }
        });
    }

    private void validateRegistration(){
        String name, username, phone, email, password, confirmpass;

        name = etRegisterName.getText().toString().trim();
        username = etRegisterUsername.getText().toString().trim();
        phone = etRegisterPhone.getText().toString().trim();
        email = etRegisterEmail.getText().toString().trim();
        password = etRegisterPassword.getText().toString().trim();
        confirmpass = etRegisterConfirm.getText().toString().trim();

        if(name.isEmpty()){
            etRegisterName.setError("Please enter your name.");
            etRegisterName.requestFocus();
            return;
        }

        if(username.isEmpty()){
            etRegisterUsername.setError("Please enter a username.");
            etRegisterUsername.requestFocus();
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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Users user = new Users(name,username,email, phone);

                    Toast.makeText(RegisterActivity.this,"SUCCESS SA UNA",Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"Success",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this,"FAIL",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(RegisterActivity.this,"FAIL SA UNA",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
