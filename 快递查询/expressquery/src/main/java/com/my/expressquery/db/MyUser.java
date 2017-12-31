package com.my.expressquery.db;

import cn.bmob.v3.BmobUser;

/**
 * Created by 123456 on 2017/12/31.
 */

public class MyUser extends BmobUser {
    private String path;        //图片路径

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
