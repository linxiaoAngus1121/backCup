package com.my.expressquery.fragment.updatePsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.my.expressquery.R;
import com.my.expressquery.customView.ButtonM;
import com.my.expressquery.util.LoginAndReisterUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 123456 on 2018/1/7.
 * 密码修改（邮箱方式）
 */

public class Email_modeFragment extends Fragment implements View.OnClickListener {
    private EditText mEditTextEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.update_ps_email, container, false);
        ButtonM mButtonM = (ButtonM) v.findViewById(R.id.updte);
        mEditTextEmail = (EditText) v.findViewById(R.id.email);

        mButtonM.setOnClickListener(this);
        Log.i("000", "这是Email_oncreateView");
        return v;
    }


    @Override
    public void onStart() {
        Log.i("000", "这是Email_onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("000", "这是Email_onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("000", "这是Email_onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("000", "这是Email_onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("000", "这是Email_onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("000", "这是Email_onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        String emailAddress = mEditTextEmail.getText().toString();
        if (TextUtils.isEmpty(emailAddress) || !isEmail(emailAddress)) {
            Toast.makeText(getContext(), "邮箱地址错误", Toast.LENGTH_SHORT).show();
            return;
        }
        update((AppCompatActivity) getActivity(), emailAddress, v);
    }

    private void update(AppCompatActivity activity, String emailAddress, final View v) {
        LoginAndReisterUtils.updatePsByEmail(activity, emailAddress, v);
    }

    /**
     * 判断是否是邮箱
     */
    private boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" +
                "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
