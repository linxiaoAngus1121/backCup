package com.my.forward;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.forward.mvp.sourcequery.presenter.SourcePresenter;
import com.my.forward.mvp.sourcequery.view.ISourceView;

import java.io.InputStream;

public class SourceQueryActivity extends BaseActivity implements ISourceView {
    private EditText editText;  //验证码
    private EditText mEditnum;  //学号
    private EditText mEditPass; //密码
    private ImageView miv;
    private TextView mtv;
    private SourcePresenter presenter = new SourcePresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorequery);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);
        mtv = (TextView) findViewById(R.id.show);
        mEditnum = (EditText) findViewById(R.id.ed_num);
        mEditPass = (EditText) findViewById(R.id.edit_password);
        miv = (ImageView) findViewById(R.id.showCode);
        editText = (EditText) findViewById(R.id.edit_ps);
        //预备登陆
        presenter.prepareLogin();
    }


    /**
     * 执行登录
     *
     * @param view 登录按钮
     */
    public void login(View view) {

        presenter.login();
    }


    @Override
    public String getstudNo() {
        return mEditnum.getText().toString();
    }

    @Override
    public String getstuPs() {
        return mEditPass.getText().toString();
    }

    @Override
    public String getCode() {
        return editText.getText().toString();
    }

    @Override
    public void showSource() {
        mtv.setText("查询成绩成功");

    }

    @Override
    public void showSourceError(String s) {
        mtv.setText("查询成绩失败" + s);

    }

    @Override
    public void showCode(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        miv.setImageBitmap(bitmap);
    }

    @Override
    public void showCodeError(String s) {
        Toast.makeText(this, "获取验证码失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showViewStateError(String s) {
        Toast.makeText(this, "获取viewstate失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginSuccess() {
        mtv.setText("登录成功");
    }

    @Override
    public void showLoginError() {
        mtv.setText("失败");
    }
}