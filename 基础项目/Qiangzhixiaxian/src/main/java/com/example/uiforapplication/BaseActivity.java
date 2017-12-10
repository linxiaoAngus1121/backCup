package com.example.uiforapplication;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.zip.Inflater;

/*
* 将强制下线的功能写在baseactivity，静态注册无法在onreceiver方法中弹出对话框，而动态注册更不可能，因为要为每个activity
* 写下线功能，所以，只有在baseaCTIVITY,因为所有的acivity都继承baseActivity
* */
public class BaseActivity extends AppCompatActivity {

    private  Qiangzhi qiangzhirever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollect.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollect.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter flater = new IntentFilter("con.example.uiforapplication.LOGOUT");
        qiangzhirever= new Qiangzhi();
        registerReceiver(qiangzhirever,flater);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (qiangzhirever!=null){
            unregisterReceiver(qiangzhirever);
            qiangzhirever=null;
        }
    }

    class   Qiangzhi extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, final Intent intent) {

         AlertDialog.Builder builder = new AlertDialog.Builder(context);
  builder.setTitle("warming");
  builder.setMessage("你被强制下线了");
  builder.setIcon(R.drawable.ic_launcher);
  builder.setCancelable(false);
  builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollect.finshALL();
                    Intent intent1 = new Intent(BaseActivity.this,MainActivity.class);
                    startActivity(intent1);
                }
            });

    builder.show();
        }
    }
}
