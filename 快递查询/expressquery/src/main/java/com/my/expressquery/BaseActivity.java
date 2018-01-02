package com.my.expressquery;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.my.expressquery.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

/*
* 在这里进行一些全局的初始化，比如判断网络
* */
public class BaseActivity extends AppCompatActivity {

    List<String> permissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyForRight();  //申请权限
    }

    private void setNetWork() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("网络连接不可用，请设置网络");
        builder.setIcon(R.drawable.wifi);
        builder.setCancelable(false);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);      //打开系统设置
            }
        });
        // builder.setNegativeButton("取消", null);
        builder.create().show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean isconnected = NetUtils.checkNet(this);
        if (!isconnected) {
            setNetWork();
        }

    }

    private void applyForRight() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager
                .PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.VIBRATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WAKE_LOCK);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissonArray = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissonArray, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意才能使用", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        permissionList.clear();
        permissionList = null;
    }

}
