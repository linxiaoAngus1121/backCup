package com.my.expressquery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(StartActivity.this, "11e8b284d54846a83eed910d02903646");
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobUser bmobUser = BmobUser.getCurrentUser();  //不用每次都登录，如果bmobUser不为空，则之前登录过
                Intent intent;
                if (bmobUser != null) {
                    //需要在这里提前更新一下用户数据，假如控制台修改了信息，这里才能及时更新
                    BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.i("000", "Newest UserInfo is " + s);

                            } else {
                                Log.i("000", "Newest UserInfo is " + e.toString());//出错
                            }
                        }
                    });
                    Log.i("000", BmobUser.getCurrentUser().getEmailVerified() + "ttttttt");
                    intent = new Intent(StartActivity.this, MainActivity.class);    //跳转到主页
                } else {
                    intent = new Intent(StartActivity.this, Login_Activity.class);      //跳转到登录界面
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
