package com.my.expressquery.Json.Json_Network_Query;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * Created by 123456 on 2017/11/11.
 */

public class Net_Data implements Serializable {
    private String expName;

    private String contactInfo;

    private String sendInfo;

    private String addr;

    private String siteName;

    private LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpName() {
        return this.expName;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() {
        return this.contactInfo;
    }

    public void setSendInfo(String sendInfo) {
        this.sendInfo = sendInfo;
    }

    public String getSendInfo() {
        return this.sendInfo;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteName() {
        return this.siteName;
    }
}
