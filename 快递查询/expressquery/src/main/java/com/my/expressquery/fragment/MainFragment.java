package com.my.expressquery.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.expressquery.MyInterFace.CallBack;
import com.my.expressquery.R;
import com.my.expressquery.Service.MyService;
import com.my.expressquery.ShowDiagoActivity;
import com.my.expressquery.util.QueryUtils;
import com.my.expressquery.util.ShareUtils;
import com.my.expressquery.zxing.activity.CaptureActivity;

import static android.app.Activity.RESULT_OK;


public class MainFragment extends Fragment implements View.OnClickListener, CallBack {

    private ImageView mImageView;
    private ImageView mImageView_odd;
    private EditText mEditText;
    private EditText mEditText_odd;
    private Button mButton_Query;
    private Button mButton_Reset;
    private ProgressDialog progressDialog;
    private String code;
    private Intent intent_startService;
    private ImageButton share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        Log.i("000", "这是Mainfragmetn_onCreateView");
        init(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("000", "Maintfragemtn_onAttach");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_main_company:
                showdialogBox();
                break;
            case R.id.iv_main_odd: //扫码触发
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 1);   //让数据返回本页面，自动输入快递单号那个框
                break;
            case R.id.but_Query:
                //查询按钮,查询快递，并且要把查询的数据放到远程数据库
                // ，不过首先要检查是否已经存在这个快递单号的记录了，已经存在就不要存了
                // ，建议开个service进行这些操作（已经开了servcice）

                String odd = mEditText_odd.getText().toString();
                if (TextUtils.isEmpty(odd)) {
                    Toast.makeText(getContext(), "单号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();
                QueryUtils util = new QueryUtils();
                // TODO: 2017/11/28 lalallala 
                util.queryNoName(MainFragment.this, odd, null);

                break;
            case R.id.but_reset:
                if (!TextUtils.isEmpty(mEditText.getText())) {
                    mEditText.setText("");
                }
                if (!TextUtils.isEmpty(mEditText_odd.getText())) {
                    mEditText_odd.setText("");
                }
                break;
            case R.id.share:
                //分享
                ShareUtils.share(getContext());
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("000", "这是Mainfragmetn_onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("000", "Mainfragmetn_onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("000", "这是Mainfragmetn_onStop");
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (intent_startService != null) {
            getActivity().stopService(intent_startService);
            Log.i("000", "intentservice不为空");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("000", "这是Mainfragmetn_onDestroy");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                //扫描按钮触发的
                if (resultCode == RESULT_OK) {
                    String result = data.getStringExtra("result");
                    mEditText_odd.setText(result);
                }
                break;
            case 2:     //这个是跳转到新的acitivy，进行选择后回来的结果
                if (data != null) {
                    Log.i("000", data.getStringExtra("item") + "这是选择的快递，传过来的");
                    mEditText.setText(data.getStringExtra("item"));
                    Log.i("000", data.getStringExtra("code_id") + "这是选择的快递代号，传过来的");
                    code = data.getStringExtra("code_id");
                }
                break;
        }
    }


    //显示选择快递的对话框
    private void showdialogBox() {
        Intent intent = new Intent(getContext(), ShowDiagoActivity.class);
        startActivityForResult(intent, 2);
    }

    private void init(View view) {
        mImageView_odd = (ImageView) view.findViewById(R.id.iv_main_odd);
        mImageView = (ImageView) view.findViewById(R.id.iv_main_company);
        mEditText = (EditText) view.findViewById(R.id.ed_main_company);
        mEditText_odd = (EditText) view.findViewById(R.id.et_main_odd);
        mButton_Reset = (Button) view.findViewById(R.id.but_reset);
        mButton_Query = (Button) view.findViewById(R.id.but_Query);
        mImageView.setOnClickListener(this);
        mImageView_odd.setOnClickListener(this);    //扫描按钮
        mButton_Query.setOnClickListener(this);
        mButton_Reset.setOnClickListener(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("正在查询");
        share = (ImageButton) view.findViewById(R.id.share);
        share.setOnClickListener(this);
    }


    //传过来的正确数据（接口回调）
    @Override
    public void success(String strings, String ex_name, String code_name) {
        Looper.prepare();
        progressDialog.cancel();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.express_info, null, false);
        AlertDialog.Builder diago = new AlertDialog.Builder(getContext()).setView(view);
        TextView tv = (TextView) view.findViewById(R.id.tv_Async_info);
        tv.setText(strings);
        diago.create().show();
        //启动service
        intent_startService = new Intent(getContext(), MyService.class);
        intent_startService.putExtra("name", ex_name);
        intent_startService.putExtra("odd", mEditText_odd.getText().toString());
        intent_startService.putExtra("code", code_name);
        getActivity().startService(intent_startService);
        Looper.loop();
    }

    //发生错误
    @Override
    public void error(String strings) {
        Looper.prepare();
        if (!TextUtils.isEmpty(strings)) {
            progressDialog.cancel();
            Toast.makeText(getContext(), "查询出错", Toast.LENGTH_SHORT).show();
        }
        Looper.loop();
    }
}
