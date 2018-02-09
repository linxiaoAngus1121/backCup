package com.my.forward.mvp.sourcequery.biz;

import com.my.forward.mvp.sourcequery.bean.Bean_l;

/**
 * Created by 123456 on 2018/2/9.
 * 一个登录的方法
 * 一个查询成绩的方法
 * 一个登录的准备方法，主要是拼接登录所需要的信息
 */

public interface ISourceQuery {

    void login(Bean_l bean, IOnLoginListener listener,
               IOnQuerySourceListener querySourceListener);

    void prepareLogin(IGetCodeListtener listener);
}
