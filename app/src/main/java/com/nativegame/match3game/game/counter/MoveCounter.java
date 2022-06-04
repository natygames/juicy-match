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

public class MoveCounter extends GameObject {

    private final TextView mText;
    private final Level mLevel;
    private int mMoves;
    private boolean mMovesHaveChanged;

    public MoveCounter(GameEngine gameEngine) {
        mText = (TextView) gameEngine.mActivity.findViewById(R.id.move);
        mLevel = gameEngine.mLevel;
        mMoves = gameEngine.mLevel.mMove;
        mMovesHaveChanged = false;
    }

    @Override
    public void startGame() {
        mMovesHaveChanged = true;
    }

    @Override
    public void onUpdate(long elapsedMillis) {

    }

    @Override
    public void onDraw() {
        if (mMovesHaveChanged) {
            mText.setText(String.valueOf(mMoves));
            createTextAnim(mText);
            mMovesHaveChanged = false;
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        if (gameEvents == GameEvent.PLAYER_SWAP) {
            if (mMoves > 0) {
                mMoves--;
                mLevel.mMove--;
            }
            mMovesHaveChanged = true;
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
