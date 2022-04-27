package com.example.matchgamesample.game.state;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.game.tile.Tile;

import java.util.ArrayList;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class CollectGameState extends BaseGameState {

    private final ArrayList<Integer> mTarget;

    public CollectGameState(GameEngine gameEngine) {
        super(gameEngine);
        mTarget = gameEngine.mLevel.mCollect;
    }

    @Override
    public boolean isPlayerReachTarget() {
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
                    // Check is match target
                    if (tile.kind == mTarget.get(n)) {
                        updateTarget(n);
                    } else if (mTarget.get(n) == R.drawable.striped_ball) {
                        if (tile.special && (tile.direct == 'H' || tile.direct == 'V'))
                            updateTarget(n);
                    }
                }
            }
        }

    }

    private void updateTarget(int index) {
        int target = mGameEngine.mLevel.mTarget.get(index);
        if (target > 0) {
            target--;
            mGameEngine.mLevel.mTarget.set(index, target);
            mGameEngine.onGameEvent(GameEvent.PLAYER_COLLECT);
        }
    }

}