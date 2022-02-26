package com.example.matchgamesample.engine;

import android.app.Activity;

import java.util.ArrayList;

public class GameEngine {
    private UpdateThread mUpdateThread;
    private DrawThread mDrawThread;
    private final ArrayList<GameObject> mGameObjects = new ArrayList<>();

    public InputController mInputController;
    public Activity mActivity;
    public int mImageSize;

    public GameEngine(Activity activity, int imageSize) {
        mActivity = activity;
        mImageSize = imageSize;
    }

    //--------------------------------------------------------
    // methods to change state of Game Engine
    // start, stop, pause, resume
    //--------------------------------------------------------

    public void startGame() {
        // Stop a game if it is running
        stopGame();
        // Setup the game objects
        int numGameObjects = mGameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            mGameObjects.get(i).startGame();
        }

        if (mInputController != null) {
            mInputController.onStart();
        }

        // Start the update thread
        mUpdateThread = new UpdateThread(this);
        mUpdateThread.start();
        // Start the drawing thread
        mDrawThread = new DrawThread(this);
        mDrawThread.start();
    }

    public void stopGame() {
        if (mUpdateThread != null) {
            mUpdateThread.stopGame();
        }
        if (mDrawThread != null) {
            mDrawThread.stopGame();
        }
        if (mInputController != null) {
            mInputController.onStop();
        }
    }

    public void pauseGame() {
        mUpdateThread.pauseGame();
        mDrawThread.pauseGame();
        if (mInputController != null) {
            mInputController.onPause();
        }
    }


    public void resumeGame() {
        mUpdateThread.resumeGame();
        mDrawThread.resumeGame();
        if (mInputController != null) {
            mInputController.onResume();
        }
    }

    public boolean isPaused() {
        return mUpdateThread != null && mUpdateThread.mIsGamePause;
    }

    public boolean isRunning() {
        return mUpdateThread != null && mUpdateThread.mIsGameRunning;
    }

    //=================================================================================

    public void onUpdate(long elapsedMillis) {
        int numGameObjects = mGameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            mGameObjects.get(i).onUpdate(elapsedMillis, this);
        }
    }

    public void onDraw() {
        int numGameObjects = mGameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            mGameObjects.get(i).onDraw();
        }
    }

    public void addGameObject(final GameObject gameObject) {
        mGameObjects.add(gameObject);
        mActivity.runOnUiThread(gameObject.mOnAddedRunnable);
    }

    public void removeGameObject(final GameObject gameObject) {
        mGameObjects.remove(gameObject);
        mActivity.runOnUiThread(gameObject.mOnRemovedRunnable);
    }

    public void setInputController(InputController inputController) {
        mInputController = inputController;
    }

    public void onGameEvent(GameEvent gameEvent) {
        // We notify all the GameObjects
        int numObjects = mGameObjects.size();
        for (int i = 0; i < numObjects; i++) {
            if (mGameObjects.get(i) instanceof GameEventListener)
                ((GameEventListener) mGameObjects.get(i)).onGameEvent(gameEvent);
        }

        // Play Sound
        //((MainActivity)mActivity).getSoundManager().playSoundForGameEvent(gameEvent);
    }
}
