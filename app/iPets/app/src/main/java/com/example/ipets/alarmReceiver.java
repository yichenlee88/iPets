package com.example.ipets;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class alarmReceiver extends BroadcastReceiver {
    private NotificationManager notificationManager;
    private Notification notification;
    private final static int NOTIFICATION_ID=0;
    String documentname;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userUID = currentUser.getUid();
        db.collection("users").document(userUID).collection("pets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentname = document.getId();
                                db.collection("users").document(userUID).collection("pets").document(documentname).collection("countdown")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        DocumentSnapshot doc = document;
                                                        StringBuilder field1 = new StringBuilder("");
                                                        String endDay = field1.append(doc.get("endDay")).toString();
                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        if(!endDay.equals("")) {
                                                            Date date = null;
                                                            try {
                                                                date = sdf.parse(endDay);
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }
                                                            Calendar calendar = Calendar.getInstance();
                                                            calendar.setTime(date);
                                                            int pendingIntentid = calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH);
                                                            Intent notifyIntent = new Intent(context, homeActivity.class);
                                                            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            PendingIntent pendingIntent=PendingIntent.getActivity(context,pendingIntentid,notifyIntent,PendingIntent.FLAG_ONE_SHOT);

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
                                                } else {

                                                }

                                            }
                                        });
                            }
                        } else {

                        }

                    }
                });

    }
}
