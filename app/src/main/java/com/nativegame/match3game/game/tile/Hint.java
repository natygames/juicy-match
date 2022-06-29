package com.nativegame.match3game.game.tile;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.GameEvent;
import com.nativegame.match3game.engine.GameObject;

import java.util.ArrayList;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * Hint check possible match in game.
 * If we find any, start hint animation
 * otherwise, refresh the game
 **/

public class Hint extends GameObject {

    private static final String PREFS_NAME = "prefs_setting";
    private static final String HINT_PREF_KEY = "hint";
    private static final int DELAY_TIME = 4000;

    private final GameEngine mGameEngine;
    private final int mColumn, mRow;
    private final Tile[][] mTileArray;
    private final Animation animation;
    private final ArrayList<ImageView> hintArray = new ArrayList<>();

    private final boolean mHintEnable;
    private boolean mShowHint;
    private int mDelayTime;

    public Hint(GameEngine gameEngine, Tile[][] tileArray) {
        mGameEngine = gameEngine;
        mColumn = gameEngine.mLevel.mColumn;
        mRow = gameEngine.mLevel.mRow;
        mTileArray = tileArray;
        animation = AnimationUtils.loadAnimation(gameEngine.mActivity, R.anim.hint_twinkle);

        // Check if the hint is on
        mHintEnable = gameEngine.mActivity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(HINT_PREF_KEY, true);
        mShowHint = false;
    }

    @Override
    public void startGame() {
        mDelayTime = 0;
        mShowHint = true;
    }

    @Override
    public void onUpdate(long elapsedMillis) {

        if (mShowHint) {
            mDelayTime += elapsedMillis;
            if (mDelayTime > DELAY_TIME) {
                startHint();
                // Reset
                mDelayTime = 0;
                mShowHint = false;
            }
        }
    }

    @Override
    public void onDraw() {
        // This game object does not draw anything
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        switch (gameEvent) {
            case START_HINT:
                // Stop the previous hint
                stopHint();

                boolean isFindMatch = checkPossibleMatch();
                if (!isFindMatch) {
                    mGameEngine.onGameEvent(GameEvent.REFRESH);
                } else {
                    mShowHint = true;
                }

                break;
            case PLAYER_SWAP:
            case PLAYER_PRESS_HAMMER:
            case PLAYER_PRESS_GLOVES:
            case PLAYER_PRESS_BOMB:
                stopHint();
                break;
            case PLAYER_REACH_TARGET:
            case PLAYER_OUT_OF_MOVE:
                stopHint();
                mGameEngine.removeGameObject(this);
                break;
        }
    }

    private void startHint() {
        if (mHintEnable) {
            // Start hint animation
            int size = hintArray.size();
            for (int i = 0; i < size; i++) {
                hintArray.get(i).startAnimation(animation);
            }
        }
    }

    private void stopHint() {
        mShowHint = false;
        mDelayTime = 0;

        hintArray.clear();
        animation.cancel();
        animation.reset();
    }

