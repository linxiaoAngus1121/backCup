package com.my.expressquery.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 123456 on 2018/1/2.
 * 分享工具类
 */

public class ShareUtils {
    public static void share(final Context context) {
        //一键分享,每个平台都会分享这个信息，如果调用分享到指定平台的话，应该是每个平台对应不同代码
        OnekeyShare share = new OnekeyShare();
        share.setText("我发现一款很好的APP，http://www.baidu.com复制此衔接下载");
        share.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object>
                    hashMap) {
                Log.i("000", "成功");
                Toast.makeText(context.getApplicationContext(), "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.i("000", throwable.toString());
                Log.i("000", "onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(context.getApplicationContext(), "分享取消", Toast.LENGTH_SHORT).show();
                Log.i("000", "onCancel" + i);
            }
        });
        share.show(context.getApplicationContext());


               /*
                指定平台分享
                Platform.ShareParams share = new Platform.ShareParams();
                share.setShareType(Platform.SHARE_IMAGE);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.back);
                share.setImageData(bitmap);
                Platform platform = ShareSDK.getPlatform(Wechat.NAME);
                platform.share(share);
                platform.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object>
                    hashMap) {
                        Log.i("000", "成功");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        Log.i("000", "onError");
                        Log.i("000", throwable.toString());
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        Log.i("000", "onCancel");
                    }
                });*/
    }
}
