package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 123456 on 2017/8/7.
 */

public class Utility {
    /*
    * 处理服务器返回的省级数据
    *
    * */

    public static  boolean handleProvinceResponse(String respone){
        if (!TextUtils.isEmpty(respone)){
            try {
                Log.i("000",respone);
                JSONArray allProvice=new JSONArray(respone);
                for (int i =0;i<allProvice.length();i++){
                    JSONObject jsonObject = allProvice.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceNane(jsonObject.getString("name"));
                    province.setProvinceCode(jsonObject.getInt("id"));
                    province.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

/*
* c处理服务器返回的市级数据
* */
    public  static boolean handleCityResponese(String respones,int provinceid){
        if (!TextUtils.isEmpty(respones)){
            try {
                JSONArray allProvice=new JSONArray(respones);
                for (int i =0;i<allProvice.length();i++){
                    JSONObject jsonObject = allProvice.getJSONObject(i);
                    City city=new City();
                    city.setCityName(jsonObject.getString("name"));
                    city.setCityCode(jsonObject.getInt("id"));
                    city.setProvinceId(provinceid);
                    city.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    * 处理县级的天气信息
    * */

    public  static  boolean handleCountyResponse(String response,int cityid){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvice=new JSONArray(response);
                for (int i =0;i<allProvice.length();i++){
                    JSONObject jsonObject = allProvice.getJSONObject(i);
                    County county=new County();
                    county.setCityId(cityid);
                    county.setCountyName(jsonObject.getString("name"));
                    county.setWeatherId(jsonObject.getString("weather_id"));
                    county.setCityId(cityid);
                    county.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

/**
 * 将服务器返回的json转化成weather对象
 *
 */

    public static Weather handleWeatherResponse(String response){

        try {
            JSONObject   object = new JSONObject(response);
            JSONArray jsonArray = object.getJSONArray("HeWeather");
            String string = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(string,Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
