package com.my.forward.mvp.sourcequery.biz;

import com.my.forward.mvp.sourcequery.bean.Bean_l;

import java.io.InputStream;

/**
 * Created by 123456 on 2018/2/9.
 */

public interface IGetCodeListtener {
    void getCodeSuccess(InputStream inputStream, Bean_l beanL);

    void getCodeFailure(String s);

    void getViewStateError(String s);
}
