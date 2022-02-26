package com.example.matchgamesample.engine;

import android.util.Log;

public class UpdateThread extends GameThread{

    public UpdateThread(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    protected void doIt(long elapsedMillis) {
        mGameEngine.onUpdate(elapsedMillis);
        Log.d("time", "" + elapsedMillis);
    }

}
