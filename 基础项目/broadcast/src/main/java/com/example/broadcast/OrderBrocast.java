
package com.example.broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderBrocast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_brocast);
        Button send= (Button)findViewById(R.id.sendOrderBrocast);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("apple");
              //  intent.putExtra("lalla","第一个发给OrderBraossReceiver");
                sendOrderedBroadcast(intent,null);
            }
        });
    }
}
