package com.my.forward.mvp.helpna.view;

import com.my.forward.mvp.helpna.bean.Information;

/**
 * Created by 123456 on 2018/1/31.
 * View层，让activity去实现这个接口，我们就能让activity与presenter进一步解耦
 */

public interface IUserCommitView {
    String getOdd();

    String getYourName();

    String getContact();

    String getQuery_address();

    String getAddress();

    Information getAll();

    void showLoading();

    void hideLoading();

    void showSuccess();

    void showFailedError();


}
