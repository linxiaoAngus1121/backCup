package com.example.service;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


public class AidlServer extends Service {

    String colors[] =new String[]{
            "黄色","白色","蓝色"
    };

    double weights[] =new double[]{
            3.2,
            2.6,
            1.9
    };
    private String color;
    private double weight;
    CatBinder catBinder;
    Timer timer;
    public AidlServer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //返回的是binder的代理对象，在serviceconnettion中需要特殊处理
      return catBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        catBinder= new CatBinder();
         timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //生成一个随机的颜色和宽度，Math.random()会生成0.0-1.0之间的数，*3就会生成3以内的任何数字，取整就是1，2，3
                int v = (int)(Math.random() * 3);
                    color = colors[v];
                    weight=weights[v];
                    Log.i("000","--------"+v);
            }
        },0,800);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

//使用一个自定义类继承于Stub(来自于我们刚刚定义的aidl文件)
    public class CatBinder extends Icat.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getcolor() throws RemoteException {
            return color;
        }

        @Override
        public double getweight() throws RemoteException {
            return weight;
        }
    }



}
