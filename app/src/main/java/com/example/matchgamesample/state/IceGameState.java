package com.example.matchgamesample.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.game.Tile;

import java.util.ArrayList;

public class IceGameState extends GameState{
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
    public void onUpdate(Tile tile) {
        if(tile.match == 0 || tile.layer != 0){
            return;
        }

        // Update ice
        if(tile.ice == 1){
            int target = mGameEngine.mLevel.target.get(0);
            if(target > 0) {
                target--;
                mGameEngine.mLevel.target.set(0, target);
                mGameEngine.onGameEvent(GameEvent.COLLECT);
            }
        }

        // Update collect item
        int size = mTarget.size();
        for(int i = 1; i < size; i++){
            if(tile.kind == mTarget.get(i)){
                int target = mGameEngine.mLevel.target.get(i);
                if(target > 0) {
                    target--;
                    mGameEngine.mLevel.target.set(i, target);
                    mGameEngine.onGameEvent(GameEvent.COLLECT);
                }
            }
        }
    }

}
