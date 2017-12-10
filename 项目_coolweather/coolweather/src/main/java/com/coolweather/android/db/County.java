package com.coolweather.android.db;


import org.litepal.crud.DataSupport;

/**
 * Created by 123456 on 2017/8/6.
 */

public class County extends DataSupport
{
    private int id;
    private String countyName;
    private String weatherId; //对应天气的id
    private int cityId; //记录所属的城市的id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
