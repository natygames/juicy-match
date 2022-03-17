package com.example.matchgamesample.engine;

import android.os.Handler;
import android.os.Looper;

public class GameThread extends Thread {
    protected final GameEngine mGameEngine;
    private final Object mLock = new Object();
    public volatile boolean mIsGameRunning;
    public volatile boolean mIsGamePause;
    private final Handler mHandle = new Handler(Looper.getMainLooper());

    public GameThread(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mIsGameRunning = false;
    }

    protected void doIt(long elapsedMillis) {

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // We just continue
        }

        mHandle.post(new Runnable() {
            @Override
            public void run() {
                mGameEngine.onUpdate(elapsedMillis);
                mGameEngine.onDraw();
            }
        });

    }

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
        long previousTimeMillis;
        long currentTimeMillis;
        long elapsedMillis;
        previousTimeMillis = System.currentTimeMillis();

        while (mIsGameRunning) {
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

    public void pauseGame() {
        mIsGamePause = true;
    }

    public void resumeGame() {
        if (mIsGamePause) {
            mIsGamePause = false;
            synchronized (mLock) {
                mLock.notify();
            }
        }
    }

}
