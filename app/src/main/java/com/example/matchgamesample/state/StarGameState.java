package com.example.matchgamesample.state;

import android.util.Log;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.game.Tile;
import com.example.matchgamesample.game.TileID;

import java.util.ArrayList;

public class StarGameState extends GameState{
    private final ArrayList<Integer> mTarget;

    public StarGameState(GameEngine gameEngine){
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
        // Update starfish
        if(tile.kind == TileID.STAR_FISH && tile.entryPoint){
            tile.match++;
            int target = mGameEngine.mLevel.target.get(0);
            if(target > 0) {
                target--;
                mGameEngine.mLevel.target.set(0, target);
                mGameEngine.onGameEvent(GameEvent.COLLECT);
            }
            return;
        }

        if(tile.match == 0 || tile.layer != 0){
            return;
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
