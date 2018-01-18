package com.my.expressquery;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.expressquery.db.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.bmob.v3.BmobUser.getCurrentUser;
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
        CircleImageView head_title = (CircleImageView) findViewById(R.id.head_title);
        title.setText(R.string.information_update);
        mBack.setOnClickListener(this);
        mFirst.setOnClickListener(this);
        mSecond.setOnClickListener(this);
        mThree.setOnClickListener(this);
        String path = BmobUser.getCurrentUser(MyUser.class).getPath();
        byte[] bytes = Base64.decode(path, Base64.DEFAULT);
        head_title.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
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
        if (getCurrentUser(MyUser.class) != null) {
            Boolean emailVerified = (Boolean) BmobUser.getObjectByKey("emailVerified");
            Log.i("000", emailVerified + "这是email的装填");
            if (emailVerified) {
                Toast.makeText(this, "邮箱已经验证通过", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("000", (String) BmobUser.getObjectByKey("email"));
                getCurrentUser(MyUser.class).requestEmailVerify((String) BmobUser
                        .getObjectByKey
                                ("email"), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        Log.i("000", "发送成功");
                    }
                });
                Toast.makeText(this, "已发送邮件，请查收", Toast.LENGTH_SHORT).show();
            }

        }
    }

    // TODO: 2017/12/12   目前还有问题

    private void showdiago() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入您的新密码");
        View view = LayoutInflater.from(this).inflate(R.layout.updatepass, null);
        final EditText mOldPs = (EditText) view.findViewById(R.id.old_ps);
        final EditText mNewPs = (EditText) view.findViewById(R.id.new_ps);
        builder.setView(view);
        builder.setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              /*  BmobUser.updateCurrentUserPassword(mOldPs.getText().toString(), mNewPs.getText()
                        .toString(), new UpdateListener() {


                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(ChangeActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("000", e.toString());
                            Toast.makeText(ChangeActivity.this, "gggggggggg", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
            }
        });*/
            }
        });
        builder.setNegativeButton(R.string.cancel_capture, null);
        builder.create().show();
    }

}

        
