package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123456 on 2017/8/7.
 */

public class Basic {
    //该注释的意思是在网络返回的json数组中对应的字段，即city
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
