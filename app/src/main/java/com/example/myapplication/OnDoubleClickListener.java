package com.example.myapplication;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class OnDoubleClickListener implements View.OnTouchListener {
    private final String tag = this.getClass().getSimpleName();
    private int count = 0;
    private long firstClick = 0;
    private long secondClick = 0;
    private DoubleClickCallback mCallback;

    public interface DoubleClickCallback {
        void onDoubleClick();
    }

    OnDoubleClickListener(DoubleClickCallback callback) {
        super();
        this.mCallback = callback;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            count++;
            if (1 == count) {
                firstClick = System.currentTimeMillis();
            } else if (2 == count) {
                secondClick = System.currentTimeMillis();
                /**
                 * 两次点击时间间隔，单位毫秒
                 */
                int interval = 1000;
                if (secondClick - firstClick < interval) {
                    if (mCallback != null) {
                        mCallback.onDoubleClick();
                    } else {
                        Log.e(tag, "在构造方法中传入一个双击回调");
                    }
                    count = 0;
                    firstClick = 0;
                } else {
                    firstClick = secondClick;
                    count = 1;
                }
                secondClick = 0;
            }
        }
        return true;
    }
}

