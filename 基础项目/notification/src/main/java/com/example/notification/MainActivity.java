package com.example.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button) findViewById(R.id.nofiti);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nofiti:
                NotificationManager manger= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent = new Intent(this,Main2Activity.class);
                PendingIntent pending = PendingIntent.getActivity(this,0,intent,0);
                Notification notification = new NotificationCompat.Builder(this)
                                            .setContentText("this is content text")
                                            .setContentTitle("this is content title")
                                            .setWhen(System.currentTimeMillis())
                                            .setSmallIcon(R.drawable.ic_launcher)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                                            .setAutoCancel(true)
                                          //  .setSound(Uri.fromFile(new File("/system/meida/ringtones/Atria.ogg")))
                                          //  .setVibrate(new long[]{0,1000,1000,1000})
                                           // .setLights(Color.RED,1000,1000)
                                            .setStyle(new NotificationCompat.BigTextStyle().bigText("thsi is a long sdfijsifsfjffsdfsdfsdfsdfsdfsfsdfsdfsdfsfsdfsdfsdfffffffffffffffffffffffffffffffffff"))
                                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher)))
                                            .setPriority(android.support.v7.app.NotificationCompat.PRIORITY_MAX)
                                            .setContentIntent(pending)
                                            .build();
                            manger.notify(1,notification);


                break;
        }
    }
}
