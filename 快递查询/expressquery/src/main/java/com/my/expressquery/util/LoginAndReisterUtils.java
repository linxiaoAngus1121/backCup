package com.my.expressquery.util;

import android.util.Log;

import com.my.expressquery.db.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 123456 on 2018/1/2.
 */

public class LoginAndReisterUtils {
    private static LoginAndRegisterListener mListener;

    public static void login(String usrename, String pass, LoginAndRegisterListener
            mloginListener) {
        mListener = mloginListener;
        BmobUser.loginByAccount(usrename, pass, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if (user != null) {
                    //登录成功
                    Log.i("000", "login方法");
                    if (mListener != null) {
                        mListener.successFul(true);
                    }
                } else {
                    Log.i("000", e.getMessage());
                    Log.i("000", e.toString());
                    if (mListener != null) {
                        mListener.successFul(false);
                    }

                }
            }
        });

    }


    public static void register(String path, String email, String pass, String phone,
                                LoginAndRegisterListener mRegisterListener) {
        mListener = mRegisterListener;
        MyUser register = new MyUser();
        register.setMobilePhoneNumber(phone);
        register.setPassword(pass);
        register.setPath(path);
        register.setUsername(phone);
        register.setEmail(email);
        register.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser user_register, BmobException e) {
                if (user_register != null) {
                    mListener.successFul(true);
                } else {
                    Log.i("000", e.toString() + "注册失败反馈");
                    mListener.successFul(false);
                }
            }
        });

    }


    public interface LoginAndRegisterListener {
        void successFul(boolean flag);
    }
}
