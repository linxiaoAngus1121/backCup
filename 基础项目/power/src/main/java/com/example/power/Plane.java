package com.example.power;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Message;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by 123456 on 2017/7/30.
 */

public class Plane extends View {
    final int backimage=300;
    private Bitmap backbitmap;
    private Bitmap planebitmap;

    final int WIDTH=320;
     final int HEIGHT=50;
    private int startY=backimage-HEIGHT;
    public Plane(Context context) {
        super(context);
        backbitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        planebitmap=BitmapFactory.decodeResource(getResources(),R.drawable.plane);
        final android.os.Handler handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
              if (msg.what==0x123){
                  if (startY<=0){
                      startY=backimage-HEIGHT;
                  }else {
                      startY=startY-3;
                  }
              }
                invalidate();
            }

        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
             handler.sendEmptyMessage(0x123);
            }
        },0,100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(backbitmap,0,startY,WIDTH,HEIGHT);
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawBitmap(planebitmap,160,380,null);
        super.onDraw(canvas);
    }
}
