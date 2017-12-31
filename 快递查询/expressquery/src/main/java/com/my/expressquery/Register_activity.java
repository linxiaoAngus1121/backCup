package com.my.expressquery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.my.expressquery.db.MyUser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/***
 *
 * 头像上上传问题需要解决
 *
 */


public class Register_activity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEt_register_phone;
    private EditText mEd_register_yanzhengma;
    private EditText mEd_register_pass;
    private EditText mEd_register_emmail;

    private Button mBut_register_getcode;

    private Button mBut_register_register;
    private ImageView mTitlt_favicon;

    private String mHeadImagePaht;
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
                    Toast.makeText(Register_activity.this, "发生未知错误，请重试或检查验证码", Toast
                            .LENGTH_SHORT).show();
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
                if (TextUtils.isEmpty(code) || TextUtils.isEmpty(email) || TextUtils.isEmpty
                        (pass) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(Register_activity.this, "不允许有空项", Toast.LENGTH_SHORT).show();
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
                                Intent photo = null;
                                Log.i("000", Environment.getExternalStorageDirectory().toString()
                                        + "这是tostring");
                                Log.i("000", Environment.getExternalStorageDirectory()
                                        .getAbsolutePath() + "则是absoultepath");
                                Log.i("000", Environment.getExternalStorageDirectory().getPath()
                                        + "这是path");
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
                startPhotoZoom(data.getData());     //data.getdata()返回的是一个URi
                break;
            case 2: //图片裁剪
                if (data == null) {
                    Log.i("000", "intent为空");
                    return;
                }
                setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                break;
            case 8:
                Log.i("000", "我在这里");
                ImageSize size = getImageViewSize(mTitlt_favicon);
                Bitmap bit = decodeSampleImage(size.width, size.height);

                //应该将这个bitmap保存为文件，然后上图到服务器，登录的时候取这个路径，然后设置到imaebview
                if (bit == null) {
                    return;
                }
                mTitlt_favicon.setImageBitmap(bit);
                File file = new File(Environment.getExternalStorageDirectory(), "/myImage/small"
                        + System.currentTimeMillis() + ".jpg");
                try {
                    BufferedOutputStream buffer = new BufferedOutputStream(new FileOutputStream
                            (file));
                    bit.compress(Bitmap.CompressFormat.JPEG, 100, buffer);
                    buffer.flush();
                    buffer.close();
                    Log.i("000", "写成文件成功");
                    mHeadImagePaht = file.getAbsolutePath();

                    File file1 = new File(Environment.getExternalStorageDirectory(),
                            "/myImage/head.jpg");       //最后要把这个head.jpg删掉
                    if (file1.exists()) {
                        file1.delete();
                    }
                } catch (IOException e) {
                    Log.i("000", "写成文件失败");
                    e.printStackTrace();
                }
                break;
        }

    }


    /**
     * 压缩图片
     */
    private Bitmap decodeSampleImage(int width, int height) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;   //获取图片的宽高，但是不加载到内存中
        BitmapFactory.decodeFile(Environment.getExternalStorageDirectory
                () + "/myImage/head.jpg", option);//现在的option中就保存着图片的实际宽高
        //根据实际的宽高，还有我们期望的宽高进行压缩
        option.inSampleSize = caculateSampleSize(option, width, height);
        //重新绘制一个新的bitmap，压缩后的
        option.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory
                () + "/myImage/head.jpg", option);
        return decodeFile;
    }

    private int caculateSampleSize(BitmapFactory.Options option, int reqwidth, int reqheight) {
        int width = option.outWidth;
        int height = option.outHeight;
        int sampleSize = 1;
        if (width > reqwidth || height > reqheight) { //进行压缩
            int widthRadio = Math.round(width * 1.0f / reqwidth);   //进行四舍五入
            int heightxRadio = Math.round(height * 1.0f / reqheight);
            sampleSize = Math.max(widthRadio, heightxRadio); //取较大者，这样压缩的比列会更大，更节省内存，或者可以自定义压缩的策略
        }
        return sampleSize;
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
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory()
                .getPath() + "/" + "small.jpg");
        mHeadImagePaht = uritempFile.toString();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 2);
    }

    private void register(String path, String email, String pass, String phone) {

        //如果邮箱和手机号码,username有一个相同就会注册失败，所以邮箱，手机号码，username不能有相同的

        MyUser login = new MyUser();
        login.setMobilePhoneNumber(phone);
        login.setPassword(pass);
        login.setPath(path);
        login.setUsername(phone);
        login.setEmail(email);
        login.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser user_login, BmobException e) {
                if (user_login != null) {
                    Toast.makeText(Register_activity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    // TODO: 2017/12/31 考虑这里是跳 StartActivity 还是mainactivity
                    Intent intent = new Intent(Register_activity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("000", e.toString());
                    Toast.makeText(Register_activity.this, "嗷，手机号或邮箱已存在，请重试", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


    }

    /**
     * 根据imageview获取适当要压缩的宽和高
     */
    private ImageSize getImageViewSize(ImageView imageView) {
        ImageSize size = new ImageSize();
        /**
         * 分析：这个imageview可能时一个固定的值如xxxdp，等，也可能是match_parent,wrap_content等，所以要进行多重判断。
         * */
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int width = imageView.getWidth();

        if (width <= 0) {   //如果还没有在layout中绘制的话，那就是0
            width = layoutParams.width;//如果设置的是wrap_content或者match_parent，那么现在也是<=0
        }
        if (width <= 0) {
            width = imageView.getMaxWidth();//检查最大值,如果没有的话，那就再下一个if
        }
        if (width <= 0) {//实在不行的话，就只能是屏幕的宽度了
            width = displayMetrics.widthPixels;
        }


        int height = imageView.getHeight();

        if (height <= 0) {   //如果还没有在layout中绘制的话，那就是0
            height = layoutParams.height;//获取layout中设置的，如果设置的是wrap_content或者match_parent，那么现在也是<=0
        }
        if (height <= 0) {
            height = imageView.getMaxHeight();//检查最大值,如果没有的话，那就再下一个if
        }
        if (height <= 0) {//实在不行的话，就只能是屏幕的宽度了
            height = displayMetrics.heightPixels;
        }
        size.width = width;
        size.height = height;
        return size;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }


    private class ImageSize {
        int width;
        int height;
    }
}
