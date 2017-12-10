package com.example.qunfaduanxin;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class Myservice extends Service {
    //壁纸的管理器
    WallpaperManager manager;
   //当前显示的壁纸
    int count=0;
    private int[] bit = new int[]{
            R.drawable.plane,
            R.drawable.ic_launcher
    };

    public Myservice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate() {
        manager=WallpaperManager.getInstance(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (count>2){
            count=0;
        }
        try {
            manager.setResource(bit[count++]);
            Log.i("000","更换成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
