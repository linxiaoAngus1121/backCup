package com.my.forward.customView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.my.forward.R;

/**
 * Created by 123456 on 2018/1/4.
 * 自定义的button
 */

public class ButtonM extends android.support.v7.widget.AppCompatButton {


    /**
     * Created by landptf on 2016/10/25.
     * 自定义Button，支持圆角矩形，圆形按钮等样式，可通过配置文件改变按下后的样式
     * 若通过代码设置圆角或者圆形，需要先调用setFillet方法将fillet设置为true
     */

    /**
     * 按钮的背景色
     */
    private int backColor = 0;
    /**
     * 按钮被按下时的背景色
     */
    private int backColorPress = 0;
    /**
     * 按钮的背景图片
     */
    private Drawable backGroundDrawable = null;
    /**
     * 按钮被按下时显示的背景图片
     */
    private Drawable backGroundDrawablePress = null;
    /**
     * 按钮文字的颜色
     */
    private ColorStateList textColor = null;
    /**
     * 按钮被按下时文字的颜色
     */
    private ColorStateList textColorPress = null;
    private GradientDrawable gradientDrawable = null;
    /**
     * 是否设置圆角或者圆形等样式
     */
    private boolean fillet = false;
    /**
     * 标示onTouch方法的返回值，用来解决onClick和onTouch冲突问题
     */
    private boolean isCost = true;

    public ButtonM(Context context) {
        super(context, null);
    }

