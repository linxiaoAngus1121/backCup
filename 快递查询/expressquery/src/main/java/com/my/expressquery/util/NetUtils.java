package com.my.expressquery.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 123456 on 2017/10/26.
 * 判断是否有网络
 */

public class NetUtils {

    public static boolean checkNet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected() && info.isAvailable()) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
