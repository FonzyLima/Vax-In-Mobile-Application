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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String currUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.mAuth = FirebaseAuth.getInstance();

        this.etEmail = findViewById(R.id.et_login_email);
        this.etPassword = findViewById(R.id.et_login_password);

        // Redirects to Register Activity
        this.tvRegister = findViewById(R.id.tv_login_register);
        this.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);

                startActivity(intent);
            }
        });


        this.btnLogin = findViewById(R.id.btn_login_login);
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
                     
            }
        });

    }

    // Function that validates user's inputs and logs in the user
    private void userLogin() {
        String email, username, password;

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please enter valid email!");
            etEmail.requestFocus();
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
                    FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
                    if(currUser.isEmailVerified()){
                        databaseReference = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
                        currUserID = currUser.getUid();
                        databaseReference.child(currUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Users userProfile = snapshot.getValue(Users.class);
                                Log.e("HELLO","I AM HEREEE");

                                if(userProfile != null){
                                    if(userProfile.isAdmin){
                                        Toast.makeText(MainActivity.this,"LOGGED IN",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, AdminMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"LOGGED IN",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, UserMainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this,"WEWWW",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                    else{
                        Toast.makeText(MainActivity.this,"Email not verified. Check your email",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this,"Log in credentials does not exist",Toast.LENGTH_LONG).show();
                }
            }
        });

        

    }
}