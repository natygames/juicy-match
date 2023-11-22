package com.nativegame.juicymatch.game.counter;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.runnable.RunnableEntity;
import com.nativegame.natyengine.event.Event;
import com.nativegame.natyengine.event.EventListener;
import com.nativegame.natyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class MoveCounter extends RunnableEntity implements EventListener {

    private static final int EXTRA_MOVES = 3;

    private final TextView mTxtMove;
    private final Animation mPulseAnimation;

    private int mMoves;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public MoveCounter(GameActivity activity, Engine engine) {
        super(activity, engine);
        mTxtMove = activity.findViewById(R.id.txt_move);
        mPulseAnimation = AnimationUtils.loadAnimation(activity, R.anim.text_pulse);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mMoves = Level.LEVEL_DATA.getMove();
        setPostRunnable(true);
    }

    @Override
    protected void onUpdateRunnable() {
        mTxtMove.setText(String.valueOf(mMoves));
        mTxtMove.startAnimation(mPulseAnimation);
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case PLAYER_SWAP:
            case ADD_BONUS:
                if (mMoves > 0) {
                    mMoves--;
                    Level.LEVEL_DATA.setMove(mMoves);
                }
                setPostRunnable(true);
                break;
            case ADD_EXTRA_MOVES:
                mMoves += EXTRA_MOVES;
                Level.LEVEL_DATA.setMove(mMoves);
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
