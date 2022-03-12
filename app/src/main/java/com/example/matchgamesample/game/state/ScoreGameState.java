package com.example.matchgamesample.game.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.Tile;

public class ScoreGameState extends GameState {
    private int mPoint;
    private final int mTarget;

    public ScoreGameState(GameEngine gameEngine) {
        super(gameEngine);
        mPoint = 0;
        mTarget = gameEngine.mLevel.mTarget.get(0);
    }

    @Override
    public boolean isPlayerReachTarget() {
        if (mPoint >= mTarget) {
            mPlayerWin = true;
        }
        return mPlayerWin;
    }

    @Override
    public void onUpdate(Tile[][] tileArray) {
        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow; i++) {
                if (tileArray[i][j].match > 0 && tileArray[i][j].isValidFruit()) {
                    mPoint += 10;
                }
            }
        }
    }


}
