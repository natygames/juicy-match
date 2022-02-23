package com.example.matchgamesample.engine;

public abstract class GameThread extends Thread{
    protected final GameEngine mGameEngine;
    private final Object mLock = new Object();
    public volatile boolean mIsGameRunning;
    public volatile boolean mIsGamePause;

    public GameThread(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mIsGameRunning = false;
    }

    protected abstract void doIt(long elapsedMillis);

    @Override
    public void start() {
        mIsGameRunning = true;
        mIsGamePause = false;
        super.start();
    }

    public void stopGame() {
        mIsGameRunning = false;
        resumeGame();
    }

    @Override
    public void run() {
        long elapsedMillis;
        long previousTimeMillis;
        long currentTimeMillis;
        previousTimeMillis = System.currentTimeMillis();
        while(mIsGameRunning){
            currentTimeMillis = System.currentTimeMillis();
            elapsedMillis = currentTimeMillis - previousTimeMillis;

            if (mIsGamePause) {
                while (mIsGamePause) {
                    try {
                        synchronized (mLock) {
                            mLock.wait();
                        }
                    } catch (InterruptedException e) {
                        // We stay on the loop
                    }
                }
                currentTimeMillis = System.currentTimeMillis();
            }

            doIt(elapsedMillis);

            previousTimeMillis = currentTimeMillis;
        }

    }

    public void resumeGame() {
        if (mIsGamePause) {
            mIsGamePause = false;
            synchronized (mLock) {
                mLock.notify();
            }
        }
    }

    public void pauseGame() {
        mIsGamePause = true;
    }
}
