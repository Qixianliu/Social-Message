package com.example.kallz2u.firebase;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.kallz2u.R;
import com.example.kallz2u.bean.Message;
import com.example.kallz2u.utilities.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

public class MessagingService extends FirebaseMessagingService {
    private static String TAG = "===================";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate " );
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "new token: " +token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String[] arrayStr = remoteMessage.getNotification().getTitle().split("##");
            if (arrayStr[4].equals("1")||arrayStr[4].equals("2")){
                Message m = new Message(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),Integer.valueOf(arrayStr[4]));
                m.save();
            }else {
                Toast.makeText(this,"accepted",Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
//        sendNotification(remoteMessage.getNotification().toString());
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, NotificationManager.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
