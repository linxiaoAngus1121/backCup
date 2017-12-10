package com.example.login;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class FourActivity extends AppCompatActivity {


    private Button register;
    private EditText editTextname,editTextpass;
    private Login login_table;
    private       SharedPreferences.Editor editor;
    private int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
    register=(Button) findViewById(R.id.name_register_four);
    editTextname=(EditText)findViewById(R.id.name_four_edit);
    editTextpass=(EditText)findViewById(R.id.name_four_edit2);
       editor = getSharedPreferences("login", MODE_PRIVATE).edit();
          SharedPreferences share=  getSharedPreferences("login",MODE_PRIVATE);
        int time = share.getInt("time", 1);
        login_table=new Login(this,"Login_lib.db",null,time);
        register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String name = editTextname.getText().toString().trim();
            String pass = editTextpass.getText().toString().trim();
            ContentValues values = new ContentValues();
            values.put("name",name);
            values.put("pass",pass);
            login_table.rece(values);
            i=i+1;
            editor.putInt("time",i);
            editor.apply();
            Log.i("000","注册成功");
            Intent intent = new Intent(FourActivity.this,MainActivity.class);
            startActivity(intent);
        }
    });
    }


}
