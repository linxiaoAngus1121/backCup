package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
/*
* 这个acitivty是动态注册borcast
*
* */

public class MainActivity extends AppCompatActivity {

    private IntentFilter filter;
    private NerworkChangeReceiver network;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filter = new IntentFilter();
        //action 就是要接受广播的标志
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        network=new NerworkChangeReceiver();
        registerReceiver(network,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(network);
    }

    class NerworkChangeReceiver extends  BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this,"network Conneted",Toast.LENGTH_LONG).show();
           /* ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo.isAvailable() && networkInfo !=null){
                Toast.makeText(MainActivity.this,"network Conneted",Toast.LENGTH_LONG).show();
            }*/
        }
    }
}
