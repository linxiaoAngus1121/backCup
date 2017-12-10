package com.example.login;

import android.annotation.SuppressLint;
import android.app.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private Button register;
    private Button create;
    private EditText name_edt;
    private EditText pass_edt;
    private Login login_table;
    private TextView tip;
    private CheckBox checkBox;
    private Boolean repass;
    private SharedPreferences cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button)findViewById(R.id.id);
        create=(Button)findViewById(R.id.create);
        name_edt=(EditText) findViewById(R.id.edt);
        pass_edt=(EditText) findViewById(R.id.edt2);
        tip=(TextView)findViewById(R.id.tip);
        checkBox=(CheckBox)findViewById(R.id.checkbox) ;
        register=(Button)findViewById(R.id.register);
        create.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        login_table=new Login(this,"Login_lib.db",null,1);
         cache = getSharedPreferences("cache", MODE_APPEND);
        repass=cache.getBoolean("isrember",false);
        if (repass){
         name_edt.setText(cache.getString("name",""));
         pass_edt.setText(cache.getString("pass",""));
         Toast.makeText(this,"这是我取出来的啊",Toast.LENGTH_LONG).show();
        }

        init();
    }

    private void init() {
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intentfornotifi = new Intent(MainActivity.this,FourActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intentfornotifi, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("通知")
                .setContentInfo("通知你去收钱了")
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_done))
                .setContentIntent(pi)
                .build();
        manager.notify(1,notification);
        Intent intent = new Intent("com.example.init");
        sendBroadcast(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create:
           /*     SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = login.edit();
                editor.putString("name","林晓");
                editor.putString("password","123456789");
                editor.putString("name1","大哥");
                editor.putString("password1","1234560");
                editor.putString("name2","789");
                editor.putString("password2","789564q");
                editor.apply();
                Toast.makeText(this,"成功",Toast.LENGTH_SHORT).show();*/
               // login_table=new Login(this,"Login_lib.db",null,1);
             //   SQLiteDatabase database = login_table.getWritableDatabase();
               // ContentValues values = new ContentValues();
             //   values.put("name","123456789aa");
              //  values.put("password","123456036");
//                ContentValues values2 = new ContentValues();
              //  values2.put("name2","大爷");
             //   values2.put("password2","qwr8965");
             //   database.insert("login",null,values2);
                break;
            case R.id.id:

               login();
                break;
            case R.id.register:
                register();
                break;
                default:
        }
    }

    private void register() {
        Intent intent = new Intent(this,ThirdActivity.class);
        startActivity(intent);
    }


    private void login() {

        final String name = name_edt.getText().toString().trim();
        final String pass = pass_edt.getText().toString().trim();
       SQLiteDatabase database1=  login_table.getWritableDatabase();
        Cursor cursor = database1.query("login", null, "name=? and password=?", new String[]{name, pass}, null, null, null);
        if (cursor.getCount()==0){
            create.setVisibility(View.GONE);
            tip.setVisibility(View.VISIBLE);
            tip.setText("你见到我了啊，说明你账号密码错误");
        }else {
            if (checkBox.isChecked()){
                SharedPreferences.Editor editor = cache.edit();
                editor.putString("name",name);
                editor.putString("pass",pass);
                editor.putBoolean("isrember",true);
                editor.apply();
                Log.i("000","我给你存进去了");
                cursor.close();
            }
            tip.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            intent.putExtra("content","给，拿着");
            startActivityForResult(intent,1);

        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                String dataStringExtra = data.getStringExtra("B给我的数据");
                Toast.makeText(this,dataStringExtra,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
