package com.nativegame.match3game.game.counter;

import android.widget.TextView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.engine.event.Event;
import com.nativegame.nattyengine.engine.event.EventListener;
import com.nativegame.nattyengine.entity.runnable.RunnableEntity;
import com.nativegame.nattyengine.ui.GameActivity;

public class MoveCounter extends RunnableEntity implements EventListener {

    private final TextView mText;

    private int mMoves;

    public MoveCounter(GameActivity activity, Engine engine) {
        super(activity, engine);
        mText = activity.findViewById(R.id.txt_move);
    }

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
        mText.setText(String.valueOf(mMoves));
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
            case GAME_OVER:
            case BONUS_TIME_END:
                removeFromGame();
                break;
        }
    }
    //========================================================

}
