package com.example.matchgamesample.game.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.level.Level;

public class ScoreBaseGameState extends BaseGameState {
    private final Level mLevel;
    private final int mTarget;

    public ScoreBaseGameState(GameEngine gameEngine) {
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
