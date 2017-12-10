package com.example.gestgure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    TextView textView;
    GestureOverlayView gestureOverlayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView=(TextView)findViewById(R.id.tv1);
        gestureOverlayView=(GestureOverlayView)findViewById(R.id.gestureforMain3);
        gestureOverlayView.setGestureColor(Color.RED);
        //设置手势绘制的宽度
        gestureOverlayView.setGestureStrokeWidth(4);
        //gesture完成时就会调用这个方法
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, final Gesture gesture) {
               //弹出提示框的布局文件R.layout.layout
                View savediago = getLayoutInflater().inflate(R.layout.layout, null);
                //获取主键中的image view和editeview
                ImageView gesturepit=(ImageView)savediago.findViewById(R.id.show);
                final EditText editText=(EditText)savediago.findViewById(R.id.edt);
                //将gesure转为bitmap
                Bitmap bitmap = gesture.toBitmap(128, 128, 10, 0xFFFF0000);
                gesturepit.setImageBitmap(bitmap);
                new AlertDialog.Builder(Main2Activity.this)
                                .setView(savediago)
                                .setTitle("请注意保存")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //存放到手势库中
                                        GestureLibrary libraries = GestureLibraries.fromFile("/sdcard/mygestures");
                                        libraries.addGesture(editText.getText().toString(),gesture);
                                        Toast.makeText(Main2Activity.this,"保存成功",Toast.LENGTH_LONG).show();
                                        libraries.save();

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Main2Activity.this,"取消",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .show();

            }
        });
    }
}
