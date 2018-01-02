package com.my.expressquery.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 123456 on 2017/9/30.
 * 发起网络连接
 */

public class HttpUtils {
    public static void sendHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
