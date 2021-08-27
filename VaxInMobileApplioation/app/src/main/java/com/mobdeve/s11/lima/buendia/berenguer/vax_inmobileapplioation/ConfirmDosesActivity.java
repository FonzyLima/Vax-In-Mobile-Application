package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ConfirmDosesActivity extends AppCompatActivity {
    private EditText etEmail;
    private Button btnConfirmFirst, btnConfirmSecond;
    private Users keyUser;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference databaseReference = database.getReference().child("Users");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_confirm_doses);

        this.etEmail = findViewById(R.id.et_confirmdose_email);
        this.btnConfirmFirst = findViewById(R.id.btn_confirmdose_first);
        this.btnConfirmSecond = findViewById(R.id.btn_confirmdose_second);

        btnConfirmFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Users user = dataSnapshot.getValue(Users.class);
                            if(user.email.equals(etEmail.getText().toString().trim())){
                                keyUser = user;
                                etEmail.setText("");
                                break;
                            }
                        }
                        if(keyUser.isFirstDose){
                            HashMap hashMap = new HashMap();
                            hashMap.put("isComplete",true);
                            databaseReference.child(keyUser.uID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ConfirmDosesActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmDosesActivity.this, AdminMainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else{
                            HashMap hashMap = new HashMap();
                            hashMap.put("isFirstDose",true);
                            databaseReference.child(keyUser.uID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ConfirmDosesActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmDosesActivity.this, AdminMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

    }
}
