package com.example.matchgamesample.input;

import android.view.MotionEvent;
import android.view.View;

import com.example.matchgamesample.engine.InputController;

public class BasicInputController extends InputController
        implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {

            mX_Down = (int) event.getX();
            mY_Down = (int) event.getY();

        } else if (action == MotionEvent.ACTION_UP) {

            mX_Up = (int) event.getX();
            mY_Up = (int) event.getY();
        }
        return true;
    }
}
