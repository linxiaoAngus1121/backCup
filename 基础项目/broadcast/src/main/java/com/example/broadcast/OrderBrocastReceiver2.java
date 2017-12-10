package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class OrderBrocastReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    Bundle extras = getResultExtras(true);
    String s = extras.get("first").toString();
    Log.i("000",s);
       /*
       *throw new UnsupportedOperationException("Not yet implemented")加这句话的时候一定要try catch,切记切记
       * */



    }
}
