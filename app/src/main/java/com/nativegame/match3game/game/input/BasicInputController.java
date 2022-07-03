package com.nativegame.match3game.game.input;

import android.view.MotionEvent;
import android.view.View;

import com.nativegame.match3game.R;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.GameEvent;
import com.nativegame.match3game.engine.InputController;

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

                mXDown = (int) event.getX();
                mYDown = (int) event.getY();
                // Log.d("input", "(" + mXDown + ", " + mYDown + ")");
                mGameEngine.onGameEvent(GameEvent.PLAYER_TOUCH);

            } else if (action == MotionEvent.ACTION_UP) {

                mXUp = (int) event.getX();
                mYUp = (int) event.getY();
                // Log.d("input", "(" + mXUp + ", " + mYUp + ")");
                mGameEngine.onGameEvent(GameEvent.PLAYER_RELEASE);

                // Check player input
                if (mUsingHammer) {
                    mGameEngine.onGameEvent(GameEvent.PLAYER_USE_HAMMER);
                } else if (mUsingGloves) {
                    if (Math.abs(mXDown - mXUp) > 50 || Math.abs(mYDown - mYUp) > 50) {
                        mGameEngine.onGameEvent(GameEvent.PLAYER_USE_GLOVES);
                    }
                } else if (mUsingBomb) {
                    mGameEngine.onGameEvent(GameEvent.PLAYER_USE_BOMB);
                } else {
                    if (Math.abs(mXDown - mXUp) > 50 || Math.abs(mYDown - mYUp) > 50) {
                        mGameEngine.onGameEvent(GameEvent.PLAYER_MOVE);
                    }
                }
            }

            return true;
        }
    }

}
