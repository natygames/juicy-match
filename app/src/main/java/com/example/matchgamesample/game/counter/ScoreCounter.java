package com.example.matchgamesample.game.counter;

import android.view.View;
import android.widget.TextView;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameObject;

public class ScoreCounter extends GameObject{
    private static final int POINTS_GAINED_PER_FRUIT = 10;

    private final TextView mText;
    private int mPoints;
    private boolean mPointsHaveChanged;

    public ScoreCounter(View view){
        mText = (TextView) view.findViewById(R.id.score);
    }

    @Override
    public void startGame() {
        mPoints = 0;
        mText.setText(String.valueOf(mPoints));
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onDraw() {
        if (mPointsHaveChanged) {
            mText.setText(String.valueOf(mPoints));
            mPointsHaveChanged = false;
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        if (gameEvents == GameEvent.SCORE) {
            mPoints += POINTS_GAINED_PER_FRUIT;
            mPointsHaveChanged = true;
        }
    }
}
