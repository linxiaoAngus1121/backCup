package com.coolweather.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.db.Login;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTv_name;
    private TextView mTv_pass;
    private Button mBu_login;
    private Button m_Bu_register;
    private EditText mEd_name;
    private EditText mEd_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTv_name = (TextView) findViewById(R.id.tv_name);
        mTv_pass = (TextView) findViewById(R.id.tv_pass);
        mBu_login = (Button) findViewById(R.id.bu_login);
        m_Bu_register = (Button) findViewById(R.id.bu_register);
        mEd_name = (EditText) findViewById(R.id.ed_name);
        mEd_pass = (EditText) findViewById(R.id.ed_pass);
        mBu_login.setOnClickListener(this);
        m_Bu_register.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bu_register:

                String username = mEd_name.getText().toString();
                String pass = mEd_pass.getText().toString();
                Connector.getDatabase();
                Login login = new Login();
                login.setUsername(username);
                login.setPassword(pass);
                login.save();
                Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bu_login:
                String name = mEd_name.getText().toString();
                String password = mEd_pass.getText().toString();
                login(name, password);
                break;
        }
    }

    private void login(String name, String password) {
        // TODO: 2017/8/15 这里开始，数据库语句已经没有问题了，记住用and
        List<Login> userList = DataSupport.where("username=? and password=?", name, password).find(Login.class);
        Intent intent = new Intent(this, MainActivity.class);
        String loginUsername=null;
        String loginPassword=null;
        if (userList.size() > 0) {
            for (Login login : userList) {
                 loginUsername = login.getUsername();
                 loginPassword = login.getPassword();
            }
           intent.putExtra("username",loginUsername);
            intent.putExtra("password",loginPassword);
            startActivity(intent);
        } else {
            Toast.makeText(this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
        }
    }


}
