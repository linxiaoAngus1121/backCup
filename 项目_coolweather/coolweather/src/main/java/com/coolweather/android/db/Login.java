package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 123456 on 2017/8/15.
 */

public class Login extends DataSupport {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
