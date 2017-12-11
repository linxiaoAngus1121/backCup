package com.my.expressquery.baiduMap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by 123456 on 2017/11/7.
 * 方向改变时的监听事件（需要方向传感器的支持）
 */

public class MyOrientationListener implements SensorEventListener {

    private SensorManager mManager;
    private Context mContext;
    private Sensor mSensor;
    private float lastX;

    public MyOrientationListener(Context context) {
        mContext = context;
    }

    //提供两个入口，一个开始，
    public void start() {
        mManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if (mManager != null) {
            //获得手机上的传感器
            mSensor = mManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if (mSensor != null) {
            //最后一个参数是设置精度的，
            mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }


    //一个结束
    public void stop() {
        mManager.unregisterListener(this);      //一定要取消注册
    }

    /*
        * 方向发生变化时调用
        * */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float x = event.values[SensorManager.DATA_X];
            if (Math.abs(x - lastX) > 1.0) {
                if (mOnOrientationListener != null) {       //如果界面注册了这个接口
                    mOnOrientationListener.onOrientationChanged(x);
                }
            }
            lastX = x;//回调给界面
        }
    }


    private OnOrientationListener mOnOrientationListener;

    public void setOnOrientationListener(
            OnOrientationListener mOnOrientationListener) {
        this.mOnOrientationListener = mOnOrientationListener;
    }

    public interface OnOrientationListener {
        void onOrientationChanged(float x);
    }

    /*
    * onAccuracyChanged当精度发生变化时调用
    * */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
