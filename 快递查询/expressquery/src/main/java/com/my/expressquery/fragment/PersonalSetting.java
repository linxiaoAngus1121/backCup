package com.my.expressquery.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.expressquery.ChangeActivity;
import com.my.expressquery.Login_Activity;
import com.my.expressquery.R;
import com.my.expressquery.db.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by 123456 on 2017/9/24.
 */

public class PersonalSetting extends Fragment implements View.OnClickListener {


    private TextView mFour_phone;       //显示手机
    private TextView mFour_information;     //修改个人信息
    private LinearLayout mFour_address;     //地址本
    private LinearLayout mFour_call_developer;  //联系开发者
    private LinearLayout mFour_reward;      //打赏
    private LinearLayout mFour_logout;      //退出
    private ImageView mHeadImage;           //头像
    private Intent intent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("000", "onCreate444");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("000", "onActivityCreated44444");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("000", "onStart444");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("000", "onResume444");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("000", "onPause4444");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("000", "onStop444");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("000", "onDestroyView4444");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("000", "onDestroy44444");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("000", "onDetach4444");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.four_fragment, container, false);
        init(view);
        Log.i("000", "onCreateView44444");
        return view;
    }


    private void init(View view) {
        mFour_phone = (TextView) view.findViewById(R.id.four_phone);
        mFour_information = (TextView) view.findViewById(R.id.four_information);
        mFour_address = (LinearLayout) view.findViewById(R.id.four_address);
        mFour_call_developer = (LinearLayout) view.findViewById(R.id.four_call_developer);
        mFour_reward = (LinearLayout) view.findViewById(R.id.four_reward);
        mFour_logout = (LinearLayout) view.findViewById(R.id.four_logout);
        mHeadImage = (ImageView) view.findViewById(R.id.head_image);
        mFour_information.setOnClickListener(this);
        mFour_address.setOnClickListener(this);
        mFour_call_developer.setOnClickListener(this);
        mFour_reward.setOnClickListener(this);
        mFour_logout.setOnClickListener(this);
        Uri uri = Uri.parse(BmobUser.getCurrentUser(MyUser.class).getPath());
        if (uri == null) {
            mHeadImage.setImageResource(R.drawable.animal02);
        } else {
            mHeadImage.setImageURI(uri);
        }
        mFour_phone.setText(BmobUser.getCurrentUser(MyUser.class).getMobilePhoneNumber());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.four_information:
                change();
                break;
            case R.id.four_address:
                break;
            case R.id.four_call_developer:
                call();
                break;
            case R.id.four_reward:
                dahshang();
                break;
            case R.id.four_logout:
                logout();
                break;
            default:
                break;
        }
    }

    private void call() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragment_layout, new Contact_developFragment());
        transaction.addToBackStack(null);        //加入会退栈，不然无法返回
        transaction.commit();
    }

    private void dahshang() {
        DaShangFragment fragment = new DaShangFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        //如果是add的话，不会执行其他fragment的销毁方法，
        transaction.addToBackStack("main_for_fragment");        //加入会退栈，不然无法返回
        transaction.commit();
    }

    private void change() {
        intent = new Intent(getContext(), ChangeActivity.class);
        startActivity(intent);
    }

    private void logout() {
        BmobUser.logOut();      //会清除当前的缓存，即下次登录需要密码了

        intent = new Intent(getContext(), Login_Activity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
