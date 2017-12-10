package com.coolweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coolweather.android.gson.Forecast;
import com.coolweather.android.gson.Weather;
import com.coolweather.android.service.AutoUpdateService;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherlayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;        //温度信息
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqIText;
    private TextView pm25Text;
    private TextView comfortText;       //舒适度
    private TextView carWashText;
    private TextView sportText;
    private ImageView bingPicImage;   //每日必应的图片
    public SwipeRefreshLayout swipeRefreshLayout;  //下拉刷新，即手动更新天气
    public DrawerLayout drawerLayout;      //左滑的菜单
    private Button navButton;               //title的最左边按钮，即home的图标
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherlayout=(ScrollView)findViewById(R.id.weather_layout);
        titleCity=(TextView)findViewById(R.id.title_city);
        titleUpdateTime=(TextView)findViewById(R.id.title_update_time);
        degreeText=(TextView)findViewById(R.id.degree_text);
        weatherInfoText=(TextView)findViewById(R.id.weather_info_text);
        aqIText=(TextView)findViewById(R.id.aqi_text);
        pm25Text=(TextView)findViewById(R.id.pm25_text);
        comfortText=(TextView)findViewById(R.id.comfort_text);
        carWashText=(TextView)findViewById(R.id.car_wash_text);
        sportText=(TextView)findViewById(R.id.sport_text);
        bingPicImage=(ImageView)findViewById(R.id.bing_pic_img);
        forecastLayout=(LinearLayout)findViewById(R.id.forecast_layout);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navButton=(Button)findViewById(R.id.nav_button);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        //设置进度条的颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        //从本地获取weather,如果没有就进行网络访问
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
        String weather = sharedPreferences.getString("weather", null);
        String weather_img = sharedPreferences.getString("bing_pic", null);
        final String weatherid;       //给下拉刷新用的天气id
        //系统大于5.0才会启用
        if(Build.VERSION.SDK_INT>=21){
            View view = getWindow().getDecorView();
            //设置全屏
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //把状态改为透明色
            getWindow().setStatusBarColor(Color.RED);
        }


        if (weather_img!=null){
            //加载每日必应的图片作为背景
            Glide.with(WeatherActivity.this).load(weather_img).into(bingPicImage);

        }else {
            loadImageFromInternt();
        }

        if (weather!=null){
            //有缓存时直接解析天气信息
            Weather weather1 = Utility.handleWeatherResponse(weather);
            weatherid= weather1.basic.weatherId;
            showWeatherInfo(weather1);
        }else {
            //没有缓存时直接访问网络
            weatherid=getIntent().getStringExtra("weather_id");
       //     Log.i("000",weatherid);
            //隐藏scrollowview
            weatherlayout.setVisibility(View.INVISIBLE);
            request(weatherid);
        }
        //下拉刷新后再次请求天气信息
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request(weatherid);
            }
        });
        //点击按钮后加载左滑菜单
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    /**
     * 根据id从网上查询数据
     *
     **/
    public void request(String weatherid) {
        String address="http://guolin.tech/api/weather?cityid="+weatherid+"&key=1d69930e3e3142b58c10feaea20cb812";
        Log.i("000","this is guolin address"+address);
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败,id网上查询"
                                ,Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String s = response.body().string();
                Log.i("000",s);
                final Weather weather = Utility.handleWeatherResponse(s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (weather!=null&&"ok".equals(weather.status)){
                            //写入本地
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",s);
                            editor.apply();
                            showWeatherInfo(weather);
                        }else {
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败哈哈哈哈"
                                    ,Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        loadImageFromInternt(); //顺便更新下每日必应图片

    }

    //将weather中的信息设置给各个控件
    private void showWeatherInfo(Weather weather0) {
        if (weather0!=null && "ok".equals(weather0.status)){
            String cityname=weather0.basic.cityName;
            /**
             *
             *"update": {
             "loc": "2017-08-14 08:53",
             "utc": "2017-08-14 00:53"
             }update的请求格式为上面的，splite(" "),用空格进行分割，【0】，代表2017-08-14，【1】代表 08：53
             *
             */

            String updatetime=weather0.basic.update.updateTime.split(" ")[0];
            String degtss = weather0.now.temperature + "°C";
            String weatherInfo = weather0.now.more.info;

            titleCity.setText(cityname);
            titleUpdateTime.setText(updatetime);
            degreeText.setText(degtss);
            weatherInfoText.setText(weatherInfo);
            forecastLayout.removeAllViews();
            for (Forecast forecast:weather0.forecastList){
                View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.forecast_item, forecastLayout, false);
                TextView datetext= view.findViewById(R.id.date_text);
                TextView infotext= view.findViewById(R.id.info_text);
                TextView mantext= view.findViewById(R.id.max_text);
                TextView mintext= view.findViewById(R.id.min_text);
                datetext.setText(forecast.date);
                infotext.setText(forecast.more.info);
                mantext.setText(forecast.temperature.max);
                mintext.setText(forecast.temperature.min);
                forecastLayout.addView(view);
            }
            if (weather0.aqi!=null){
                aqIText.setText(weather0.aqi.city.aqi);
                pm25Text.setText(weather0.aqi.city.pm25);
            }
            String comort="舒适度"+weather0.suggestion.comfort.info;
            String carWash="洗车指数"+weather0.suggestion.carWash.info;
            String sport="运动指数"+weather0.suggestion.sport.info;
            comfortText.setText(comort);
            carWashText.setText(carWash);
            sportText.setText(sport);
            weatherlayout.setVisibility(View.VISIBLE);
            Intent intent=new Intent(this, AutoUpdateService.class);
            startService(intent);
        }else {
            Toast.makeText(WeatherActivity.this,"获取天气信息失败"
                    ,Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImageFromInternt() {
        String address="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String url = response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",url);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(url).into(bingPicImage);

                    }
                });
            }
        });
    }
}
