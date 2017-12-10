package com.example.uiforapplication;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123456 on 2017/7/13.
 */

public class ActivityCollect {

    public  static List<Activity > activitylist= new ArrayList<Activity>();

    public  static   void addActivity(Activity activity){
        activitylist.add(activity);
    }

    public  static void removeActivity(Activity activity){
        activitylist.remove(activity);
    }

    public  static  void finshALL(){

        /*

    如果不是finish,则将其finish掉
    */
        for (Activity activity:activitylist){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
