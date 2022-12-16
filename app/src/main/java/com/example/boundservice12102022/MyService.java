package com.example.boundservice12102022;


import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * Created by pphat on 12/16/2022.
 */
public class MyService extends Service {

    Notification notification;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notification = createNotification("Bắt đầu chạy");
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    private Notification createNotification(String text) {
        return new NotificationCompat.Builder(this, Constant.CHANNEL_ID)
                .setContentTitle("Thông báo")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }
}