    private boolean checkPossibleMatch() {
        /* Hint priority
         * 1. Special combine
         * 2. match in 5
         * 3. match in 4 T L
         * 4. match in 3
         * 5. ice cream
         */

        // Check special combine
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 1; j++) {

                // Find two special fruit in row and column
                if (!mTileArray[i][j].invalid && !mTileArray[i][j + 1].invalid
                        && mTileArray[i][j].special && mTileArray[i][j + 1].special) {

                    hintArray.add(mTileArray[i][j].mImage);
                    hintArray.add(mTileArray[i][j + 1].mImage);
                    return true;
                }
            }
        }

        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow - 1; i++) {

                // Find two special fruit in row and column
                if (!mTileArray[i][j].invalid && !mTileArray[i + 1][j].invalid
                        && mTileArray[i][j].special && mTileArray[i + 1][j].special) {

                    // Check honey
                    hintArray.add(mTileArray[i][j].mImage);
                    hintArray.add(mTileArray[i + 1][j].mImage);
                    return true;
                }
            }
        }

        // Check match 5 in column
        for (int i = 0; i < mRow - 4; i++) {
            for (int j = 0; j < mColumn; j++) {
                // Check tile state
                if (!mTileArray[i][j].empty && mTileArray[i][j].isFruit()) {

                    int kind = mTileArray[i][j].kind;
                    if (kind == mTileArray[i + 1][j].kind
                            && kind == mTileArray[i + 3][j].kind
                            && kind == mTileArray[i + 4][j].kind) {

                        // Check potential match
                        if (j > 0 && kind == mTileArray[i + 2][j - 1].kind) {
                            // Check is swappable
                            if (mTileArray[i + 2][j - 1].isMovable()
                                    && mTileArray[i + 2][j].isMovable()) {
                                // O
                                // O
                                //O
                                // O
                                // O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i + 2][j - 1].mImage);
                                hintArray.add(mTileArray[i + 3][j].mImage);
                                hintArray.add(mTileArray[i + 4][j].mImage);
                                return true;
                            }
                        } else if (j < mColumn - 1 && kind == mTileArray[i + 2][j + 1].kind) {
                            // Check is swappable
                            if (mTileArray[i + 2][j + 1].isMovable()
                                    && mTileArray[i + 2][j].isMovable()) {
                                //O
                                //O
                                // O
                                //O
                                //O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i + 2][j + 1].mImage);
                                hintArray.add(mTileArray[i + 3][j].mImage);
                                hintArray.add(mTileArray[i + 4][j].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // Check match 5 in row
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 4; j++) {
                // Check tile state
                if (!mTileArray[i][j].empty && mTileArray[i][j].isFruit()) {

                    int kind = mTileArray[i][j].kind;
                    if (kind == mTileArray[i][j + 1].kind
                            && kind == mTileArray[i][j + 3].kind
                            && kind == mTileArray[i][j + 4].kind) {

                        // Check potential match
                        if (i > 0 && kind == mTileArray[i - 1][j + 2].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j + 2].isMovable()
                                    && mTileArray[i][j + 2].isMovable()) {
                                //  O
                                //OO OO
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i - 1][j + 2].mImage);
                                hintArray.add(mTileArray[i][j + 3].mImage);
                                hintArray.add(mTileArray[i][j + 4].mImage);
                                return true;
                            }
                        } else if (i < mRow - 1 && kind == mTileArray[i + 1][j + 2].kind) {
                            // Check is swappable
                            if (mTileArray[i + 1][j + 2].isMovable()
                                    && mTileArray[i][j + 2].isMovable()) {
                                //OO OO
                                //  O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i + 1][j + 2].mImage);
                                hintArray.add(mTileArray[i][j + 3].mImage);
                                hintArray.add(mTileArray[i][j + 4].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // Check match 4 T L in column
        for (int i = 0; i < mRow - 1; i++) {
            for (int j = 0; j < mColumn; j++) {

                // Check tile state
                if (!mTileArray[i][j].empty && mTileArray[i][j].isFruit()) {

                    int kind = mTileArray[i][j].kind;
                    // Check next 1 row
                    if (kind == mTileArray[i + 1][j].kind) {

                        // Check potential match
                        if (i < mRow - 3 && j > 0
                                && kind == mTileArray[i + 2][j - 1].kind
                                && kind == mTileArray[i + 3][j].kind) {
                            // Check is swappable
                            if (mTileArray[i + 2][j - 1].isMovable()
                                    && mTileArray[i + 2][j].isMovable()) {
                                // O
                                // O
                                //O
                                // O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i + 2][j - 1].mImage);
                                hintArray.add(mTileArray[i + 3][j].mImage);
                                if (j > 1 && kind == mTileArray[i + 2][j - 2].kind) {
                                    //  O
                                    //  O
                                    //OO
                                    //  O
                                    hintArray.add(mTileArray[i + 2][j - 2].mImage);
                                }
                                if (j < mColumn - 1 && kind == mTileArray[i + 2][j + 1].kind) {
                                    // O
                                    // O
                                    //O O
                                    // O
                                    hintArray.add(mTileArray[i + 2][j + 1].mImage);
                                    if (j < mColumn - 2 && kind == mTileArray[i + 2][j + 2].kind) {
                                        // O
                                        // O
                                        //O OO
                                        // O
                                        hintArray.add(mTileArray[i + 2][j + 2].mImage);
                                    }
                                }
                                return true;
                            }
                        } else if (i < mRow - 3 && j < mColumn - 1
                                && kind == mTileArray[i + 2][j + 1].kind
                                && kind == mTileArray[i + 3][j].kind) {
                            // Check is swappable
                            if (mTileArray[i + 2][j + 1].isMovable()
                                    && mTileArray[i + 2][j].isMovable()) {
                                //O
                                //O
                                // O
                                //O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i + 2][j + 1].mImage);
                                hintArray.add(mTileArray[i + 3][j].mImage);
                                if (j < mColumn - 2 && kind == mTileArray[i + 2][j + 2].kind) {
                                    //O
                                    //O
                                    // OO
                                    //O
                                    hintArray.add(mTileArray[i + 2][j + 2].mImage);
                                }
                                return true;
                            }
                        } else if (i > 1 && j > 0
                                && kind == mTileArray[i - 1][j - 1].kind
                                && kind == mTileArray[i - 2][j].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j - 1].isMovable() && mTileArray[i - 1][j].isMovable()) {
                                // O
                                //O
                                // O
                                // O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i - 1][j - 1].mImage);
                                hintArray.add(mTileArray[i - 2][j].mImage);
                                if (j > 1 && kind == mTileArray[i - 1][j - 2].kind) {
                                    //  O
                                    //OO
                                    //  O
                                    //  O
                                    hintArray.add(mTileArray[i - 1][j - 2].mImage);
                                }
                                if (j < mColumn - 1 && kind == mTileArray[i - 1][j + 1].kind) {
                                    // O
                                    //O O
                                    // O
                                    // O
                                    hintArray.add(mTileArray[i - 1][j + 1].mImage);
                                    if (j < mColumn - 2 && kind == mTileArray[i - 1][j + 2].kind) {
                                        // O
                                        //O OO
                                        // O
                                        // O
                                        hintArray.add(mTileArray[i - 1][j + 2].mImage);
                                    }
                                }
                                return true;
                            }
                        } else if (i > 1 && j < mColumn - 1
                                && kind == mTileArray[i - 1][j + 1].kind
                                && kind == mTileArray[i - 2][j].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j + 1].isMovable() && mTileArray[i - 1][j].isMovable()) {
                                //O
                                // O
                                //O
                                //O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i - 1][j + 1].mImage);
                                hintArray.add(mTileArray[i - 2][j].mImage);
                                if (j < mColumn - 2 && kind == mTileArray[i - 1][j + 2].kind) {
                                    //O
                                    // OO
                                    //O
                                    //O
                                    hintArray.add(mTileArray[i - 1][j + 2].mImage);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // Check match 4 T L in row
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 1; j++) {
                if (!mTileArray[i][j].empty
                        && mTileArray[i][j].isFruit()) {

                    // Check next 1 column
                    if (mTileArray[i][j].kind == mTileArray[i][j + 1].kind) {

                        int kind = mTileArray[i][j].kind;
                        // Check potential match
                        if (i > 0 && j < mColumn - 3
                                && kind == mTileArray[i - 1][j + 2].kind
                                && kind == mTileArray[i][j + 3].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j + 2].isMovable()
                                    && mTileArray[i][j + 2].isMovable()) {
                                //  O
                                //OO O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i - 1][j + 2].mImage);
                                hintArray.add(mTileArray[i][j + 3].mImage);
                                if (i > 1 && kind == mTileArray[i - 2][j + 2].kind) {
                                    //  O
                                    //  O
                                    //OO O
                                    hintArray.add(mTileArray[i - 2][j + 2].mImage);
                                }
                                if (i < mRow - 1 && kind == mTileArray[i + 1][j + 2].kind) {
                                    //  O
                                    //OO O
                                    //  O
                                    hintArray.add(mTileArray[i + 1][j + 2].mImage);
                                    if (i < mRow - 2 && kind == mTileArray[i + 2][j + 2].kind) {
                                        //  O
                                        //OO O
                                        //  O
                                        //  O
                                        hintArray.add(mTileArray[i + 2][j + 2].mImage);
                                    }
                                }
                                return true;
                            }
                        } else if (i < mRow - 1 && j < mColumn - 3
                                && kind == mTileArray[i + 1][j + 2].kind
                                && kind == mTileArray[i][j + 3].kind) {
                            // Check is swappable
                            if (mTileArray[i + 1][j + 2].isMovable()
                                    && mTileArray[i][j + 2].isMovable()) {
                                //OO O
                                //  O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i + 1][j + 2].mImage);
                                hintArray.add(mTileArray[i][j + 3].mImage);
                                if (i < mRow - 2 && kind == mTileArray[i + 2][j + 2].kind) {
                                    //OO O
                                    //  O
                                    //  O
                                    hintArray.add(mTileArray[i + 2][j + 2].mImage);
                                }
                                return true;
                            }
                        } else if (i > 0 && j > 1
                                && kind == mTileArray[i - 1][j - 1].kind
                                && kind == mTileArray[i][j - 2].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j - 1].isMovable()
                                    && mTileArray[i][j - 1].isMovable()) {
                                // O
                                //O OO
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i - 1][j - 1].mImage);
                                hintArray.add(mTileArray[i][j - 2].mImage);
                                if (i > 1 && kind == mTileArray[i - 2][j - 1].kind) {
                                    // O
                                    // O
                                    //O OO
                                    hintArray.add(mTileArray[i - 2][j - 1].mImage);
                                }
                                if (i < mRow - 1 && kind == mTileArray[i + 1][j - 1].kind) {
                                    // O
                                    //O OO
                                    // O
                                    hintArray.add(mTileArray[i + 1][j - 1].mImage);
                                    if (i < mRow - 2 && kind == mTileArray[i + 2][j - 1].kind) {
                                        // O
                                        //O OO
                                        // O
                                        // O
                                        hintArray.add(mTileArray[i + 2][j - 1].mImage);
                                    }
                                }
                                return true;
                            }
                        } else if (i < mRow - 1 && j > 1
                                && kind == mTileArray[i + 1][j - 1].kind
                                && kind == mTileArray[i][j - 2].kind) {
                            // Check is swappable
                            if (mTileArray[i + 1][j - 1].isMovable()
                                    && mTileArray[i][j - 1].isMovable()) {
                                //O OO
                                // O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i + 1][j - 1].mImage);
                                hintArray.add(mTileArray[i][j - 2].mImage);
                                if (i < mRow - 2 && kind == mTileArray[i + 2][j - 1].kind) {
                                    //O OO
                                    // O
                                    // O
                                    hintArray.add(mTileArray[i + 2][j - 1].mImage);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // Check match 3 in column 1
        for (int i = 0; i < mRow - 1; i++) {
            for (int j = 0; j < mColumn; j++) {
                // Check tile state
                if (!mTileArray[i][j].empty
                        && mTileArray[i][j].isFruit()) {

                    int kind = mTileArray[i][j].kind;
                    // Check next 1 row
                    if (kind == mTileArray[i + 1][j].kind) {
                        // Check potential match
                        if (i < mRow - 2 && j > 0
                                && kind == mTileArray[i + 2][j - 1].kind) {
                            // Check is swappable
                            if (mTileArray[i + 2][j - 1].isMovable()
                                    && mTileArray[i + 2][j].isMovable()) {
                                // O
                                // O
                                //O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i + 2][j - 1].mImage);
                                return true;
                            }
                        } else if (i < mRow - 2 && j < mColumn - 1
                                && kind == mTileArray[i + 2][j + 1].kind) {
                            // Check is swappable
                            if (mTileArray[i + 2][j + 1].isMovable()
                                    && mTileArray[i + 2][j].isMovable()) {
                                //O
                                //O
                                // O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i + 2][j + 1].mImage);
                                return true;
                            }
                        } else if (i > 0 && j > 0
                                && kind == mTileArray[i - 1][j - 1].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j - 1].isMovable()
                                    && mTileArray[i - 1][j].isMovable()) {
                                //O
                                // O
                                // O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i - 1][j - 1].mImage);
                                return true;
                            }
                        } else if (i > 0 && j < mColumn - 1
                                && kind == mTileArray[i - 1][j + 1].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j + 1].isMovable()
                                    && mTileArray[i - 1][j].isMovable()) {
                                // O
                                //O
                                //O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j].mImage);
                                hintArray.add(mTileArray[i - 1][j + 1].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // Check match 3 in column 2
        for (int i = 0; i < mRow - 2; i++) {
            for (int j = 0; j < mColumn; j++) {
                // Check tile state
                if (!mTileArray[i][j].empty
                        && mTileArray[i][j].isFruit()) {

                    int kind = mTileArray[i][j].kind;
                    // Check next 2 row
                    if (kind == mTileArray[i + 2][j].kind) {

                        // Check potential match
                        if (j > 0 && kind == mTileArray[i + 1][j - 1].kind) {
                            // Check is swappable
                            if (mTileArray[i + 1][j - 1].isMovable()
                                    && mTileArray[i + 1][j].isMovable()) {
                                // O
                                //O
                                // O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j - 1].mImage);
                                hintArray.add(mTileArray[i + 2][j].mImage);
                                return true;
                            }
                        } else if (j < mColumn - 1 && kind == mTileArray[i + 1][j + 1].kind) {
                            // Check is swappable
                            if (mTileArray[i + 1][j + 1].isMovable()
                                    && mTileArray[i + 1][j].isMovable()) {
                                //O
                                // O
                                //O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j + 1].mImage);
                                hintArray.add(mTileArray[i + 2][j].mImage);
                                return true;
                            }
                        } else if (i < mRow - 3 && kind == mTileArray[i + 3][j].kind) {
                            // Check is swappable
                            if (mTileArray[i][j].isMovable()
                                    && mTileArray[i + 1][j].isMovable()) {
                                //O
                                //
                                //O
                                //O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 2][j].mImage);
                                hintArray.add(mTileArray[i + 3][j].mImage);
                                return true;
                            }
                        } else if (i > 0 && kind == mTileArray[i - 1][j].kind) {
                            // Check is swappable
                            if (mTileArray[i + 1][j].isMovable()
                                    && mTileArray[i + 2][j].isMovable()) {
                                //O
                                //O
                                //
                                //O
                                hintArray.add(mTileArray[i - 1][j].mImage);
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 2][j].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // Check match 3 in row 1
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 1; j++) {
                if (!mTileArray[i][j].empty && mTileArray[i][j].isFruit()) {

                    int kind = mTileArray[i][j].kind;
                    // Check next 1 column
                    if (kind == mTileArray[i][j + 1].kind) {
                        // Check potential match
                        if (i > 0 && j < mColumn - 2
                                && kind == mTileArray[i - 1][j + 2].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j + 2].isMovable()
                                    && mTileArray[i][j + 2].isMovable()) {
                                //  O
                                //OO
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i - 1][j + 2].mImage);
                                return true;
                            }
                        } else if (i < mRow - 1 && j < mColumn - 2
                                && kind == mTileArray[i + 1][j + 2].kind) {
                            // Check is swappable
                            if (mTileArray[i + 1][j + 2].isMovable()
                                    && mTileArray[i][j + 2].isMovable()) {
                                //OO
                                //  O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i + 1][j + 2].mImage);
                                return true;
                            }
                        } else if (i > 0 && j > 0
                                && kind == mTileArray[i - 1][j - 1].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j - 1].isMovable()
                                    && mTileArray[i][j - 1].isMovable()) {
                                //O
                                // OO
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i - 1][j - 1].mImage);
                                return true;
                            }
                        } else if (i < mRow - 1 && j > 0
                                && kind == mTileArray[i + 1][j - 1].kind) {
                            // Check is swappable
                            if (mTileArray[i + 1][j - 1].isMovable()
                                    && mTileArray[i][j - 1].isMovable()) {
                                // OO
                                //O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 1].mImage);
                                hintArray.add(mTileArray[i + 1][j - 1].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // Check match 3 in row 2
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 2; j++) {
                if (!mTileArray[i][j].empty
                        && mTileArray[i][j].isFruit()) {

                    int kind = mTileArray[i][j].kind;
                    // Check next 2 column
                    if (kind == mTileArray[i][j + 2].kind) {

                        // Check potential match
                        if (i > 0 && kind == mTileArray[i - 1][j + 1].kind) {
                            // Check is swappable
                            if (mTileArray[i - 1][j + 1].isMovable()
                                    && mTileArray[i][j + 1].isMovable()) {
                                // O
                                //O O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i - 1][j + 1].mImage);
                                hintArray.add(mTileArray[i][j + 2].mImage);
                                return true;
                            }
                        } else if (i < mRow - 1 && kind == mTileArray[i + 1][j + 1].kind) {
                            // Check is swappable
                            if (mTileArray[i + 1][j + 1].isMovable()
                                    && mTileArray[i][j + 1].isMovable()) {
                                //O O
                                // O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i + 1][j + 1].mImage);
                                hintArray.add(mTileArray[i][j + 2].mImage);
                                return true;
                            }
                        } else if (j < mColumn - 3 && kind == mTileArray[i][j + 3].kind) {
                            // Check is swappable
                            if (mTileArray[i][j].isMovable()
                                    && mTileArray[i][j + 1].isMovable()) {
                                //O OO
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 2].mImage);
                                hintArray.add(mTileArray[i][j + 3].mImage);
                                return true;
                            }
                        } else if (j > 0 && kind == mTileArray[i][j - 1].kind) {
                            // Check is swappable
                            if (mTileArray[i][j + 2].isMovable()
                                    && mTileArray[i][j + 1].isMovable()) {
                                //OO O
                                hintArray.add(mTileArray[i][j].mImage);
                                hintArray.add(mTileArray[i][j + 2].mImage);
                                hintArray.add(mTileArray[i][j - 1].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // Check ice cream
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {

                if (mTileArray[i][j].direct == 'I' && mTileArray[i][j].isMovable()) {

                    // Check nearby fruit is swappable
                    if (i > 0 && mTileArray[i - 1][j].isMovable()
                            && mTileArray[i - 1][j].isFruit()) {
                        hintArray.add(mTileArray[i][j].mImage);
                        hintArray.add(mTileArray[i - 1][j].mImage);
                        return true;
                    } else if (i < mRow - 1 && mTileArray[i + 1][j].isMovable()
                            && mTileArray[i + 1][j].isFruit()) {
                        hintArray.add(mTileArray[i][j].mImage);
                        hintArray.add(mTileArray[i + 1][j].mImage);
                        return true;
                    } else if (j > 0 && mTileArray[i][j - 1].isMovable()
                            && mTileArray[i][j - 1].isFruit()) {
                        hintArray.add(mTileArray[i][j].mImage);
                        hintArray.add(mTileArray[i][j - 1].mImage);
                        return true;
                    } else if (j < mColumn - 1 && mTileArray[i][j + 1].isMovable()
                            && mTileArray[i][j + 1].isFruit()) {
                        hintArray.add(mTileArray[i][j].mImage);
                        hintArray.add(mTileArray[i][j + 1].mImage);
                        return true;
                    }

                }
            }
        }

        return false;
    }

}
