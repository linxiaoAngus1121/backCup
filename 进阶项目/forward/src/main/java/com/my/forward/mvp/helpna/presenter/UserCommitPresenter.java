package com.my.forward.mvp.helpna.presenter;

import android.os.Handler;

import com.my.forward.mvp.helpna.biz.IUserCommit;
import com.my.forward.mvp.helpna.biz.OnCommitListener;
import com.my.forward.mvp.helpna.biz.UserCommitBiz;
import com.my.forward.mvp.helpna.view.IUserCommitView;

/**
 * Created by 123456 on 2018/1/31.
 * Model层与view层交互的桥梁,在这里会处理服务器返回我们是否成功的注册信息。
 */

public class UserCommitPresenter {
    private IUserCommit commit;
    private IUserCommitView view;
    private Handler handler = new Handler();

    public UserCommitPresenter(IUserCommitView view) {
        this.view = view;               //把activity传进来
        commit = new UserCommitBiz();   //申明父类，创建子类
    }

    public void commit() {
        view.showLoading();
        commit.commit(view.getAll(), new OnCommitListener() {
            @Override
            public void commitSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.hideLoading();
                        view.showSuccess();
                    }
                });
            }

            @Override
            public void commitFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.hideLoading();
                        view.showFailedError();

                    }
                });
            }
        });
    }

}
