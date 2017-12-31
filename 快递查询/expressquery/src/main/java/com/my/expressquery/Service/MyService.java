package com.my.expressquery.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.my.expressquery.db.Data;
import com.my.expressquery.db.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MyService extends Service {


    private BmobQuery<Data> query;
    private BmobQuery<com.my.expressquery.db.Data> query1;
    private List<BmobQuery<com.my.expressquery.db.Data>> list;
    private BmobQuery<Data> querylast;

    public MyService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("000", intent.getStringExtra("odd") + "onstartcommd");
        final String odd = intent.getStringExtra("odd");       //单号
        final String name = intent.getStringExtra("name");  //快递名称
        final String code = intent.getStringExtra("code");
        final BmobUser currentUser = BmobUser.getCurrentUser(MyUser.class);
        new Thread(new Runnable() {
            @Override
            public void run() {

                //第一个条件
                query = new BmobQuery<Data>();
                query.addWhereEqualTo("odd", odd);   //快递单号和当前用户为条件

                //第二个条件
                query1 = new BmobQuery<Data>();
                query1.addWhereEqualTo("user", currentUser);   //快递单号和当前用户为条件

                //总的条件
                list = new ArrayList<>();
                list.add(query);
                list.add(query1);

                //开始查询
                querylast = new BmobQuery<Data>();
                querylast.and(list);        //多个条件
                querylast.findObjects(new FindListener<Data>() {
                    @Override
                    public void done(final List<Data> list, BmobException e) {
                        Log.i("000", list.size() + "拿到要存储的size");
                        if (list.size() == 0) {
                            //证明数据库没有这个数据，所以存储
                            com.my.expressquery.db.Data data = new com.my.expressquery.db.Data();
                            data.setUser(currentUser);
                            data.setOdd(odd);
                            data.setName(name);
                            data.setCode(code);
                            data.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Log.i("000", "保存成功");
                                        list.clear();
                                    } else {
                                        list.clear();
                                        Log.i("000", e.toString());
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("000", "onDestroy+++++++service");
        //回收资源
        list.clear();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
