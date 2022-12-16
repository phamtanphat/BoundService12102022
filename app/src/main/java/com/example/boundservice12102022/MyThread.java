package com.example.boundservice12102022;

import android.os.Handler;
import android.os.Message;


/**
 * Created by pphat on 12/16/2022.
 */
public class MyThread extends Thread {

    private Handler handler;
    private int count = 1;
    private Message message;
    private boolean isRunning = true;
    public MyThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        while (isRunning) {
            message = new Message();
            message.obj = count++;
            handler.sendMessage(message);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        this.isRunning = false;
    }
}
