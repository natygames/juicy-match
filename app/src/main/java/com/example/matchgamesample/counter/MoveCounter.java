package com.example.matchgamesample.counter;

import android.view.View;
import android.widget.TextView;

import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameEventListener;
import com.example.matchgamesample.engine.GameObject;

public class MoveCounter extends GameObject implements GameEventListener {

    private final TextView mText;
    private int mMoves;
    private boolean mMovesHaveChanged;

    public MoveCounter(View view, int viewResId, int move) {
        mText = (TextView) view.findViewById(viewResId);
        mMoves = move;
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
        if (gameEvents == GameEvent.VALID_SWAP) {
            if (mMoves > 0)
                mMoves--;
            mMovesHaveChanged = true;
        }
    }
}
