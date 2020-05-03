package com.example.dailymood2020.activities;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private IFlingHandler flingHandler;

    public MyGestureDetector(IFlingHandler flingHandler) {
        this.flingHandler = flingHandler;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getY() < e2.getY()) {
            flingHandler.handleFlingDown();
        }

        if (e1.getY() > e2.getY()) {
            flingHandler.handleFlingUp();
        }

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return super.onDown(e);
    }


}
