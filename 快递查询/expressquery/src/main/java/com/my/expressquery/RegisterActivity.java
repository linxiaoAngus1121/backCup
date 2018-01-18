package com.my.expressquery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.my.expressquery.util.Base64Utils;
import com.my.expressquery.util.LoginAndReisterUtils;
import com.my.expressquery.util.PhotoUtils;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/***
 *
 * 头像上上传问题需要解决
 *
 */


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEt_register_phone;
    private EditText mEd_register_yanzhengma;
    private EditText mEd_register_pass;
    private EditText mEd_register_emmail;

    private Button mBut_register_getcode;

    private Button mBut_register_register;
    private ImageView mTitlt_favicon;

    private String mHeadImagePaht;
    private AlertDialog dialog;
    private Handler mhandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {        //msg.arg1:
            //0代表发送成功
            //2代表验证成功
            switch (msg.arg1) {
                case 0:
                    Toast.makeText(RegisterActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "发生未知错误，请重试或检查验证码", Toast
                            .LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(RegisterActivity.this, "11e8b284d54846a83eed910d02903646");
        mEt_register_phone = (EditText) findViewById(R.id.et_register_phone);
        mEd_register_yanzhengma = (EditText) findViewById(R.id.ed_register_yanzhengma);
        mEd_register_pass = (EditText) findViewById(R.id.ed_register_pass);
        mEd_register_emmail = (EditText) findViewById(R.id.ed_register_emmail);

        mBut_register_getcode = (Button) findViewById(R.id.get_code);
        mBut_register_register = (Button) findViewById(R.id.but_register_register);
        mTitlt_favicon = (ImageView) findViewById(R.id.iv_favicon);

        mBut_register_getcode.setOnClickListener(this);     //获取验证码按钮

        mBut_register_register.setOnClickListener(this);    //注册按钮
        mTitlt_favicon.setOnClickListener(this);        //头像

        EventHandler handler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message message = mhandle.obtainMessage();
                if (result == SMSSDK.RESULT_COMPLETE) {//回调成功
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {  //获取验证码成功
                        Log.i("000", "获取验证码成功");
                        message.arg1 = 0;       //发送成功
                        mhandle.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {    //提交了验证码
                        //可以进行注册操作了，因为如果提交验证码失败是不会走到这里来的
                        String email = mEd_register_emmail.getText().toString();
                        String pass = mEd_register_pass.getText().toString();
                        String phone = mEt_register_phone.getText().toString();
                        register(mHeadImagePaht, email, pass, phone);
                    }
                } else {                                //回调失败
                    Log.i("000", ((Throwable) data).getMessage() + "这是错误信息");
                    message.arg1 = 2;
                    mhandle.sendMessage(message);
                }
            }
        };
        SMSSDK.registerEventHandler(handler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code: //获取验证码
                String phonenumber = mEt_register_phone.getText().toString();
                if (TextUtils.isEmpty(phonenumber)) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(phonenumber);
                break;
            case R.id.but_register_register:    //注册按钮
                String email = mEd_register_emmail.getText().toString();
                String pass = mEd_register_pass.getText().toString();
                String phone = mEt_register_phone.getText().toString();
                String code = mEd_register_yanzhengma.getText().toString();
                if (TextUtils.isEmpty(code) || TextUtils.isEmpty(email) || TextUtils.isEmpty
                        (pass) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(RegisterActivity.this, "不允许有空项", Toast.LENGTH_SHORT).show();
                    return;
                }
                judgment_code(phone, code);         //这里进行验证码判断
                break;

            case R.id.iv_favicon:   //点击头像选择，然后上传
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(this, "你惨了，没有存储卡", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog = new AlertDialog.Builder(this)
                        .setTitle("选择头像")
                        .setSingleChoiceItems(new String[]{"相册", "拍照"}, -1, new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent photo;
                                File f = new File(Environment
                                        .getExternalStorageDirectory(), "/myImage/head.jpg");
                                if (!f.getParentFile().exists()) {
                                    f.getParentFile().mkdirs();
                                }
                                Uri uri = Uri.fromFile(f);
                                switch (which) {
                                    case 0:     //相册
                                        photo = new Intent(Intent.ACTION_GET_CONTENT);
                                        photo.setType("image/*");
                                        startActivityForResult(photo, 0);
                                        break;
                                    case 1:     //拍照
                                        photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        photo.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                        startActivityForResult(photo, 8);
                                        break;
                                }
                            }
                        }).create();
                dialog.show();
                break;
        }
    }


    private void judgment_code(String phone, String code) {
        SMSSDK.submitVerificationCode("86", phone, code);

    }

    private void sendMessage(String phone) {
        SMSSDK.getVerificationCode("86", phone);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.cancel();
        switch (requestCode) {
            case 0:         //图库选择
                if (data == null) {
                    Log.i("000", "intent为空");
                    return;
                }
                Log.i("000", data.getData() + "这是图库选择返回的url");
                Intent intent = PhotoUtils.startPhotoZoom(data.getData());
                //这里需要修改
                //  mHeadImagePaht = intent.getStringExtra("myNeedInfor");
                startActivityForResult(intent, 2);
                break;
            case 2: //图片裁剪
                if (data == null) {
                    Log.i("000", "intent为空");
                    return;
                }
                String s = PhotoUtils.setImageToView(mTitlt_favicon, data);// 让刚才选择裁剪得到的图片显示在界面上
                mHeadImagePaht = s;
                break;
            case 8:
                Log.i("000", "我在这里");
                PhotoUtils.ImageSize size = PhotoUtils.getImageViewSize(mTitlt_favicon);
                Bitmap bit = PhotoUtils.decodeSampleImage(size.width, size.height);
                if (bit == null) {
                    return;
                }
                //这里将Bitmap转成byte[]数组
                mHeadImagePaht = Base64Utils.encrypt(bit);
                mTitlt_favicon.setImageBitmap(bit);
                break;
        }
    }

    private void register(String path, String email, String pass, String phone) {
        //如果邮箱和手机号码,username有一个相同就会注册失败，所以邮箱，手机号码，username不能有相同的
        LoginAndReisterUtils.register(path, email, pass, phone, new LoginAndReisterUtils
                .LoginAndRegisterListener() {
            @Override
            public void successFul(boolean flag) {
                if (flag) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    // TODO: 2017/12/31 考虑这里是跳 StartActivity 还是mainactivity
                    Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "嗷，手机号或邮箱已存在，请重试", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

}
