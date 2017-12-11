package com.my.expressquery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
/*
* 个人信息修改的activity
* */

public class ChangeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mBack;
    private RelativeLayout mFirst;
    private RelativeLayout mSecond;
    private RelativeLayout mThree;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        mBack = (TextView) findViewById(R.id.back);
        mFirst = (RelativeLayout) findViewById(R.id.first);
        mSecond = (RelativeLayout) findViewById(R.id.second);
        mThree = (RelativeLayout) findViewById(R.id.three);
        title = (TextView) findViewById(R.id.fragment_title);
        title.setText(R.string.information_update);
        mBack.setOnClickListener(this);
        mFirst.setOnClickListener(this);
        mSecond.setOnClickListener(this);
        mThree.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.first:    //头像
                break;
            case R.id.second:   //密码
                showdiago();
                break;
            case R.id.three:    //邮箱
                bind();
                break;
        }
    }

    //有问题
    private void bind() {
        if (BmobUser.getCurrentUser() != null) {
            Boolean emailVerified = (Boolean) BmobUser.getObjectByKey("emailVerified");
            Log.i("000", emailVerified + "这是email的装填");
            if (emailVerified) {
                Toast.makeText(this, "邮箱已经验证通过", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("000", (String) BmobUser.getObjectByKey("email"));
                BmobUser.getCurrentUser().requestEmailVerify((String) BmobUser.getObjectByKey("email"), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        Log.i("000", "发送成功");
                    }
                });
                Toast.makeText(this, "已发送邮件，请查收", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /*
    * 目前还有问题
    * */
    private void showdiago() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入您的新密码");
        View view = LayoutInflater.from(this).inflate(R.layout.updatepass, null);
        final EditText mOldPs = (EditText) view.findViewById(R.id.old_ps);
        final EditText mNewPs = (EditText) view.findViewById(R.id.new_ps);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String old = mOldPs.getText().toString();
                String new_ps = mNewPs.getText().toString();
                if (!TextUtils.isEmpty(old) && !TextUtils.isEmpty(new_ps)) {
                    Log.i("000", BmobUser.getCurrentUser().getSessionToken());
                    BmobUser.updateCurrentUserPassword(old, new_ps, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ChangeActivity.this,
                                        "密码修改成功，可以用新密码进行登录啦",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Log.i("000", e.getMessage());
                                Toast.makeText(ChangeActivity.this,
                                        "gg了，修改失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
