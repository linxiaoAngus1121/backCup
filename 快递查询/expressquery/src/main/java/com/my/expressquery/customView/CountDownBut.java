package com.my.expressquery.customView;

import android.os.CountDownTimer;
import android.widget.Button;

import com.my.expressquery.R;

/**
 * Created by 123456 on 2018/1/19.
 * 验证码倒计时
 */

public class CountDownBut extends CountDownTimer {
    private Button but;

    /**
     * 使用方法 ： CountDownBut countTimer =  new CountDownBut(10000, 1000); 倒计时十秒，1秒读一次数
     * countTimer.start()
     *
     * @param millisInFuture    倒计时的总时间，如60秒。The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and
     *                          {@link #onFinish()}
     *                          is called.
     * @param countDownInterval 倒计时的间隔，如1秒。The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownBut(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.but = button;
    }

    //倒计时中调用
    @Override
    public void onTick(long millisUntilFinished) {
        int time = (int) (Math.round((double) millisUntilFinished / 1000) - 1);
        but.setText(String.format("%ss", String.valueOf(time)));
        but.setClickable(false);//倒计时过程中将按钮设置为不可点击
        but.setBackgroundResource(R.drawable.get_code_style_clicked);
        but.setTextSize(16);
    }

    //倒计时完成调用
    @Override
    public void onFinish() {
        but.setText("验证码");
        but.setBackgroundResource(R.drawable.get_code_style);
        but.setClickable(true);
    }
}
