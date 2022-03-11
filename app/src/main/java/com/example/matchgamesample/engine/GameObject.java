package com.example.matchgamesample.engine;

public abstract class GameObject {
    public abstract void startGame();

    public abstract void onUpdate();

    public abstract void onDraw();

    public void onGameEvent(GameEvent gameEvent) {

    }
}
