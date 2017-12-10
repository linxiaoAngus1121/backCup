package com.example.shujuku;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    String content=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText =(EditText) findViewById(R.id.edit);
        content = editText.getText().toString();
        String inputtext=load();
        if (!TextUtils.isEmpty(inputtext)){
    editText.setText(inputtext);
    editText.setSelection(editText.length());
            Log.i("123","还原成功");
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        save(content);
        Log.i("123","保存成功");
    }

        /*
        load()方法
        * 重新进入程序时还原原来保存的东西
        * */


    public String load() {
        FileInputStream input=null;
       BufferedReader reader=null;
        StringBuilder builder=null;
        try {
            String line=null;
             builder = new StringBuilder();
             input = openFileInput("data");
            reader=new BufferedReader(new InputStreamReader(input));
         /*   if((line=reader.readLine())!=null){
                builder.append(line);
            }*/

         while ((line=reader.readLine())!=null){
             builder.append(line);
         }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return builder.toString();
    }


    /*
        save方法
    * 退出程序时保存输入的数据
    * */
    public void save(String content) {
        FileOutputStream fileOutput =null;
        BufferedWriter writer = null;
        try {
             fileOutput = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter( new OutputStreamWriter(fileOutput));
             writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
