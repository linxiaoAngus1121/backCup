package com.my.expressquery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.my.expressquery.fragment.updatePsFragment.Email_modeFragment;
import com.my.expressquery.fragment.updatePsFragment.Phone_modeFragment;

import cn.bmob.v3.Bmob;

/**
 * 考虑支持多种方式修改密码（1.邮箱修改，2.手机验证码修改）
 */
public class ForegestPsActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager mManager;
    private FragmentTransaction transaction;
    private TextView mEmail;
    private TextView mPhone;
    private Phone_modeFragment phone_modeFragment;
    private Email_modeFragment email_modeFragment;
    private final int SELECT_PHONE = 0;
    private final int SELECT_EMAIL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foregest_ps);
        init();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email:
                Choise(SELECT_EMAIL);
                break;
            case R.id.phone:
                Choise(SELECT_PHONE);
                break;
            default:
                break;
        }
    }

    private void Choise(int i) {
        clearStyle();
        transaction = mManager.beginTransaction();
        hideFragments(transaction);
        switch (i) {
            case SELECT_PHONE:
                mPhone.setTextColor(Color.BLUE);
                if (phone_modeFragment == null) {
                    phone_modeFragment = new Phone_modeFragment();
                    transaction.add(R.id.fill, phone_modeFragment);
                } else {
                    transaction.show(phone_modeFragment);
                }
                break;
            case SELECT_EMAIL:
                mEmail.setTextColor(Color.BLUE);
                if (email_modeFragment == null) {
                    email_modeFragment = new Email_modeFragment();
                    transaction.add(R.id.fill, email_modeFragment);
                } else {
                    transaction.show(email_modeFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void clearStyle() {
        mEmail.setTextColor(Color.GRAY);
        mPhone.setTextColor(Color.GRAY);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (phone_modeFragment != null) {
            transaction.hide(phone_modeFragment);
        }
        if (email_modeFragment != null) {
            transaction.hide(email_modeFragment);
        }
    }


    private void init() {
        mEmail = (TextView) findViewById(R.id.email);
        mPhone = (TextView) findViewById(R.id.phone);
        mEmail.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        mManager = getSupportFragmentManager();
        Choise(SELECT_EMAIL);
        Bmob.initialize(this, "11e8b284d54846a83eed910d02903646");
    }

}
