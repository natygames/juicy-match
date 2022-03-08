package com.example.matchgamesample.engine;

import android.app.Activity;

import com.example.matchgamesample.level.Level;

import java.util.ArrayList;

public class GameEngine {
    private GameThread mGameThread;
    private final ArrayList<GameObject> mGameObjects = new ArrayList<>();

    public InputController mInputController;
    public Activity mActivity;
    public Level mLevel;
    public int mImageSize;

    public GameEngine(Activity activity, Level level, int imageSize) {
        mActivity = activity;
        mLevel = level;
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
        mGameThread = new GameThread(this);
        mGameThread.start();
    }

    public void stopGame() {
        if (mGameThread != null) {
            mGameThread.stopGame();
        }
        if (mInputController != null) {
            mInputController.onStop();
        }
    }

    public void pauseGame() {
        mGameThread.pauseGame();
        if (mInputController != null) {
            mInputController.onPause();
        }
    }

    public void resumeGame() {
        mGameThread.resumeGame();
        if (mInputController != null) {
            mInputController.onResume();
        }
    }

    public boolean isPaused() {
        return mGameThread != null && mGameThread.mIsGamePause;
    }

    public boolean isRunning() {
        return mGameThread != null && mGameThread.mIsGameRunning;
    }

    //=================================================================================

    public void onUpdate() {
        int numGameObjects = mGameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            mGameObjects.get(i).onUpdate();
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
             mGameObjects.get(i).onGameEvent(gameEvent);
        }

        // Play Sound
        //((MainActivity)mActivity).getSoundManager().playSoundForGameEvent(gameEvent);
    }
}
