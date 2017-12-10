package com.example.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 123456 on 2017/7/21.
 */

public class MyIntentService extends IntentService {


    public MyIntentService() {
        super("MyIntentService");
    }

    /*
    * 在此方法中处理一些具体的业务逻辑
    * 这是在子线程中，不会阻塞主线程，运行完会自己停止，当执行耗时操作时，用这种方式
    * */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.i("000","thread id is "+ Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("000","destroy 了 ");
    }
}
