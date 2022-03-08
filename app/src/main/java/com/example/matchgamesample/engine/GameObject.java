package com.example.matchgamesample.engine;

public abstract class GameObject {
    public abstract void startGame();
    public abstract void onUpdate();
    public abstract void onDraw();

    public final Runnable mOnAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };

    public final Runnable mOnRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };

    public void onRemovedFromGameUiThread(){}
    public void onAddedToGameUiThread() {}

    public void onGameEvent(GameEvent gameEvent) {

    }
}
