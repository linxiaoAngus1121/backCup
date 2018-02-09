package com.my.forward.mvp.helpna.biz;

import android.util.Log;

import com.my.forward.mvp.helpna.bean.Information;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 123456 on 2018/1/31.
 * IUserCommit的实现类
 */

public class UserCommitBiz implements IUserCommit {

    @Override
    public void commit(final Information information, final OnCommitListener listener) {
        //这里进行写入数据库操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream outputStream = null;
                InputStream inputStream = null;
                InputStreamReader bufferedInputStream = null;
                BufferedReader reader = null;
                String path = "http://192.168.0.102:8080/Android_forward/CommitServlet";
                try {
                    URL url = new URL(path);
                    try {
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);
                        urlConnection.setDoInput(true);
                        outputStream = urlConnection.getOutputStream();
                        String data = "odd=" + information.getOdd() +
                                "&name=" + information.getName() +
                                "&contact=" + information.getContact() +
                                "&query_address=" + information.getQuery_address() +
                                "&address=" + information.getAddress();
                        Log.i("000", data);
                        outputStream.write(data.getBytes());
                        inputStream = urlConnection.getInputStream();
                        bufferedInputStream = new InputStreamReader(inputStream);
                        reader = new BufferedReader(bufferedInputStream);
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = (reader.readLine())) != null) {
                            builder.append(line);
                        }
                        Log.i("000", builder.toString() + "这是返回的结果");
                        if (builder.toString().equals("1")) {
                            //这里回调listener的成功方法
                            listener.commitSuccess();
                        } else {
                            listener.commitFailed();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
