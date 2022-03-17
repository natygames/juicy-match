package com.example.matchgamesample.game.counter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.TextView;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameObject;
import com.example.matchgamesample.level.Level;

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
