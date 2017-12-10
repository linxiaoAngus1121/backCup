package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class OrderBrocastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // Log.i("000",intent.getStringExtra("lalla"));
        Bundle bundle = new Bundle();
        bundle.putString("first","babana");
            setResultExtras(bundle);
            Log.i("000","这是在第一个receiver");
           // abortBroadcast();


    }
}
