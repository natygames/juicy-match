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

    protected void doIt() {

        mHandle.post(new Runnable() {
            @Override
            public void run() {
                mGameEngine.onUpdate();
                mGameEngine.onDraw();
            }
        });

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // We stay on the loop
        }
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
        while (mIsGameRunning) {

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
            }

            doIt();
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
