package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText etEmail,etUsername, etPassword;
    private Button btnLogin, btnRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mAuth = FirebaseAuth.getInstance();

        this.etEmail = findViewById(R.id.et_email);
        this.etUsername = findViewById(R.id.et_username);
        this.etPassword = findViewById(R.id.et_password);

        this.btnRegister = findViewById(R.id.btn_register);
        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);

                startActivity(intent);
            }
        });

        this.btnLogin = findViewById(R.id.btn_submit);
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
                     
            }
        });

    }

    private void userLogin() {
        String email, username, password;

        email = etEmail.getText().toString().trim();
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please enter valid email!");
            etEmail.requestFocus();
            return;
            
        }
        if(username.isEmpty()){
            etUsername.setError("Please enter valid username");
            etUsername.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 6)  {
            etPassword.setError("Please enter valid password");
            etPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.e("WEW","SUCCESS NAMAN");
                    Toast.makeText(MainActivity.this,"LOGGED IN",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, Loggedin_user.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,"LOGGED IN",Toast.LENGTH_LONG).show();   

                }
            }
        });

        

    }
}