package com.example.gestgure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private ViewFlipper flipper;
    private GestureDetector detector;
    final  int min=50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flipper = (ViewFlipper) findViewById(R.id.viewfil);
        detector= new GestureDetector(this);
         flipper.addView(addimage(R.drawable.ic_launcher));
        flipper.addView(addimage(R.drawable.plane));
        //flipper.addView(addimage(R.drawable.cherry));
        //flipper.addView(addimage(R.drawable.grape));
    }

    /*
    * tip:当图片过大时，会抛出outofmenory错误,如果把  flipper.addView(addimage(R.drawable.ic_launcher))，改为apple,就会报这个错
    * */
    public View addimage(int resid) {
        ImageView imageView = new ImageView(this);
      imageView.setImageResource(resid);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
       //从右向左滑动
       if (e1.getX()-e2.getX()>min){
            flipper.showPrevious();
            return true;
            //从左向右
       }else if(e2.getX()-e1.getX()>min){
           flipper.showNext();
           return true;
        }
        return false;
    }
}
