package com.example.boundservice12102022;


import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * Created by pphat on 12/16/2022.
 */
public class MyService extends Service {

    Notification notification;
    boolean isRunning = false;
    MyThread myThread;
    OnListenerService onListenerService;
    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notification = createNotification("Bắt đầu chạy");
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isRunning) {
            isRunning = true;
            myThread = new MyThread(new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message message) {
                    notification = createNotification("Count " + message.obj);
                    MyApplication.notificationManager.notify(1, notification);
                    if (onListenerService != null) {
                        onListenerService.onCount((Integer) message.obj);
                    }
                    return true;
                }
            }));
            myThread.start();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myThread.stopThread();
    }

    private Notification createNotification(String text) {
        return new NotificationCompat.Builder(this, Constant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Thông báo")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    public void setOnListenerService(OnListenerService onListenerService) {
        this.onListenerService = onListenerService;
    }

    interface OnListenerService {
        void onCount(int count);
    }
}
