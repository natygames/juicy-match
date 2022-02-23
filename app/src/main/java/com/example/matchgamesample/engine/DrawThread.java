package com.example.matchgamesample.engine;

import android.os.Handler;

public class DrawThread extends GameThread{
    private final Handler mHandle = new Handler();

    public DrawThread(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    protected void doIt(long elapsedMillis) {
        if (elapsedMillis < 20) { // This is 50 fps
            try {
                Thread.sleep(20 - elapsedMillis);
            } catch (InterruptedException e) {
                // We just continue.
            }
        }

        mHandle.post(new Runnable() {
            @Override
            public void run() {
                mGameEngine.onDraw();
            }
        });
    }

}
