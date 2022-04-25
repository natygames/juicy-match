package com.example.matchgamesample.game.input;

import android.view.MotionEvent;
import android.view.View;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.InputController;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * InputController will handler player input,
 * including press position, release position,
 * and booster
 */

public class BasicInputController extends InputController {
    private final GameEngine mGameEngine;

    public BasicInputController(View view, GameEngine gameEngine) {
        mGameEngine = gameEngine;
        view.findViewById(R.id.fruit_board).setOnTouchListener(new BasicOnTouchListener());
    }

    private class BasicOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {

                mX_Down = (int) event.getX();
                mY_Down = (int) event.getY();
                // Log.d("input", "(" + mX_Down + ", " + mY_Down + ")");
                mGameEngine.onGameEvent(GameEvent.PLAYER_TOUCH);

            } else if (action == MotionEvent.ACTION_UP) {

                mX_Up = (int) event.getX();
                mY_Up = (int) event.getY();
                // Log.d("input", "(" + mX_Up + ", " + mY_Up + ")");
                mGameEngine.onGameEvent(GameEvent.PLAYER_RELEASE);

                // Check player input
                if (mUsingHammer) {
                    mGameEngine.onGameEvent(GameEvent.PLAYER_USE_HAMMER);
                } else if (mUsingGloves) {
                    if (Math.abs(mX_Down - mX_Up) > 50 || Math.abs(mY_Down - mY_Up) > 50) {
                        mGameEngine.onGameEvent(GameEvent.PLAYER_USE_GLOVES);
                    }
                } else if (mUsingBomb) {
                    mGameEngine.onGameEvent(GameEvent.PLAYER_USE_BOMB);
                } else {
                    if (Math.abs(mX_Down - mX_Up) > 50 || Math.abs(mY_Down - mY_Up) > 50) {
                        mGameEngine.onGameEvent(GameEvent.PLAYER_MOVE);
                    }
                }
            }

            return true;
        }
    }

}
