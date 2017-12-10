package com.coolweather.android.util;


import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 123456 on 2017/8/6.
 */

public class HttpUtil {
   public  static  void sendOkHttpRequest(String address,okhttp3.Callback callback){
       OkHttpClient httpClient=new OkHttpClient();
       Request request=new Request
               .Builder()
               .url(address)
               .get()
               .build();
       //开启子线程去访问网络
       httpClient.newCall(request).enqueue(callback);
    }
}
