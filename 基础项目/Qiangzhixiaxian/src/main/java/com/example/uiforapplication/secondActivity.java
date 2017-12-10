package com.example.uiforapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class secondActivity extends BaseActivity {

    private Button buttonLogout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        buttonLogout=(Button)findViewById(R.id.button);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * 发送强制下线广播，处理强制下线功能应该在广播接收者那边实现，不可以在activity做耗时操作，而且写在接收者那里
                * ，只要发送这条广播就能强制下线
                * */
                Intent intent = new Intent("con.example.uiforapplication.LOGOUT");
                sendBroadcast(intent);
            }
        });
    }
}
