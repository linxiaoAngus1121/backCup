package com.my.forward.mvp.sourcequery.view;

import java.io.InputStream;

/**
 * Created by 123456 on 2018/2/9.
 */

public interface ISourceView {
    String getstudNo();   //获取学生学号

    String getstuPs();    //获取学生密码

    String getCode();       //获取输入的验证码

    void showSource();  //展示学生成绩

    void showSourceError(String s);   //展示错误

    void showCode(InputStream inputStream);       //显示验证码

    void showCodeError(String s);

    void showViewStateError(String s);      //获取viewstate失败

    void showLoginSuccess();

    void showLoginError();
}
