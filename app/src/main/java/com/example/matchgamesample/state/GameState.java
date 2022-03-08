package com.example.matchgamesample.state;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.Tile;

public class GameState {
    protected GameEngine mGameEngine;

    public GameState(GameEngine gameEngine){
        mGameEngine = gameEngine;
    }

    public boolean isPlayerWin() {
        return false;
    }

    public boolean isPlayerLoss() {
        return false;
    }

    public void onUpdate(Tile tile) {
    }

}
