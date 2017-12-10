package com.example.power;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by 123456 on 2017/7/30.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint= new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置画笔颜色
        paint.setColor(Color.BLUE);
        //设置画笔的笔触宽度
        paint.setStrokeWidth(3);
        //设置画笔的样式
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(40,40,60,paint);
        canvas.drawRect(10,80,70,140,paint);
        Path path= new Path();
        path.moveTo(70,350);
        path.lineTo(100,350);
        path.lineTo(80,300);

        path.close();
        canvas.drawPath(path,paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(90,200,150,230);
        canvas.drawRoundRect(rectF,15,15,paint);

        Shader mshader= new LinearGradient(0,0,40,60,new int[]{
                Color.RED,Color.BLACK,Color.BLUE,Color.GREEN,Color.YELLOW
        },null,Shader.TileMode.REPEAT);
        paint.setShader(mshader);

        //设置椭圆
        RectF f= new RectF(170,240,230,300);
        canvas.drawOval(f,paint);
       // canvas.drawPoint();
        paint.setTextSize(24);
        paint.setShader(null);
        canvas.drawText(getResources().getString(R.string.canvas),240,50,paint);
        canvas.drawText(getResources().getString(R.string.recentagle),240,120,paint);

    }
}
