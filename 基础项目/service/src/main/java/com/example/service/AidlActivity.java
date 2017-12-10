package com.example.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;

public class AidlActivity extends AppCompatActivity {

    private Button get;
    private TextView color,weight;
    private Icat icat;

    private ServiceConnection serviceConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {//获取远程service的对象代理
                icat=Icat.Stub.asInterface(iBinder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            icat=null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        get=(Button)findViewById(R.id.getstate);
        color=(TextView)findViewById(R.id.tv1);
        weight=(TextView)findViewById(R.id.tv2);
        Intent intent = new Intent("999999999");
        //bindservice一定要记得在destroy方法中unbind
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    color.setText(icat.getcolor());
                    weight.setText(icat.getweight()+"");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
