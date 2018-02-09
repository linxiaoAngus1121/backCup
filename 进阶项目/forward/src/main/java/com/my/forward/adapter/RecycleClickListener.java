package com.my.forward.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 123456 on 2018/1/21.
 */

public class RecycleClickListener implements RecyclerView.OnItemTouchListener {
    private Context context;
    private GestureDetector detector;

    public interface ItemClickListener {
        void onItemClick(View v, int postion);
    }

    public RecycleClickListener(Context context, final RecyclerView view, final ItemClickListener
            listener) {
        this.context = context;
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View v = view.findChildViewUnder(e.getX(), e.getY());
                if (v != null && listener != null) {
                    listener.onItemClick(v, view.getChildLayoutPosition(v));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (detector.onTouchEvent(e)) {
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
