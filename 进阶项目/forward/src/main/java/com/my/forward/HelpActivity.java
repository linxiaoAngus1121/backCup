package com.my.forward;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.my.forward.mvp.helpna.bean.Information;
import com.my.forward.mvp.helpna.presenter.UserCommitPresenter;
import com.my.forward.mvp.helpna.view.IUserCommitView;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener,
        IUserCommitView {


    private EditText mOdd;
    private EditText mName;
    private EditText mContact;
    private EditText mAddress_query;
    private EditText mAddress_your;
    private AppCompatDialog dialog;
    private UserCommitPresenter presenter = new UserCommitPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        bindViews();
    }

    private void bindViews() {
        mOdd = (EditText) findViewById(R.id.odd);
        mName = (EditText) findViewById(R.id.name);
        mContact = (EditText) findViewById(R.id.contact);
        mAddress_query = (EditText) findViewById(R.id.address_query);
        mAddress_your = (EditText) findViewById(R.id.address_your);
        Button mCommit = (Button) findViewById(R.id.commit);

        dialog = new AppCompatDialog(this);
        dialog.setTitle("正在处理");
        mCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //先保存到数据库，再模拟支付
        presenter.commit();
    }

    @Override
    public String getOdd() {
        return mOdd.getText().toString();
    }

    @Override
    public String getYourName() {
        return mName.getText().toString();
    }

    @Override
    public String getContact() {
        return mContact.getText().toString();
    }

    @Override
    public String getQuery_address() {
        return mAddress_query.getText().toString();
    }

    @Override
    public String getAddress() {
        return mAddress_your.getText().toString();
    }

    @Override
    public Information getAll() {
        return new Information(getOdd(), getYourName(), getContact(),
                getQuery_address(), getAddress());
    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (dialog.isShowing()) {
            dialog.cancel();
        }
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this, "写入失败", Toast.LENGTH_SHORT).show();
    }
}
