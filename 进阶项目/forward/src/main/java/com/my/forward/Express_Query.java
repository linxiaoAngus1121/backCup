package com.my.forward;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Express_Query extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_layout);

        findViewById(R.id.sos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Express_Query.this, DaiNaActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Express_Query.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }


}
