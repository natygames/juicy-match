package com.example.matchgamesample.game.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.Tile;

public class GameState {
    protected GameEngine mGameEngine;
    protected int mRow, mColumn;
    protected boolean mPlayerWin;
    protected boolean mPlayerLoss;

    public GameState(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mRow = gameEngine.mLevel.mRow;
        mColumn = gameEngine.mLevel.mColumn;
        mPlayerWin = false;
        mPlayerLoss = false;
    }

    public boolean isPlayerWin() {
        return mPlayerWin;
    }

    public boolean isPlayerLoss() {
        if (mGameEngine.mLevel.mMove == 0 && !mPlayerWin) {
            mPlayerLoss = true;
        }
        return mPlayerLoss;
    }

    public void onUpdate(Tile[][] tileArray) {
    }

}
