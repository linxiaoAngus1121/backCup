package com.example.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private DownloadBinder binder = new DownloadBinder();
    public MyService() {
    }

    @Override
    public void onCreate() {
        Log.i("000","create success");
        super.onCreate();
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi= PendingIntent.getActivity(this,0,intent,0);
        Notification notification= new android.support.v7.app.NotificationCompat.Builder(this)
                .setContentTitle("this is title")
                .setWhen(System.currentTimeMillis())
                .setContentText("this is content")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pi)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher))
                .build();
      startForeground(1,notification);

    }

    @Override
    public void onDestroy() {
        Log.i("000","destroy success");
        super.onDestroy();
    }
/*
* 每次启动都会调用此方法
* */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("000","startcommond success");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }


    class DownloadBinder extends Binder{
       public void  startDown(){
            Log.i("000","startdownload");
        }
        public  int getprogress(){
            Log.i("000","getprogress");
            return 0;
        }
    }
}
