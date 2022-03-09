package com.example.matchgamesample.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.game.Tile;

import java.util.ArrayList;

public class IceGameState extends GameState {
    private final ArrayList<Integer> mTarget;

    public IceGameState(GameEngine gameEngine) {
        super(gameEngine);
        mTarget = gameEngine.mLevel.collect;
    }

    @Override
    public boolean isPlayerWin() {
        return false;
    }

    @Override
    public boolean isPlayerLoss() {
        return false;
    }

    @Override
    public void onUpdate(Tile[][] tileArray) {
        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow; i++) {

                Tile tile = tileArray[i][j];
                if (tile.match == 0 || tile.layer != 0) {
                    continue;
                }

                // Update ice
                if (tile.ice == 1) {
                    int target = mGameEngine.mLevel.target.get(0);
                    if (target > 0) {
                        target--;
                        mGameEngine.mLevel.target.set(0, target);
                        mGameEngine.onGameEvent(GameEvent.COLLECT);
                    }
                }

                // Update collect item
                int size = mTarget.size();
                for (int n = 1; n < size; n++) {
                    if (tile.kind == mTarget.get(n)) {
                        int target = mGameEngine.mLevel.target.get(n);
                        if (target > 0) {
                            target--;
                            mGameEngine.mLevel.target.set(n, target);
                            mGameEngine.onGameEvent(GameEvent.COLLECT);
                        }
                    }
                }
            }
        }
    }

}
