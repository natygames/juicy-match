package com.example.matchgamesample.game.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.game.Tile;

import java.util.ArrayList;

public class CollectGameState extends GameState {
    private final ArrayList<Integer> mTarget;

    public CollectGameState(GameEngine gameEngine) {
        super(gameEngine);
        mTarget = gameEngine.mLevel.mCollect;
        mPlayerWin = false;
    }

    @Override
    public boolean isPlayerWin() {
        int size = mGameEngine.mLevel.mTarget.size();
        for (int i = 0; i < size; i++) {
            if (mGameEngine.mLevel.mTarget.get(i) != 0) {
                return false;
            }
        }
        mPlayerWin = true;
        return true;
    }

    @Override
    public void onUpdate(Tile[][] tileArray) {
        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow; i++) {

                Tile tile = tileArray[i][j];
                if (tile.match == 0 || tile.layer != 0) {
                    continue;
                }

                int size = mTarget.size();
                for (int n = 0; n < size; n++) {
                    if (tile.kind == mTarget.get(n)) {
                        int target = mGameEngine.mLevel.mTarget.get(n);
                        if (target > 0) {
                            target--;
                            mGameEngine.mLevel.mTarget.set(n, target);
                            mGameEngine.onGameEvent(GameEvent.COLLECT);
                        }
                    }
                }
            }
        }

    }
}