    public ButtonM(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonM(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ButtonM, defStyle, 0);
        if (a != null) {
            //设置背景色
            ColorStateList colorList = a.getColorStateList(R.styleable.ButtonM_background_color);
            if (colorList != null) {
                backColor = colorList.getColorForState(getDrawableState(), 0);
                if (backColor != 0) {
                    setBackgroundColor(backColor);
                }
            }
            //记录按钮被按下时的背景色
            ColorStateList colorListPress = a.getColorStateList(R.styleable.ButtonM_backColorPress);
            if (colorListPress != null) {
                backColorPress = colorListPress.getColorForState(getDrawableState(), 0);
            }
            //设置背景图片，若backColor与backGroundDrawable同时存在，则backGroundDrawable将覆盖backColor
            backGroundDrawable = a.getDrawable(R.styleable.ButtonM_backGroundImage);
            if (backGroundDrawable != null) {
                setBackgroundDrawable(backGroundDrawable);
            }
            //记录按钮被按下时的背景图片
            backGroundDrawablePress = a.getDrawable(R.styleable.ButtonM_backGroundImagePress);
            //设置文字的颜色
            textColor = a.getColorStateList(R.styleable.ButtonM_textColor);
            if (textColor != null) {
                setTextColor(textColor);
            }
            //记录按钮被按下时文字的颜色
            textColorPress = a.getColorStateList(R.styleable.ButtonM_textColorPress);
            //设置圆角或圆形等样式的背景色
            fillet = a.getBoolean(R.styleable.ButtonM_fillet, false);
            if (fillet) {
                getGradientDrawable();
                if (backColor != 0) {
                    gradientDrawable.setColor(backColor);
                    setBackgroundDrawable(gradientDrawable);
                }
            }
            //设置圆角矩形的角度，fillet为true时才生效
            float radius = a.getFloat(R.styleable.ButtonM_radius, 0);
            if (fillet && radius != 0) {
                setRadius(radius);
            }
            //设置按钮形状，fillet为true时才生效
            int shape = a.getInteger(R.styleable.ButtonM_shape, 0);
            if (fillet && shape != 0) {
                setShape(shape);
            }
            a.recycle();
        }
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                //根据touch事件设置按下抬起的样式
                return setTouchStyle(event.getAction());
            }
        });
    }

    /**
     * 根据按下或者抬起来改变背景和文字样式
     *
     * @param state 状态
     * @return isCost
     * 为解决onTouch和onClick冲突的问题
     * 根据事件分发机制，如果onTouch返回true，则不响应onClick事件
     * 因此采用isCost标识位，当用户设置了onClickListener则onTouch返回false
     */
    private boolean setTouchStyle(int state) {
        if (state == MotionEvent.ACTION_DOWN) {     //按下的话，就要开始显示按下的文字，背景等信息
            //判断是否设置了按钮按下的背景颜色，设置了就不会为0
            if (backColorPress != 0) {
                if (fillet) {       //如果启用fillet
                    gradientDrawable.setColor(backColorPress);  //设置按钮被按下的背景色
                    setBackgroundDrawable(gradientDrawable);
                } else {
                    setBackgroundColor(backColorPress);
                }
            }
            if (backGroundDrawablePress != null) {
                setBackgroundDrawable(backGroundDrawablePress);
            }
            if (textColorPress != null) {
                setTextColor(textColorPress);
            }
        }
        if (state == MotionEvent.ACTION_UP) {   //抬起
            if (backColor != 0) {
                if (fillet) {
                    gradientDrawable.setColor(backColor);
                    setBackgroundDrawable(gradientDrawable);
                } else {
                    setBackgroundColor(backColor);
                }
            }
            if (backGroundDrawable != null) {
                setBackgroundDrawable(backGroundDrawable);
            }
            if (textColor != null) {
                setTextColor(textColor);
            }
        }
        return isCost;
    }

    /**
     * 重写setOnClickListener方法，解决onTouch和onClick冲突问题
     *
     * @param l 监听接口
     */
    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        isCost = false;
    }

    /**
     * 设置按钮的背景色
     *
     * @param backColor
     */
    public void setBackColor(int backColor) {
        this.backColor = backColor;
        if (fillet) {
            gradientDrawable.setColor(backColor);
            setBackgroundDrawable(gradientDrawable);
        } else {
            setBackgroundColor(backColor);
        }
    }

    /**
     * 设置按钮被按下时的背景色
     *
     * @param backColorPress
     */
    public void setBackColorPress(int backColorPress) {
        this.backColorPress = backColorPress;
    }

    /**
     * 设置按钮的背景图片
     *
     * @param backGroundDrawable
     */
    public void setBackGroundDrawable(Drawable backGroundDrawable) {
        this.backGroundDrawable = backGroundDrawable;
        setBackgroundDrawable(backGroundDrawable);
    }

    /**
     * 设置按钮被按下时的背景图片
     *
     * @param backGroundDrawablePress
     */
    public void setBackGroundDrawablePress(Drawable backGroundDrawablePress) {
        this.backGroundDrawablePress = backGroundDrawablePress;
    }

    /**
     * 设置文字的颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        if (textColor == 0) return;
        this.textColor = ColorStateList.valueOf(textColor);
        //此处应加super关键字，调用父类的setTextColor方法，否则会造成递归导致内存溢出
        super.setTextColor(this.textColor);
    }

    /**
     * 设置按钮被按下时文字的颜色
     *
     * @param textColorPress
     */
    public void setTextColorPress(int textColorPress) {
        if (textColorPress == 0) return;
        this.textColorPress = ColorStateList.valueOf(textColorPress);
    }

    /**
     * 设置按钮是否设置圆角或者圆形等样式
     *
     * @param fillet
     */
    public void setFillet(boolean fillet) {
        this.fillet = fillet;
        getGradientDrawable();
    }

    /**
     * 设置圆角按钮的角度
     *
     * @param radius
     */
    public void setRadius(float radius) {
        if (!fillet) return;
        getGradientDrawable();
        gradientDrawable.setCornerRadius(radius);
        setBackgroundDrawable(gradientDrawable);
    }

    /**
     * 设置按钮的形状
     *
     * @param shape
     */
    public void setShape(int shape) {
        if (!fillet) return;
        getGradientDrawable();
        gradientDrawable.setShape(shape);
        setBackgroundDrawable(gradientDrawable);
    }

    private void getGradientDrawable() {
        if (gradientDrawable == null) {
            gradientDrawable = new GradientDrawable();
        }
    }

}



