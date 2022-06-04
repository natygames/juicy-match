package com.nativegame.match3game.engine;

import android.app.Activity;

import com.nativegame.match3game.level.Level;

import java.util.ArrayList;

public class GameEngine {
    private GameThread mGameThread;
    private final ArrayList<GameObject> mGameObjects = new ArrayList<>();
    private final ArrayList<GameObject> mObjectsToAdd = new ArrayList<>();
    private final ArrayList<GameObject> mObjectsToRemove = new ArrayList<>();

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

    public void onUpdate(long elapsedMillis) {
        int numGameObjects = mGameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            mGameObjects.get(i).onUpdate(elapsedMillis);
        }

        synchronized (mGameObjects) {
            while (!mObjectsToRemove.isEmpty()) {
                mGameObjects.remove(mObjectsToRemove.remove(0));
            }
            while (!mObjectsToAdd.isEmpty()) {
                mGameObjects.add(mObjectsToAdd.remove(0));
            }
        }
    }

    public void onDraw() {
        synchronized (mGameObjects) {
            int numGameObjects = mGameObjects.size();
            for (int i = 0; i < numGameObjects; i++) {
                mGameObjects.get(i).onDraw();
            }
        }
    }

    public void addGameObject(final GameObject gameObject) {
        if (isRunning()) {
            mObjectsToAdd.add(gameObject);
        } else {
            mGameObjects.add(gameObject);
        }
    }

    public void removeGameObject(final GameObject gameObject) {
        mObjectsToRemove.add(gameObject);
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
    }

}
