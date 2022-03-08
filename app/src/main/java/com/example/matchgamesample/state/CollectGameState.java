package com.example.matchgamesample.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.game.Tile;

import java.util.ArrayList;

public class CollectGameState extends GameState{
    private final ArrayList<Integer> mTarget;

    public CollectGameState(GameEngine gameEngine){
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
        int size = mTarget.size();
        for(int i = 0; i < size; i++){
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
