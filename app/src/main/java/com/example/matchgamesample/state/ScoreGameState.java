package com.example.matchgamesample.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.Tile;

public class ScoreGameState extends GameState {
    private int mPoint;
    private final int mTarget;

    public ScoreGameState(GameEngine gameEngine) {
        super(gameEngine);
        mPoint = 0;
        mTarget = gameEngine.mLevel.target.get(0);
    }

    @Override
    public boolean isPlayerWin() {
        if (mPoint >= mTarget) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isPlayerLoss() {
        if (mGameEngine.mLevel.move == 0 && mPoint < mTarget) {
            return true;
        }
        return false;
    }

    @Override
    public void onUpdate(Tile tile) {
        if (tile.match > 0) {
            mPoint += 10;
        }
    }


}
