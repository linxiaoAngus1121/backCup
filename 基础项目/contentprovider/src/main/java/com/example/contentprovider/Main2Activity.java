package com.example.contentprovider;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Uri uri = Uri.parse("content://sms")访问短信的uri
        Uri uri = Uri.parse("content://sms");
        /*getContentResolver().registerContentObserver(uri,true,new MycontentObserver(new Handler())),监听当短信的contentprovideer发生变化
            第一个参数是uri,第二个一般给true（会传递到下一层），第三个为自定义类，但必须继承ContentObserver
        */
        getContentResolver().registerContentObserver(uri,true,new MycontentObserver(new Handler()));
    }

    private class MycontentObserver extends ContentObserver {
        public MycontentObserver(Handler handler) {
            super(handler);
        }

        //当数据发生变化时会调用onchange方法
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            //发送的消息会存放在“content://sms/outbox”中,发件箱
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/outbox"), null, null, null, null);
            if (cursor!=null){
                while (cursor.moveToNext()){
                    StringBuilder builder = new StringBuilder();
                    //获取短信收件人,标题，内容，时间
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    builder.append("收件人").append(address);
                    String subject = cursor.getString(cursor.getColumnIndex("subject"));
                    builder.append("标题是").append(subject);
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    builder.append("内容为").append(body);
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    builder.append("时间").append(date);
                    Log.i("000",builder.toString());
                }
            }

        }
    }
}
