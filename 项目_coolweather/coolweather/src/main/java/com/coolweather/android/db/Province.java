package com.coolweather.android.db;


import org.litepal.crud.DataSupport;

/**
 * Created by 123456 on 2017/8/6.
 */

public class Province extends DataSupport {
    private  int id;
    private  String provinceNane;  //省名
    private int provinceCode;       //省的代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceNane() {
        return provinceNane;
    }

    public void setProvinceNane(String provinceNane) {
        this.provinceNane = provinceNane;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
