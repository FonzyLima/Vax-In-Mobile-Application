package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class AdminSettingsActivity extends AppCompatActivity {

    private Button btnLogout, btnEditSms;
    private ImageButton ibBack;
    private FirebaseAuth mAuth;
    private Intent incomingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        this.mAuth = FirebaseAuth.getInstance();

        /*
        Logs out the logged in user
         */
        this.btnLogout = findViewById(R.id.btn_adminsettings_logout);
        this.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prompts a confirmation for the user
                new AlertDialog.Builder(AdminSettingsActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirm Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();
                                Intent intent = new Intent(AdminSettingsActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        /*
        Redirects to editSms activity
         */
        this.btnEditSms = findViewById(R.id.btn_adminsettings_editsms);
        this.btnEditSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSettingsActivity.this, EditSMSActivity.class);
                startActivity(intent);
            }
        });

        /*
        Redirects to AdminMain activity
         */
        this.ibBack = findViewById(R.id.ib_adminsettings_back);
        this.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSettingsActivity.this,AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}