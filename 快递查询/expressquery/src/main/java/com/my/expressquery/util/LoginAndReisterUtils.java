package com.my.expressquery.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.my.expressquery.db.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.R.attr.id;

/**
 * Created by 123456 on 2018/1/2.
 * 登录注册工具,修改密码等
 */

public class LoginAndReisterUtils {
    private static LoginAndRegisterListener mListener;
    private static String objectid;

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

    /**
     * 邮箱方式修改密码
     */
    public static void updatePsByEmail(final AppCompatActivity activity, String
            emailAddress, final View v) {
        hideKeyboard(activity);
        BmobUser.resetPasswordByEmail(emailAddress, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    Log.i("000", e.toString());
                    return;
                }
                //相当于toast,但是有个打开邮箱的按钮
                Snackbar.make(v, "发送邮件成功", Snackbar.LENGTH_LONG)
                        .setAction("点我打开邮箱", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(getContext(), "已经打开邮箱", Toast.LENGTH_SHORT).show();
                                Intent intent;
                                PackageManager packageManager = activity
                                        .getPackageManager();
                                intent = packageManager.getLaunchIntentForPackage("com.android" +
                                        ".email");
                                if (intent == null) {
                                    Toast.makeText(activity, "没有邮件类应用", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
                                        .FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent
                                        .FLAG_ACTIVITY_CLEAR_TOP);
                                activity.startActivity(intent);
                            }
                        })
                        .show();
            }
        });
    }

    /**
     * 调用此方法对键盘进行处理，防止遮挡住弹出的Snackbar
     */
    private static void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager
                                .HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 手机验证码方式修改密码
     * 暂时无法修改
     *
     * @param phone 用户的手机号码
     * @param newPS 用户的新密码
     */
    public static void updatePsByPhone(String phone, final String newPS) {
        //这个是空，因为没有登录，这里要先查询到objectid（根据手机号进行查询）

        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addQueryKeys("objectId");
        query.addWhereEqualTo("mobilePhoneNumber", phone);
        //异步出现问题，这里还没执行到，已经返回了
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                Log.i("000", list.size() + "listsize的值");
                objectid = list.get(0).getObjectId();
                Log.i("000", objectid + "这是objectid的值");
                update(newPS, objectid);
            }
        });

    }

    private static void update(String newPS, String objectid) {
        MyUser user = new MyUser();
        user.setPassword(newPS);
        Log.i("000", id + "这是回传id");
        user.update(objectid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("000", "修改成功");
                } else {
                    Log.i("000", e.toString() + "修改失败");
                }
            }
        });
    }


    public interface LoginAndRegisterListener {
        void successFul(boolean flag);
    }
}
