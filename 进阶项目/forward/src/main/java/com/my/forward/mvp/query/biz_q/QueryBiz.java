package com.my.forward.mvp.query.biz_q;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 123456 on 2018/2/1.
 */

public class QueryBiz implements IQuery {
    @Override
    public void query(final OnQueryListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String path = "http://192.168.0.102:8080/Android_forward/QueryServley";
                try {
                    URL url = new URL(path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestProperty("contentType", "utf-8");
                    urlConnection.setConnectTimeout(5 * 1000);
                    BufferedReader reader = new BufferedReader(new InputStreamReader
                            (urlConnection.getInputStream(), "utf-8"));

                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = (reader.readLine())) != null) {
                        builder.append(line);
                    }
                    String data = new String(builder.toString().getBytes(), "utf-8");
                    Log.i("000", data + "json字符串");
                    listener.querySuccess(data);
                } catch (Exception e) {
                    Log.i("000", e.getMessage());
                    listener.queryFailed();
                }
            }
        }).start();


    }
}
