package com.my.expressquery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 123456 on 2018/1/19.
 * 执行两次NetWorkRecever，这时候对话框的内存地址是不一样的，所以导致无法取消dialog
 */

public class NetWorkRecever extends BroadcastReceiver {

    AlertDialog dialog;
    boolean flag = false;

    @Override
    public void onReceive(final Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.i("000", "receive执行");
        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("网络连接不可用，请设置网络");
        builder.setIcon(R.drawable.wifi);
        builder.setCancelable(false);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                context.startActivity(intent);      //打开系统设置
            }
        });
        dialog = builder.create();*/
        if (networkInfo == null || !networkInfo.isAvailable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("网络连接不可用，请设置网络");
            builder.setIcon(R.drawable.wifi);
            builder.setCancelable(false);
            builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    context.startActivity(intent);      //打开系统设置
                }
            });
            dialog = builder.create();
            Toast.makeText(context, "网络关闭",
                    Toast.LENGTH_SHORT).show();
            dialog.show();
            flag = true;
        } else {
            if (flag) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
            Log.i("000", "现在网络可用");
        }
    }
}
