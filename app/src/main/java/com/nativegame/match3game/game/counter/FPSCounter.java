package com.nativegame.match3game.game.counter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.GameObject;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class FPSCounter extends GameObject {

    private static final String PREFS_NAME = "prefs_setting";
    private static final String FPS_PREF_KEY = "fps";

    private final TextView mText;
    private long mTotalMillis;
    private int mDraws;

    private String mFpsText = "";

    public FPSCounter(GameEngine gameEngine) {
        mText = (TextView) gameEngine.mActivity.findViewById(R.id.txt_fps);

        // Check if the fps is on
        boolean fpsEnable = gameEngine.mActivity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(FPS_PREF_KEY, true);
        if (!fpsEnable) {
            mText.setVisibility(View.INVISIBLE);
            gameEngine.removeGameObject(this);
        }
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
