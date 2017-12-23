package com.my.mysql;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText telephone, password, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login);
        telephone = (EditText) findViewById(R.id.telephone);
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  login(telephone.getText().toString(), password.getText().toString());
                register(username.getText().toString(), password.getText().toString(), telephone.getText().toString());
            }


        });
    }

    private void login(final String s, final String s1) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://192.168.155.2:8080/AndroidProject/LoginServlet";
                try {
                    URL url = new URL(path);
                    try {
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);
                        urlConnection.setDoInput(true);
                        OutputStream outputStream = urlConnection.getOutputStream();
                        String data = "telephone=" + s + "&password=" + s1;
                        Log.i("000", data);
                        outputStream.write(data.getBytes());
                        InputStream inputStream = urlConnection.getInputStream();
                        InputStreamReader bufferedInputStream = new InputStreamReader(inputStream);
                        BufferedReader reader = new BufferedReader(bufferedInputStream);
                        StringBuilder builder = new StringBuilder();
                        String line = null;
                        while ((line = (reader.readLine())) != null) {
                            builder.append(line);
                        }
                        Log.i("000", builder.toString() + "00000025555555555555");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void register(final String s, final String s1, final String s2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //ip地址会变，所以每次运行可能需要修改
                String path = "http://192.168.155.2:8080/AndroidProject/RegisterServlet";
                try {
                    URL url = new URL(path);
                    try {
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);
                        urlConnection.setDoInput(true);
                        OutputStream outputStream = urlConnection.getOutputStream();
                        String data = "username=" + s + "&password=" + s1 + "&telephone=" + s2;
                        Log.i("000", data);
                        outputStream.write(data.getBytes());
                        InputStream inputStream = urlConnection.getInputStream();
                        InputStreamReader bufferedInputStream = new InputStreamReader(inputStream);
                        BufferedReader reader = new BufferedReader(bufferedInputStream);
                        StringBuilder builder = new StringBuilder();
                        String line = null;
                        while ((line = (reader.readLine())) != null) {
                            builder.append(line);
                        }
                        Log.i("000", builder.toString() + "这是返回的结果");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}

