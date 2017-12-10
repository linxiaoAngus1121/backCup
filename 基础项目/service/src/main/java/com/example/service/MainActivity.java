package com.example.service;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   private Button start,stop,bind,unbind,intentService;
   private MyService.DownloadBinder binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=(Button) findViewById(R.id.statr_server);
        stop=(Button) findViewById(R.id.stop_service);
        bind=(Button)findViewById(R.id.bind_service);
        unbind=(Button)findViewById(R.id.unbind_service);
        intentService=(Button)findViewById(R.id.IntentService);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        intentService.setOnClickListener(this);


    }

    ServiceConnection connection = new ServiceConnection() {

       /*
       * 活动成功绑定时调用
       * */
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder= (MyService.DownloadBinder) iBinder;
            /*
            * 能获取MyService.DownloadBinder)中所有public方法
            * */
            binder.startDown();
            binder.getprogress();
        }
        /*
        * 活动取消绑定时调用
        * */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.statr_server :
                Intent startintent = new Intent(this,MyService.class);
                startService(startintent);
                break;
            case R.id.stop_service:
                Intent stopintent = new Intent(this,MyService.class);
                stopService(stopintent);
                break;
            case R.id.bind_service:
                Intent bind=new Intent(this,MyService.class);
                bindService(bind,connection,BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                Intent unbind=new Intent(this,MyService.class);
                unbindService(connection);
                break;
            case R.id.IntentService:
                Intent intentservice= new Intent(this,MyIntentService.class);
                startService(intentservice);
                break;
        }
    }
}
