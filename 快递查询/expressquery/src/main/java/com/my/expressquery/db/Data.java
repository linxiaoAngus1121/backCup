package com.my.expressquery.db;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by 123456 on 2017/10/24.
 */

public class Data extends BmobObject {
    private String odd; //快递单号
    private BmobUser user;      //主外键关联
    private String name;

    private String code;    //快递代号

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOdd() {
        return odd;
    }

    @Override
    public String toString() {
        return "Data{" +
                "odd='" + odd + '\'' +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
    }
}
