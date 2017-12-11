package com.my.expressquery;

import android.app.Activity;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Register_activity extends Activity implements View.OnClickListener {

    private EditText mEt_register_phone;
    private EditText mEd_register_yanzhengma;
    private EditText mEd_register_pass;
    private EditText mEd_register_emmail;

    private Button mBut_register_getcode;

    private Button mBut_register_register;
    private ImageView mTitlt_favicon;

    private Uri tempUri;
    private Uri uritempFile;
    private AlertDialog dialog;
    private EventHandler handler;
    private Handler mhandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {        //msg.arg1:
            //0代表发送成功
            //2代表验证成功
            switch (msg.arg1) {
                case 0:
                    Toast.makeText(Register_activity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(Register_activity.this, "发生未知错误，请重试或检查验证码", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(Register_activity.this, "11e8b284d54846a83eed910d02903646");
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

        handler = new EventHandler() {
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
                        register(email, pass, phone);
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
                    Toast.makeText(Register_activity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(phonenumber);
                break;
            case R.id.but_register_register:    //注册按钮
                String email = mEd_register_emmail.getText().toString();
                String pass = mEd_register_pass.getText().toString();
                String phone = mEt_register_phone.getText().toString();
                String code = mEd_register_yanzhengma.getText().toString();
                if (TextUtils.isEmpty(code) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(Register_activity.this, "不允许有空项", Toast.LENGTH_SHORT).show();
                    return;
                }
                judgment_code(phone, code);         //这里进行验证码判断
                break;

            case R.id.iv_favicon:   //点击头像选择，然后上传
                //4.4.4出现点击闪退
                dialog = new AlertDialog.Builder(this)
                        .setTitle("选择头像")
                        .setSingleChoiceItems(new String[]{"相册"}, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:     //相册
                                        Intent photo = new Intent(Intent.ACTION_GET_CONTENT);
                                        photo.setType("image/*");
                                        startActivityForResult(photo, 0);
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
        if (data == null) {
            Log.i("000", "intent为空");
            return;
        }
        switch (requestCode) {
            case 0:         //图库选择
                Log.i("000", data.getData() + "这是图库选择返回的url");
                startPhotoZoom(data.getData());     //data.getdata()返回的是一个URi
                break;
            case 2: //图片裁剪
                setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
               /* try {
                        Log.i("000", "我走了这里+++++++");
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                    mTitlt_favicon.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/

                break;
        }


    }

    //显示图片
    private void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            mTitlt_favicon.setImageBitmap(photo);
        }
    }

    //裁剪图片
    private void startPhotoZoom(Uri ul) {
        if (ul == null) {
            Toast.makeText(this, "gg，出问题了", Toast.LENGTH_LONG).show();
            return;
        }
        Log.i("000", "我走了startPhotoZoom");
        //        tempUrl = ul;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(ul, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 2);
    }

    private void register(String email, String pass, String phone) {

        //如果邮箱和手机号码,username有一个相同就会注册失败，所以邮箱，手机号码，username不能有相同的
        BmobUser login = new BmobUser();
        login.setMobilePhoneNumber(phone);
        login.setPassword(pass);
        login.setUsername(phone);
        login.setEmail(email);
        login.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser user_login, BmobException e) {
                if (user_login != null) {
                    Toast.makeText(Register_activity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register_activity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("000", e.toString());
                    Toast.makeText(Register_activity.this, "嗷，手机号或邮箱已存在，请重试", Toast.LENGTH_SHORT).show();
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
