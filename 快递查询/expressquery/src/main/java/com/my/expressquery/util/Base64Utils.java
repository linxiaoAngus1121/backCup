package com.my.expressquery.util;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by 123456 on 2018/1/18.
 * Base64加密后返回路径显示
 */

public class Base64Utils {
    /**
     * Base64加密，然后转成String类型保存，以后就不会出现在不同的设备上无法获取头像问题
     *
     * @param bit 哪张图片
     */
    public static String encrypt(Bitmap bit) {
        String road = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 50, baos);
        road = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        return road;
    }
}
