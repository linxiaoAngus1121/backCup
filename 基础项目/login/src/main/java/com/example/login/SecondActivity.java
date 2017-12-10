package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

   private TextView success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        success=(TextView)findViewById(R.id.success);
        String extra = getIntent().getStringExtra("content");
        Intent intenttofirst=new Intent();
        intenttofirst.putExtra("B给我的数据","还给你咯");
       if (extra.equals("")){
           Toast.makeText(this,"gg了",Toast.LENGTH_SHORT).show();
       }else {
           success.setText(extra);
           setResult(RESULT_OK,intenttofirst);
       }
    }
}
