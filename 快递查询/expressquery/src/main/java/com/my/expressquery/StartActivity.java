package com.my.expressquery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.my.expressquery.db.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

public class StartActivity extends Activity {

    private BmobUser bmobUser;  //不用每次都登录，如果bmobUser不为空，则之前登录过

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(StartActivity.this, "11e8b284d54846a83eed910d02903646");
        setContentView(R.layout.activity_start);
        bmobUser = BmobUser.getCurrentUser(MyUser.class);
        if (bmobUser != null) {
            //需要在这里提前更新一下用户数据，假如控制台修改了信息，这里才能及时更新
            BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.i("000", "正确情况下的Newest UserInfo is " + s);

                    } else {
                        Log.i("000", "出错情况下的数据Newest UserInfo is " + e.toString());//出错
                    }
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (bmobUser != null) {
                    intent = new Intent(StartActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(StartActivity.this, Login_Activity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
