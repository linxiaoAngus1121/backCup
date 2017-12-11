package com.my.expressquery.baiduMap;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * Created by 123456 on 2017/11/8.
 * 传递数据要用
 */

public class Info implements Serializable {

    private String name;
    private String address;
    private LatLng latLng;
    private Boolean flag = false;

    public Info(String name, String address, LatLng latLng, Boolean flag) {
        this.address = address;
        this.name = name;
        this.latLng = latLng;
        this.flag = flag;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
