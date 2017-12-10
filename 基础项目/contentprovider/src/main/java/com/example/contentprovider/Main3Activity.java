package com.example.contentprovider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Button start=(Button)findViewById(R.id.start);
        Button stop=(Button)findViewById(R.id.stop);
        final Intent intent = new Intent();
        intent.setAction("lalallallaa");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startService(intent);
                Toast.makeText(Main3Activity.this,"可以了",Toast.LENGTH_SHORT).show();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
                Toast.makeText(Main3Activity.this,"官邸了",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
