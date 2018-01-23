package com.my.expressquery;

import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.expressquery.fragment.MainFragment;
import com.my.expressquery.fragment.Network_query;
import com.my.expressquery.fragment.PersonalSetting;
import com.my.expressquery.fragment.my_press;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private MainFragment mainFragment;              //第一个
    private my_press unreceivedDelivery; //第二个
    private Network_query theDeliveryHasBeenReceived;                     //第3个
    private PersonalSetting personalSetting;                      //第4个

    //底下4个按钮
    private FrameLayout mFraglay_main_home;
    private FrameLayout mFraglay_main_UnreceivedDelivery;
    private FrameLayout mFraglay_main_TheDeliveryHasBeenReceived;
    private FrameLayout mFraglay_main_PersonalSetting;

    private ImageView mIm_main_home;
    private TextView mTv_main_home;

    private ImageView mIm_main_serch;
    private TextView mTv_main_serch;

    private ImageView mIm_main_collect;
    private TextView mTv_main_collect;

    private ImageView mIm_main_more;
    private TextView mTv_main_more;

    private FragmentManager fragmentManager;

    private NetWorkRecever recever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        init();
        choiseItem(0);//默认是选中第一个fragment
        Log.i("000", "MainActivity_onCreate");


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("000", "MainActivity_onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i("000", "MainActivity_onPause");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("000", "MainActivity_onDestroy");
        unregisterReceiver(recever);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("000", "MainActivity_onStop");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fraglay_main_home:            //主页
                choiseItem(0);
                break;
            case R.id.fraglay_main_UnreceivedDelivery: //我的快递
                choiseItem(1);
                break;
            case R.id.fraglay_main_TheDeliveryHasBeenReceived:  //网点查询
                choiseItem(2);
                break;
            case R.id.fraglay_main_PersonalSetting:         //个人设置
                choiseItem(3);
                break;
        }

    }


    private void choiseItem(int i) {
        clearStyle();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (i) {
            case 0:
                mIm_main_home.setImageResource(R.drawable.icon_home_sel);
                mTv_main_home.setTextColor(Color.parseColor("#FF94D697"));
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    transaction.add(R.id.fragment_layout, mainFragment);
                } else {
                    transaction.show(mainFragment);
                }
                break;
            case 1:
                mIm_main_serch.setImageResource(R.drawable.icon_square_sel);
                mTv_main_serch.setTextColor(Color.parseColor("#FF94D697"));
                if (unreceivedDelivery == null) {
                    unreceivedDelivery = new my_press();
                    transaction.add(R.id.fragment_layout, unreceivedDelivery);
                } else {
                    transaction.show(unreceivedDelivery);
                }
                break;
            case 2:
                mIm_main_collect.setImageResource(R.drawable.icon_selfinfo_sel);
                mTv_main_collect.setTextColor(getResources().getColor(R.color.colorGreen));
                if (theDeliveryHasBeenReceived == null) {
                    theDeliveryHasBeenReceived = new Network_query();
                    transaction.add(R.id.fragment_layout, theDeliveryHasBeenReceived);
                } else {
                    transaction.show(theDeliveryHasBeenReceived);
                }
                break;
            case 3:
                mIm_main_more.setImageResource(R.drawable.icon_more_sel);
                mTv_main_more.setTextColor(getResources().getColor(R.color.colorGreen));
                if (personalSetting == null) {
                    personalSetting = new PersonalSetting();
                    transaction.add(R.id.fragment_layout, personalSetting);
                } else {
                    transaction.show(personalSetting);
                }
                break;
        }
        transaction.commit();

    }

    //隐藏所有的fragment
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (mainFragment != null) {
            fragmentTransaction.hide(mainFragment);
        }

        if (unreceivedDelivery != null) {
            fragmentTransaction.hide(unreceivedDelivery);
        }

        if (theDeliveryHasBeenReceived != null) {
            fragmentTransaction.hide(theDeliveryHasBeenReceived);
        }

        if (personalSetting != null) {
            fragmentTransaction.hide(personalSetting);
        }
    }

    //清除所有的样式
    private void clearStyle() {
        mIm_main_home.setImageResource(R.drawable.icon_home_nor);
        mTv_main_home.setTextColor(getResources().getColor(R.color.colorWhite));
        mIm_main_serch.setImageResource(R.drawable.icon_square_nor);
        mTv_main_serch.setTextColor(getResources().getColor(R.color.colorWhite));
        mIm_main_collect.setImageResource(R.drawable.icon_selfinfo_nor);
        mTv_main_collect.setTextColor(getResources().getColor(R.color.colorWhite));
        mIm_main_more.setImageResource(R.drawable.icon_more_nor);
        mTv_main_more.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private void init() {

        mFraglay_main_home = (FrameLayout) findViewById(R.id.fraglay_main_home);
        mFraglay_main_UnreceivedDelivery = (FrameLayout) findViewById(R.id
                .fraglay_main_UnreceivedDelivery);
        mFraglay_main_TheDeliveryHasBeenReceived = (FrameLayout) findViewById(R.id
                .fraglay_main_TheDeliveryHasBeenReceived);
        mFraglay_main_PersonalSetting = (FrameLayout) findViewById(R.id
                .fraglay_main_PersonalSetting);

        mIm_main_home = (ImageView) findViewById(R.id.im_main_home);
        mTv_main_home = (TextView) findViewById(R.id.tv_main_home);

        mIm_main_serch = (ImageView) findViewById(R.id.im_main_serch);
        mTv_main_serch = (TextView) findViewById(R.id.tv_main_serch);

        mIm_main_collect = (ImageView) findViewById(R.id.im_main_collect);
        mTv_main_collect = (TextView) findViewById(R.id.tv_main_collect);

        mIm_main_more = (ImageView) findViewById(R.id.im_main_more);
        mTv_main_more = (TextView) findViewById(R.id.tv_main_more);

        mFraglay_main_home.setOnClickListener(this);
        mFraglay_main_UnreceivedDelivery.setOnClickListener(this);
        mFraglay_main_TheDeliveryHasBeenReceived.setOnClickListener(this);
        mFraglay_main_PersonalSetting.setOnClickListener(this);

        recever = new NetWorkRecever();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(recever, filter);
    }


}