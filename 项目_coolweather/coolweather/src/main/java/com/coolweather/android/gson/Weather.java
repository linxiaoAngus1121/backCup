package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 123456 on 2017/8/7.
 */

public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    //因为Forecast里面是数组，所以用一个list来引用
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
