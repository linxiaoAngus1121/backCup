package com.my.expressquery.Json.Json_Network_Query;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 123456 on 2017/11/11.
 */

public class Net_Body {
    @SerializedName("siteList")
    private List<Net_Data> list;
    public List<Net_Data> getList() {
        return list;
    }

    public void setList(List<Net_Data> list) {
        this.list = list;
    }
}
