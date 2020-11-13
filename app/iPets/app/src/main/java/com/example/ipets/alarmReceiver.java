package com.example.ipets;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

public class alarmReceiver extends BroadcastReceiver {
    private NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent notifyIntent = new Intent(context, homeActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,999,notifyIntent,PendingIntent.FLAG_ONE_SHOT);

        notificationManager=
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        //        建立通知物件內容
        String id ="channel_1";//channel的id
        int importance = NotificationManager.IMPORTANCE_LOW;//channel的重要性
        NotificationChannel channel = new NotificationChannel(id, "123", importance);//生成channel
        //为channel添加属性
        channel.enableVibration(true);
        channel.enableLights(true);
        notificationManager.createNotificationChannel(channel);//添加channel
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Notification notification = new Notification.Builder(context,id)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setSmallIcon(R.drawable.app_logo1)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.app_logo1))
                .setContentTitle("倒數計時器")
                .setContentText("某項行程該做囉!點擊確認")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1,notification);
    }
}
