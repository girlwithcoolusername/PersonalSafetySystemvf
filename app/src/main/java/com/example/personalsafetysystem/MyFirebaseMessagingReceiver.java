package com.example.personalsafetysystem;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.personalsafetysystem.UserDashboard.Notifications;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingReceiver extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgReceiver";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
        Intent intent = new Intent("MyFirebaseMessagingReceiver");
        // Créer l'intent pour ouvrir l'activité contenant la notification
        Intent resultIntent = new Intent(this, Notifications.class);
        resultIntent.putExtra("messageBody", text);
        resultIntent.putExtra("messageTitle", title);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // Créer la notification
                final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID,
                        "Heads up Notification",
                        NotificationManager.IMPORTANCE_HIGH
                );
                getSystemService(NotificationManager.class).createNotificationChannel(channel);
                Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setSmallIcon(R.drawable.ic_notifs)
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent); // Ajouter resultPendingIntent

        // Afficher la notification
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                NotificationManagerCompat.from(this).notify(1, notification.build());

        }
}
