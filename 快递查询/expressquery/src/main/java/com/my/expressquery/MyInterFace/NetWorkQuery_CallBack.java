package com.my.expressquery.MyInterFace;

import com.my.expressquery.Json.Json_Network_Query.Net_Data;

import java.util.List;

/**
 * Created by 123456 on 2017/11/11.
 */

public interface NetWorkQuery_CallBack {
    void getList(List<Net_Data> strings);
}
