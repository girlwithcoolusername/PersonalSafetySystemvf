package com.example.personalsafetysystem.UserDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.personalsafetysystem.LoginActivity;
import com.example.personalsafetysystem.R;
import com.google.firebase.auth.FirebaseAuth;

public class ContactDashboard extends AppCompatActivity {

    private CardView btnLogout;
    private CardView btnHome;
    private CardView btnNotifs;
    private CardView btnProfile;
    private CardView btnTracking;
    private CardView btnListusers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_dashboard);
        btnLogout = findViewById(R.id.card_logout);
        btnProfile = findViewById(R.id.card_profil);
        btnTracking = findViewById(R.id.card_tracking);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_profile_contact);
            }
        });
        btnTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrackingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_profile_contact);

            }
        });

    }
}
