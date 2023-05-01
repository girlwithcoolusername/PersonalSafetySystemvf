package com.example.personalsafetysystem.UserDashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.personalsafetysystem.R;

public class Notifications extends AppCompatActivity {

    private TextView mNotificationTextView,title;
    private BroadcastReceiver mMessageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        title = findViewById(R.id.notification_textview);
        mNotificationTextView = findViewById(R.id.notification_textview2);


        // Vérifiez si l'activité a été lancée à partir d'une notification FCM
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("messageTitle")) {
            String message = getIntent().getExtras().getString("messageBody");
            String title1 = getIntent().getExtras().getString("messageTitle");
            title.setText(title1);
            mNotificationTextView.setText(message);

        }

        // Register a broadcast receiver to handle FCM messages
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                mNotificationTextView.setText(message);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("MyFirebaseMessagingReceiver"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}
