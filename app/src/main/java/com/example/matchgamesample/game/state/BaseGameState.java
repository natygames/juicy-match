package com.example.matchgamesample.game.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.tile.Tile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class BaseGameState {

    protected GameEngine mGameEngine;
    protected int mRow, mColumn;
    protected boolean mPlayerWin;
    protected boolean mPlayerLoss;

    public BaseGameState(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mRow = gameEngine.mLevel.mRow;
        mColumn = gameEngine.mLevel.mColumn;
        mPlayerWin = false;
        mPlayerLoss = false;
    }

    public boolean isPlayerReachTarget() {
        return mPlayerWin;
    }

    public boolean isPlayerOutOfMove() {
        if (mGameEngine.mLevel.mMove == 0 && !mPlayerWin) {
            mPlayerLoss = true;
        }
        return mPlayerLoss;
    }

    public void onUpdate(Tile[][] tileArray) {
    }

}
