package com.example.kallz2u.utilities;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.kallz2u.R;
import com.example.kallz2u.activites.DashBoardActivity;
import com.google.firebase.installations.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NotificationHelper {
    private static final String CHANNEL_ID="channel_id";   //通道渠道id
    public static final String  CHANEL_NAME="chanel_name"; //通道渠道名称
    private static SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");


    @TargetApi(Build.VERSION_CODES.O)
    public static  void  show(Context context){
        int numcode = (int) ((Math.random() * 9 + 1));
//        Constants.COD = numcode;
        NotificationChannel channel = null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            channel = new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.setShowBadge(false);
        }
        Notification notification;
        //获取Notification实例
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //向上兼容 用Notification.Builder构造notification对象

            notification = new Notification.Builder(context,CHANNEL_ID)
                    .setContentTitle("消息")
                    .setContentText("12344444")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(Color.parseColor("#FEDA26"))
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                    .setTicker("消息")
                    .build();
        }else {
            //向下兼容 用NotificationCompat.Builder构造notification对象
            notification = new NotificationCompat.Builder(context)
                    .setContentTitle("消息")
                    .setContentText("12344444")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(Color.parseColor("#FEDA26"))
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                    .setTicker("消息")
                    .build();

        }
        notification.flags= Notification.FLAG_AUTO_CANCEL;
        Intent intent=new Intent(context, DashBoardActivity.class);
        intent.putExtra("message",":"+sdf.format(new Date()));

        int requestId=(int) new Date().getTime();

        PendingIntent pendingIntent=PendingIntent.getActivity(context,requestId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);

        notification.contentIntent=pendingIntent;
        int  notifiId=1;
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(notifiId,notification);
    }
}
