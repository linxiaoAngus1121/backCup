package com.example.power;


import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    GestureDetector gestureDetector=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
     gestureDetector = new GestureDetector(this);
 
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把ontouchevent交给  GestureDetector 处理，而不是由activity处理
        return gestureDetector.onTouchEvent(event);
    }

    //按下
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Toast.makeText(this,"this is down",Toast.LENGTH_SHORT).show();
        return false;
    }

    //按下并未拖动和松开
    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Toast.makeText(this,"this is onShowPress",Toast.LENGTH_SHORT).show();
    }

    //轻击
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Toast.makeText(this,"this is onSingleTapUp",Toast.LENGTH_SHORT).show();
        return false;
    }
    //滑动
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Toast.makeText(this,"this is onScroll",Toast.LENGTH_SHORT).show();
        return false;
    }
    //长按
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Toast.makeText(this,"this is onLongPress",Toast.LENGTH_SHORT).show();
    }

    //拖动，v,v1代表拖动在x,y轴的速度
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Toast.makeText(this,"this is onFling",Toast.LENGTH_SHORT).show();
        return false;
    }
}
