package com.example.qunfaduanxin;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    AlarmManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Button button=(Button)findViewById(R.id.set);
        manager= (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cl = Calendar.getInstance();
                new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        Intent intent = new Intent(AlarmActivity.this,Secondly.class);
                        PendingIntent pi= PendingIntent.getActivity(AlarmActivity.this,0,intent,0);
                        Calendar calendar =Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR,i);
                        calendar.set(Calendar.MINUTE,i1);
                        manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
                        Log.i("000","设置闹钟成功");
                    }
                },cl.get(Calendar.HOUR_OF_DAY),cl.get(Calendar.MINUTE),false).show();
                Log.i("000",Calendar.HOUR_OF_DAY+"");
            }
        });
    }
}
