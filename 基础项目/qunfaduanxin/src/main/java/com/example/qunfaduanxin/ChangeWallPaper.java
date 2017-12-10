package com.example.qunfaduanxin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ChangeWallPaper extends AppCompatActivity {

   AlarmManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_wall_paper);
        final Button start =(Button)findViewById(R.id.bt1);
        final Button stop =(Button)findViewById(R.id.bt2);
        manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(ChangeWallPaper.this,Myservice.class);
        final PendingIntent pi=PendingIntent.getActivity(ChangeWallPaper.this,0,intent,0);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startService(intent);
                manager.setRepeating(AlarmManager.RTC_WAKEUP,0,5000,pi);
                start.setEnabled(false);
                stop.setEnabled(true);
                Log.i("000","OK");
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setEnabled(true);
                stop.setEnabled(false);
                stopService(intent);
                manager.cancel(pi);
            }
        });
    }
}
