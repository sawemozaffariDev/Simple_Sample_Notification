package com.samira.simplesamplenotification;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //UI Views
    AppCompatButton showNotification;
    private static String CHANNEL_ID = "channel01";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init UI Views
        showNotification = this.findViewById(R.id.show_notification_btn);

        showNotification.setOnClickListener(v -> {
            //check permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                showNotification();
            } else {
                this.requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        });


        //init UI Views
        showNotification = this.findViewById(R.id.show_notification_btn);

//        //handle click, show Notification
//        showNotification.setOnClickListener(v -> {
//            showNotification();
//        });
    }

    private void showNotification() {

        createNotificationChannel();

        //unique id to show new notification each time we click notification, if you want replace previous use a constant value as id
        Date date = new Date();
        int notificationId = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(date));

        //handle notification click, start SecondActivity  by topping notification
        Intent mainIntent = new Intent(this, SecondActivity.class);
        mainIntent.putExtra("KEY_NAME", "TEST NAME");
        mainIntent.putExtra("KEY_EMAIL", "TEST EMAIL");
        mainIntent.putExtra("KEY_TYPE", "Notification content clicked .... ");
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 1, mainIntent, PendingIntent.FLAG_IMMUTABLE);

        //handle (call) notification click, start SecondActivity  by topping notification
        Intent callIntent = new Intent(this, SecondActivity.class);
        //if you  want to pass data in notification and get in required activity
        callIntent.putExtra("KEY_NAME", "TEST NAME");
        callIntent.putExtra("KEY_EMAIL", "TEST EMAIL");
        callIntent.putExtra("KEY_TYPE", "call button clicked .... ");
        PendingIntent callPendingIntent = PendingIntent.getActivity(this, 2, callIntent, PendingIntent.FLAG_IMMUTABLE);

        //handle (message) notification click, start SecondActivity  by topping notification
        Intent messageIntent = new Intent(this, SecondActivity.class);
        //if you  want to pass data in notification and get in required activity
        messageIntent.putExtra("KEY_NAME", "TEST NAME");
        messageIntent.putExtra("KEY_EMAIL", "TEST EMAIL");
        messageIntent.putExtra("KEY_TYPE", "message button clicked .... ");
        PendingIntent messagePendingIntent = PendingIntent.getActivity(this, 3, messageIntent, PendingIntent.FLAG_IMMUTABLE);


        //creating notification builder
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID + "");
        //notification icon
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        //notification title
        notificationBuilder.setContentTitle("Notification Title");
        //notification description
        notificationBuilder.setContentText("This is the description of the notification, can be of multiple lines");
        //notification priority
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        //cancel notification on click
        notificationBuilder.setAutoCancel(true);
        //add click intent
        notificationBuilder.setContentIntent(mainPendingIntent);
        //add action button (call)
        notificationBuilder.addAction(R.drawable.ic_answer_call, "answer", callPendingIntent);
        //add action button (message)
        notificationBuilder.addAction(R.drawable.ic_call_reject_white, "reject", messagePendingIntent);


        //notification manager
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, notificationBuilder.build());

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyNotification";
            String description = "My notification channel description";

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }


    }


}