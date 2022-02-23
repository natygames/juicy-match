package com.example.matchgamesample.engine;

public class UpdateThread extends GameThread{

    public UpdateThread(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    protected void doIt(long elapsedMillis) {
        mGameEngine.onUpdate(elapsedMillis);
    }

}
