package com.example.matchgamesample.game.counter;

import android.view.View;
import android.widget.TextView;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameObject;
import com.example.matchgamesample.level.Level;

public class MoveCounter extends GameObject {

    private final Level mLevel;
    private final TextView mText;
    private int mMoves;
    private boolean mMovesHaveChanged;

    public MoveCounter(View view, GameEngine gameEngine) {
        mText = (TextView) view.findViewById(R.id.move);
        mLevel = gameEngine.mLevel;
        mMoves = gameEngine.mLevel.mMove;
    }

    @Override
    public void startGame() {
        mText.setText(String.valueOf(mMoves));
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onDraw() {
        if (mMovesHaveChanged) {
            mText.setText(String.valueOf(mMoves));
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
}
