package com.example.matchgamesample.game.counter;

import android.widget.TextView;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameObject;

public class FPSCounter extends GameObject {

    private final TextView mText;
    private long mTotalMillis;
    private int mDraws;

    private String mFpsText = "";

    public FPSCounter(GameEngine gameEngine) {
        mText = (TextView) gameEngine.mActivity.findViewById(R.id.txt_fps);
    }

    @Override
    public void startGame() {
        mTotalMillis = 0;
        mDraws = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        mTotalMillis += elapsedMillis;
        if (mTotalMillis > 1000) {
            long mFps = mDraws * 1000L / mTotalMillis;
            mFpsText = mFps + " fps";
            mTotalMillis = 0;
            mDraws = 0;
        }
    }

    @Override
    public void onDraw() {
        mText.setText(mFpsText);
        mDraws++;
    }

}
