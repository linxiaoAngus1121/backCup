package com.example.gestgure;

import android.app.AlertDialog;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity{

    private GestureLibrary librariy;
    private GestureOverlayView gestureOverlayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        gestureOverlayView=(GestureOverlayView)findViewById(R.id.gestureforMain3);
        librariy=GestureLibraries.fromFile("/sdcard/mygestures");
        if (librariy.load()){
            Toast.makeText(Main3Activity.this,"load success",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Main3Activity.this,"load fail",Toast.LENGTH_SHORT).show();
        }

        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
              //返回一个list，里面存放着相似度score，还有手势名称name
                ArrayList<Prediction> recognize = librariy.recognize(gesture);
                //用一个list存放匹配上的手势
                List<String> result = new ArrayList<String>() ;
                for (Prediction pref:recognize){
                    if (pref.score>2.0){
                        result.add("与手势【"+pref.name+"】的相似度为"+pref.score);
                    }
                }
                if (result.size()>0){
                    ArrayAdapter adapter=new ArrayAdapter(Main3Activity.this,android.R.layout.simple_dropdown_item_1line, result.toArray());
                    new AlertDialog.Builder(Main3Activity.this)
                                    .setAdapter(adapter,null)
                                    .setTitle("显示匹配的手势")
                                    .setPositiveButton("保存",null)
                                     .show();
                }else {
                    Toast.makeText(Main3Activity.this,"没有找到相似度为2的手势",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }








    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureOverlayView.onTouchEvent(event);
    }
}
