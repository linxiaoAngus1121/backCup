package com.my.expressquery;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.my.expressquery.util.LoginAndReisterUtils;
import com.my.expressquery.util.NetUtils;

import cn.bmob.v3.Bmob;


public class Login_Activity extends BaseActivity implements View.OnClickListener {

    private EditText mEdit_account;
    private EditText mEdit_mima;
    private Button mLogin;
    private TextView mText_forget;
    private TextView mText_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean isconnected = NetUtils.checkNet(this);
        if (!isconnected) {
            Toast.makeText(this, "出事了，没网", Toast.LENGTH_SHORT).show();
        }
        Bmob.initialize(Login_Activity.this, "11e8b284d54846a83eed910d02903646");
        init();
    }


    private void init() {
        mEdit_account = (EditText) findViewById(R.id.edit_account);
        mEdit_mima = (EditText) findViewById(R.id.edit_mima);
        mText_forget = (TextView) findViewById(R.id.forget_pass);
        mText_register = (TextView) findViewById(R.id.register);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mText_forget.setOnClickListener(this);
        mText_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:            //登录
                String account = mEdit_account.getText().toString();
                String pass = mEdit_mima.getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Login_Activity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                mLogin.setClickable(false);
                login(account, pass);
                break;
            case R.id.register:        //注册
                //跳转到新的activity
                register();
                break;
            case R.id.forget_pass:     //忘记密码
                //跳转到新的activity
                findps();
                break;
        }
    }

    private void findps() {
        Intent intent = new Intent(Login_Activity.this, ForegestPsActivity.class);
        startActivity(intent);
    }

    private void register() {
        Intent intetnt = new Intent(Login_Activity.this, RegisterActivity.class);
        startActivity(intetnt);
    }

    private void login(String usrename, String pass) {
        //采用接口回调进行登录,进行解耦
        LoginAndReisterUtils.login(usrename, pass, new LoginAndReisterUtils
                .LoginAndRegisterListener() {
            @Override
            public void successFul(boolean flag) {
                if (flag) {
                    mLogin.setText("登录成功，请稍后");
                    Intent intetnt = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(intetnt);
                    finish();
                } else {
                    Toast.makeText(Login_Activity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
