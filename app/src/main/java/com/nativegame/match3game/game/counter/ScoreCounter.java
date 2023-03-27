package com.nativegame.match3game.game.counter;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.engine.event.Event;
import com.nativegame.nattyengine.engine.event.EventListener;
import com.nativegame.nattyengine.entity.runnable.RunnableEntity;
import com.nativegame.nattyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ScoreCounter extends RunnableEntity implements EventListener {

    private static final int POINTS_PER_FRUIT = 10;

    private final TextView mText;
    private final Animation mTextAnimation;

    private int mPoints;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ScoreCounter(GameActivity activity, Engine engine) {
        super(activity, engine);
        mText = activity.findViewById(R.id.txt_score);
        mTextAnimation = AnimationUtils.loadAnimation(activity, R.anim.text_pulse);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mPoints = 0;
        setPostRunnable(true);
    }

    @Override
    protected void onUpdateRunnable() {
        mText.setText(String.valueOf(mPoints));
        mText.startAnimation(mTextAnimation);
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case PLAYER_SCORE:
            case ADD_BONUS:
                mPoints += POINTS_PER_FRUIT;
                Level.LEVEL_DATA.setScore(mPoints);
                setPostRunnable(true);
                break;
            case GAME_OVER:
            case BONUS_TIME_END:
                removeFromGame();
                break;
        }
    }
    //========================================================

}
