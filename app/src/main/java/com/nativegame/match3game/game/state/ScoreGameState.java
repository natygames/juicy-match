package com.nativegame.match3game.game.state;

import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.level.Level;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ScoreGameState extends BaseGameState {

    private final Level mLevel;
    private final int mTarget;

    public ScoreGameState(GameEngine gameEngine) {
        super(gameEngine);
        mLevel = gameEngine.mLevel;
        mTarget = gameEngine.mLevel.mTarget.get(0);
    }

    @Override
    public boolean isPlayerReachTarget() {
        if (mLevel.mScore >= mTarget) {
            mPlayerWin = true;
        }
        return mPlayerWin;
    }

}
