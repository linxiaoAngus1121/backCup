package com.coolweather.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.coolweather.android.gson.Weather;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updatePic();
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int hours=8*60*60*1000;  //8小时
        long triggerAtTime= SystemClock.elapsedRealtime()+hours;
        Intent intent1=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,intent1,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        manager.cancel(pi);
        return super.onStartCommand(intent, flags, startId);

    }

    private void updatePic() {
        String url="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic",string);
                editor.apply();
            }
        });

    }

    private void updateWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weather = sharedPreferences.getString("weather",null);
        if (weather!=null){
            Weather weather1 = Utility.handleWeatherResponse(weather);
            String id=weather1.basic.weatherId;
            String url="http://guolin.tech/api/weather?cityid="+id+"&key=1d69930e3e3142b58c10feaea20cb812";
            Log.i("000",id+"sfsfsfsfsdfsfsfsf");
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string();
                    Log.i("000",s);
                    Weather weather2 = Utility.handleWeatherResponse(s);
                    if (weather2!=null && "ok".equals(weather2.status)){
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather",s);
                        editor.apply();
                    }
                }
            });
        }


    }
}
