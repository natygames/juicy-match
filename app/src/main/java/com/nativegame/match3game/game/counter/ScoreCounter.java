package com.nativegame.match3game.game.counter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.TextView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.GameEvent;
import com.nativegame.match3game.engine.GameObject;
import com.nativegame.match3game.level.Level;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ScoreCounter extends GameObject {

    private static final int POINTS_GAINED_PER_FRUIT = 10;

    private final TextView mText;
    private final Level mLevel;
    private int mPoints;
    private boolean mPointsHaveChanged;

    public ScoreCounter(GameEngine gameEngine) {
        mText = (TextView) gameEngine.mActivity.findViewById(R.id.score);
        mLevel = gameEngine.mLevel;
        mPointsHaveChanged = false;
    }

    @Override
    public void startGame() {
        mPoints = 0;
        mPointsHaveChanged = true;
    }

    @Override
    public void onUpdate(long elapsedMillis) {

    }

    @Override
    public void onDraw() {
        if (mPointsHaveChanged) {
            mText.setText(String.valueOf(mPoints));
            createTextAnim(mText);
            mPointsHaveChanged = false;
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        if (gameEvents == GameEvent.PLAYER_SCORE) {
            mPoints += POINTS_GAINED_PER_FRUIT;
            mLevel.mScore += POINTS_GAINED_PER_FRUIT;
            mPointsHaveChanged = true;
        }
    }

    private void createTextAnim(View view) {
        view.animate().cancel();
        view.animate().scaleX(1.5f).scaleY(1.5f).setDuration(100).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.animate().scaleX(1).scaleY(1).setDuration(100);
            }
        });
    }

}
