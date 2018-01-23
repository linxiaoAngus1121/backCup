package com.my.expressquery.fragment.updatePsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.my.expressquery.R;
import com.my.expressquery.util.LoginAndReisterUtils;

/**
 * Created by 123456 on 2018/1/7.
 * 应该是通过手机验证码修改
 */

public class Phone_modeFragment extends Fragment implements View.OnClickListener {

    private EditText mEditTextPhone;
    private EditText mEditTextPs;
    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_ps_phone, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mEditTextPhone = (EditText) view.findViewById(R.id.update_ps_phone);
        mEditTextPs = (EditText) view.findViewById(R.id.update_ps_newPs);
        mButton = (Button) view.findViewById(R.id.update_ps_go);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        Log.i("000", "这是Phone_onStart");
        super.onStart();

    }

    @Override
    public void onResume() {
        Log.i("000", "这是Phone_onResume");
        super.onResume();

    }

    @Override
    public void onPause() {
        Log.i("000", "这是Phone_onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("000", "这是Phone_onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("000", "这是Phone_onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("000", "这是Phone_onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // TODO: 2018/1/21  这里要进行验证码验证，没有进行，记得实现
        String phone = mEditTextPhone.getText().toString();
        String newPS = mEditTextPs.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(newPS)) {
            Toast.makeText(getContext(), "请填写缺省项", Toast.LENGTH_SHORT).show();
            return;
        }
        LoginAndReisterUtils.updatePsByPhone(phone, newPS);
    }
}
