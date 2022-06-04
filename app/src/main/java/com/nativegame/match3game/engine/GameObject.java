package com.nativegame.match3game.engine;

public abstract class GameObject {

    public abstract void startGame();

    public abstract void onUpdate(long elapsedMillis);

    public abstract void onDraw();

    public void onGameEvent(GameEvent gameEvent) {

    }

}
