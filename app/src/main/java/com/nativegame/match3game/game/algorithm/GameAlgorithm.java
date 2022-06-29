package com.nativegame.match3game.game.algorithm;

import android.widget.ImageView;

import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.GameEvent;
import com.nativegame.match3game.game.tile.Tile;
import com.nativegame.match3game.game.tile.TileUtils;
import com.nativegame.match3game.game.state.CollectGameState;
import com.nativegame.match3game.game.state.BaseGameState;
import com.nativegame.match3game.game.state.ScoreGameState;
import com.nativegame.match3game.game.state.StarGameState;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * GameAlgorithm contain the core algorithm of our match 3 game
 */

public class GameAlgorithm extends BaseAlgorithm {
    private ImageView[][] mIceArray, mIceArray2;
    private ImageView[][] mAdvanceArray;
    private BaseGameState mGameState;
    //----------------------------------------------------------------------------------
    // Var to change state of game
    //----------------------------------------------------------------------------------
    private int mSwapCol, mSwapRow, mSwapCol2, mSwapRow2;
    private boolean mSwapping = false, mShowHint = false;
    private int mCombo = 0;
    // Tile moving control
    private static final int WAITING_TIME = 300;
    private static final int EFFECT_WAITING_TIME = 1200;
    public boolean mMoveTile = false;
    //==================================================================================

    public GameAlgorithm(GameEngine gameEngine) {
        super(gameEngine);
        initGameState();
    }

    private void initGameState() {
        switch (mGameEngine.mLevel.mLevelType) {
            case LEVEL_TYPE_SCORE:
                mGameState = new ScoreGameState(mGameEngine);
                break;
            case LEVEL_TYPE_COLLECT:
            case LEVEL_TYPE_ICE:
                mGameState = new CollectGameState(mGameEngine);
                break;
            case LEVEL_TYPE_STARFISH:
                mGameState = new StarGameState(mGameEngine);
                break;
        }

    }

    public void setIceArray(ImageView[][] iceArray, ImageView[][] iceArray2) {
        mIceArray = iceArray;
        mIceArray2 = iceArray2;
    }

    public void setAdvanceArray(ImageView[][] advanceArray) {
        mAdvanceArray = advanceArray;
    }

    @Override
    public void update(Tile[][] tileArray, long elapsedMillis) {

        // Do nothing when waiting event
        if (mIsTransf) {
            mWaitingTime += elapsedMillis;
            if (mWaitingTime > EFFECT_WAITING_TIME) {
                mIsTransf = false;
                mWaitingTime = 0;
            }
            return;
        }

        // 1. Find match
        updateWait(tileArray);
        if (!mWaitFinding) {
            findMatch(tileArray);
        }

        // 2. Moving
        if (mMoveTile) {

            for (int i = 0; i < mRow; i++) {
                for (int j = 0; j < mColumn; j++) {

                    tileArray[i][j].onUpdate(elapsedMillis);

                    if (!tileArray[i][j].isMoving()) {
                        // Start bouncing animation
                        if (tileArray[i][j].bounce == 1) {
                            mAnimationManager.createLightBounceAnim(tileArray[i][j].mImage);
                        } else if (tileArray[i][j].bounce == 2) {
                            mAnimationManager.createHeavyBounceAnim(tileArray[i][j].mImage);
                        }

                        tileArray[i][j].bounce = 0;
                    }
                }
            }
        }

        updateMove(tileArray);

        // 3. Fruit wait
        if (!mIsMoving && !mWaitFinding) {
            // Play fruit bouncing sound when tile stop
            if (mMoveTile && !mSwapping)
                mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_BOUNCING);
            mMoveTile = false;
        } else {
            // Check is player swapping
            if (!mMoveTile) {
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > WAITING_TIME) {
                    mMoveTile = true;
                    mWaitingTime = 0;
                }
            }
        }

        updateMatch(tileArray);

        // Check combo
        if (!mIsMoving) {
            if (mMatchFinding) {
                mCombo++;
                if (mCombo == 1) {
                    mSoundManager.playSoundForSoundEvent(SoundEvent.COMB01);
                } else if (mCombo == 2) {
                    mSoundManager.playSoundForSoundEvent(SoundEvent.COMB02);
                } else if (mCombo == 3) {
                    mSoundManager.playSoundForSoundEvent(SoundEvent.COMB03);
                } else {
                    mSoundManager.playSoundForSoundEvent(SoundEvent.COMBO4);
                }
            } else {
                if (!mWaitFinding) {
                    if (mCombo >= 4) {
                        if (mCombo == 4) {
                            mGameEngine.onGameEvent(GameEvent.COMBO_4);
                        } else if (mCombo == 5) {
                            mGameEngine.onGameEvent(GameEvent.COMBO_5);
                        } else {
                            mGameEngine.onGameEvent(GameEvent.COMBO_6);
                        }

                        mCombo = 0;
                        return;
                    }
                    mCombo = 0;
                }
            }
        }

        // Check hint
        if (!mSwapping && !mIsMoving) {
            // Check is potential moving
            if (!mMatchFinding && !mWaitFinding && mShowHint) {
                //Show hint
                mGameEngine.onGameEvent(GameEvent.START_HINT);
                mShowHint = false;
            }
        }

        // 4. Swap back if no match
        if (mSwapping && !mIsMoving) {
            if (!mMatchFinding) {
                // Player make an invalid swap
                swap(tileArray, tileArray[mSwapRow][mSwapCol], tileArray[mSwapRow2][mSwapCol2]);
                mMoveTile = true;
            } else {
                // Player make valid swap
                mGameEngine.onGameEvent(GameEvent.PLAYER_SWAP);
            }
            mSwapping = false;
        }

        // 5. Update tile
        if (!mIsMoving) {

            // (5.1) Check special fruit
            outer:
            for (int j = 0; j < mColumn; j++) {
                for (int i = 0; i < mRow; i++) {
                    // Check is special fruit
                    if (tileArray[i][j].special
                            && tileArray[i][j].match != 0
                            && !tileArray[i][j].lock) {
                        // Check direction
                        if (tileArray[i][j].direct == 'H') {
                            // Check special combine
                            if (tileArray[i][j].specialCombine == 'R') {
                                explodeV(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].specialCombine == 'G') {
                                explodeBigH(tileArray, tileArray[i][j]);
                            } else {
                                if (!tileArray[i][j].isExplode)
                                    explodeH(tileArray, tileArray[i][j]);
                            }
                        } else if (tileArray[i][j].direct == 'V') {
                            // Check special combine
                            if (tileArray[i][j].specialCombine == 'R') {
                                explodeH(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].specialCombine == 'G') {
                                explodeBigV(tileArray, tileArray[i][j]);
                            } else {
                                if (!tileArray[i][j].isExplode)
                                    explodeV(tileArray, tileArray[i][j]);
                            }
                        } else if (tileArray[i][j].direct == 'S' && !tileArray[i][j].isExplode) {
                            // Check special combine
                            if (tileArray[i][j].specialCombine == 'B') {
                                explodeBigS(tileArray, tileArray[i][j]);
                            } else {
                                explodeS(tileArray, tileArray[i][j]);
                            }
                        } else if (tileArray[i][j].direct == 'I' && !tileArray[i][j].isExplode) {
                            // Check special combine
                            if (tileArray[i][j].specialCombine == 'T') {
                                transI(tileArray, tileArray[i][j]);   // We break the loop
                                break outer;
                            } else if (tileArray[i][j].specialCombine == 'S') {
                                transI(tileArray, tileArray[i][j]);   // We break the loop
                                break outer;
                            } else if (tileArray[i][j].specialCombine == 'M') {
                                explodeBigI(tileArray, tileArray[i][j]);
                            } else {
                                explodeI(tileArray, tileArray[i][j]);
                            }
                        }
                    }
                }
            }

            // (5.2) Add square special fruit
            for (int i = 0; i < mRow; i++) {
                for (int j = 1; j < mColumn - 1; j++) {
                    // Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        // Check row for 3
                        if (tileArray[i][j].kind == tileArray[i][j - 1].kind &&
                                tileArray[i][j].kind == tileArray[i][j + 1].kind) {
                            // Check potential match
                            if (i > 0 && tileArray[i][j].kind == tileArray[i - 1][j - 1].kind
                                    && tileArray[i - 1][j - 1].match > 0) {   //Top left
                                // If tile is special, do not add
                                if (!tileArray[i][j - 1].special) {
                                    if (i > 1 && tileArray[i - 2][j - 1].kind == tileArray[i][j].kind) {
                                        /* O
                                         * O
                                         * X O O
                                         */
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j - 1], 'L', 1);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j - 1].isUpgrade = true;
                                        tileArray[i - 2][j - 1].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j - 1].direct = 'S';
                                    } else if (i < mRow - 1 && tileArray[i + 1][j - 1].kind == tileArray[i][j].kind) {
                                        /* O
                                         * X O O
                                         * O
                                         */
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j - 1], 'L', 2);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j - 1].isUpgrade = true;
                                        tileArray[i + 1][j - 1].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j - 1].direct = 'S';
                                    }
                                }
                            } else if (i < mRow - 1 && tileArray[i][j].kind == tileArray[i + 1][j - 1].kind
                                    && tileArray[i + 1][j - 1].match > 0) {   //Bottom Left
                                // If tile is special, do not add
                                if (!tileArray[i][j - 1].special) {
                                    if (i < mRow - 2 && tileArray[i + 2][j - 1].kind == tileArray[i][j].kind) {
                                        /* X O O
                                         * O
                                         * O
                                         */
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j - 1], 'L', 3);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i + 1][j - 1].isUpgrade = true;
                                        tileArray[i + 2][j - 1].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j - 1].direct = 'S';
                                    }
                                }
                            } else if (i > 0 && tileArray[i][j].kind == tileArray[i - 1][j].kind
                                    && tileArray[i - 1][j].match > 0) {   //Top Center
                                // If tile is special, do not add
                                if (!tileArray[i][j].special) {
                                    if (i > 1 && tileArray[i - 2][j].kind == tileArray[i][j].kind) {
                                        /*   O
                                         *   O
                                         * O X O
                                         */
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j], 'C', 1);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j].isUpgrade = true;
                                        tileArray[i - 2][j].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j].direct = 'S';
                                    } else if (i < mRow - 1 && tileArray[i + 1][j].kind == tileArray[i][j].kind) {
                                        /*   O
                                         * O X O
                                         *   O
                                         */
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j], 'C', 2);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j].isUpgrade = true;
                                        tileArray[i + 1][j].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j].direct = 'S';
                                    }
                                }
                            } else if (i < mRow - 1 && tileArray[i][j].kind == tileArray[i + 1][j].kind
                                    && tileArray[i + 1][j].match > 0) {   //Bottom Center
                                // If tile is special, do not add
                                if (!tileArray[i][j].special) {
                                    if (i < mRow - 2 && tileArray[i + 2][j].kind == tileArray[i][j].kind) {
                                        /* O X O
                                         *   O
                                         *   O
                                         */
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j], 'C', 3);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i + 1][j].isUpgrade = true;
                                        tileArray[i + 2][j].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j].direct = 'S';
                                    }
                                }
                            } else if (i > 0 && tileArray[i][j].kind == tileArray[i - 1][j + 1].kind
                                    && tileArray[i - 1][j + 1].match > 0) {   //Top Right
                                // If tile is special, do not add
                                if (!tileArray[i][j + 1].special) {
                                    if (i > 1 && tileArray[i - 2][j + 1].kind == tileArray[i][j].kind) {
                                        /*     O
                                         *     O
                                         * O O X
                                         */
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j + 1], 'R', 1);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i - 1][j + 1].isUpgrade = true;
                                        tileArray[i - 2][j + 1].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j + 1].direct = 'S';
                                    } else if (i < mRow - 1 && tileArray[i + 1][j + 1].kind == tileArray[i][j].kind) {
                                        /*     O
                                         * O O X
                                         *     O
                                         */
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j + 1], 'R', 2);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i - 1][j + 1].isUpgrade = true;
                                        tileArray[i + 1][j + 1].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j + 1].direct = 'S';
                                    }
                                }
                            } else if (i < mRow - 1 && tileArray[i][j].kind == tileArray[i + 1][j + 1].kind
                                    && tileArray[i + 1][j + 1].match > 0) {   //Bottom Right
                                // If tile is special, do not add
                                if (!tileArray[i][j + 1].special) {
                                    if (i < mRow - 2 && tileArray[i + 2][j + 1].kind == tileArray[i][j].kind) {
                                        /* O O X
                                         *     O
                                         *     O
                                         */
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j + 1], 'R', 3);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i + 1][j + 1].isUpgrade = true;
                                        tileArray[i + 2][j + 1].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j + 1].direct = 'S';
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // (5.3) Add vertical special fruit
            for (int i = 0; i < mRow; i++) {
                for (int j = 0; j < mColumn - 3; j++) {
                    // Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        // Check row for >= 4
                        if (tileArray[i][j].match > 0 && tileArray[i][j + 1].match > 0
                                && tileArray[i][j].kind == tileArray[i][j + 1].kind
                                && tileArray[i][j].kind == tileArray[i][j + 2].kind
                                && tileArray[i][j].kind == tileArray[i][j + 3].kind) {
                            // Check row for 5
                            if (j < mColumn - 4 && tileArray[i][j].kind == tileArray[i][j + 4].kind) {
                                // O O X O O
                                // Add upgrade animation
                                mAnimationManager.upgrade2I_h(tileArray[i][j + 2]);
                                mSoundManager.playSoundForSoundEvent(SoundEvent.ICE_CREAM_UPGRADE);
                                tileArray[i][j].isUpgrade = true;
                                tileArray[i][j + 1].isUpgrade = true;
                                tileArray[i][j + 3].isUpgrade = true;
                                tileArray[i][j + 4].isUpgrade = true;
                                // Make it special
                                tileArray[i][j + 2].direct = 'I';
                                tileArray[i][j + 2].special = false;   // We make sure it will transform before explode
                                tileArray[i][j + 2].isExplode = false;   // We make sure it will detect
                                tileArray[i][j + 2].kind = TileUtils.ICE_CREAM;
                            } else {
                                // Check is right or left be chosen
                                if ((i == mSwapRow && j + 1 == mSwapCol) || (i == mSwapRow2 && j + 1 == mSwapCol2)) {
                                    // If tile is already special, do not add
                                    if (tileArray[i][j + 1].direct == 'N' && !tileArray[i][j + 1].isUpgrade) {
                                        // O X O O
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2H_left(tileArray[i][j + 1]);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 2].isUpgrade = true;
                                        tileArray[i][j + 3].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j + 1].direct = 'V';
                                    }
                                } else {
                                    // If tile is already special, do not add
                                    if (tileArray[i][j + 2].direct == 'N' && !tileArray[i][j + 2].isUpgrade) {
                                        // O O X O O
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2H_right(tileArray[i][j + 2]);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i][j + 3].isUpgrade = true;
                                        // Make it special
                                        tileArray[i][j + 2].direct = 'V';
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // (5.4) Add horizontal special fruit
            for (int j = 0; j < mColumn; j++) {
                for (int i = 0; i < mRow - 3; i++) {
                    // Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        // Check column for >= 4
                        if (tileArray[i][j].match > 0 && tileArray[i + 1][j].match > 0
                                && tileArray[i][j].kind == tileArray[i + 1][j].kind
                                && tileArray[i][j].kind == tileArray[i + 2][j].kind
                                && tileArray[i][j].kind == tileArray[i + 3][j].kind) {
                            // Check row for 5
                            if (i < mRow - 4 && tileArray[i][j].kind == tileArray[i + 4][j].kind) {
                                // Add upgrade animation
                                mAnimationManager.upgrade2I_v(tileArray[i + 2][j]);
                                mSoundManager.playSoundForSoundEvent(SoundEvent.ICE_CREAM_UPGRADE);
                                tileArray[i][j].isUpgrade = true;
                                tileArray[i + 1][j].isUpgrade = true;
                                tileArray[i + 3][j].isUpgrade = true;
                                tileArray[i + 4][j].isUpgrade = true;
                                // Make it special
                                tileArray[i + 2][j].direct = 'I';
                                tileArray[i + 2][j].special = false;   // We make sure it will transform before explode
                                tileArray[i + 2][j].isExplode = false;   // We make sure it will detect
                                tileArray[i + 2][j].kind = TileUtils.ICE_CREAM;
                            } else {
                                // Check is top or bottom be chosen
                                if ((i + 1 == mSwapRow && j == mSwapCol)
                                        || (i + 1 == mSwapRow2 && j == mSwapCol2)) {
                                    // If tile is already special, do not add
                                    if (tileArray[i + 1][j].direct == 'N' && !tileArray[i + 1][j].isUpgrade) {
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2V_top(tileArray[i + 1][j]);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i + 2][j].isUpgrade = true;
                                        tileArray[i + 3][j].isUpgrade = true;
                                        // Make it special
                                        tileArray[i + 1][j].direct = 'H';
                                    }
                                } else {
                                    // If tile is already special, do not add
                                    if (tileArray[i + 2][j].direct == 'N' && !tileArray[i + 2][j].isUpgrade) {
                                        // Add upgrade animation
                                        mAnimationManager.upgrade2V_bottom(tileArray[i + 2][j]);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i + 1][j].isUpgrade = true;
                                        tileArray[i + 3][j].isUpgrade = true;
                                        // Make it special
                                        tileArray[i + 2][j].direct = 'H';
                                    }

                                }
                            }
                        }
                    }
                }
            }

            // (5.5) Check invalid tile
            for (int i = mRow - 1; i >= 0; i--) {
                for (int j = 0; j < mColumn; j++) {

                    // Check invalid tile match
                    if (tileArray[i][j].invalid && tileArray[i][j].match != 0) {

                        if (i == 0) {
                            // Add match to whole column if not waiting
                            for (int m = i + 1; m < mRow; m++) {
                                // Check invalid obstacle (if match != 0 means can go down)
                                if (tileArray[m][j].tube) {
                                    continue;
                                } else if (tileArray[m][j].invalid && tileArray[m][j].match == 0) {
                                    break;
                                } else if (tileArray[m][j].wait != 0) {
                                    tileArray[m][j].wait = 0;
                                    tileArray[m][j].match++;
                                }
                            }
                        } else {
                            // Check up 3 in row, if any column tile is available, then can falling down
                            for (int n = j - 1; n <= j + 1; n++) {

                                if (n < 0 || n >= mColumn)
                                    continue;

                                // If find tile can fill
                                if (!tileArray[i - 1][n].invalid || (tileArray[i - 1][n].tube && n == j)) {

                                    /* The tile can only go though tube vertically from bottom
                                     *      | |   <-- tube
                                     *      | |
                                     *     x o x  <-- tile (No diagonal swapping)
                                     */

                                    // Add match to whole column if not waiting
                                    for (int m = i + 1; m < mRow; m++) {
                                        // Check invalid obstacle (if match != 0 means can go down)
                                        if (tileArray[m][j].tube) {
                                            continue;
                                        } else if (tileArray[m][j].invalid && tileArray[m][j].match == 0) {
                                            break;
                                        } else if (tileArray[m][j].wait != 0) {
                                            tileArray[m][j].wait = 0;
                                            tileArray[m][j].match++;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            // (5.6) Update game state
            if (mGameState.isPlayerReachTarget()) {
                if (!mMatchFinding && !mWaitFinding) {
                    playerWin(tileArray);
                    return;
                }
            }

            if (mGameState.isPlayerOutOfMove()) {
                if (!mMatchFinding && !mWaitFinding && !mIsTransf) {
                    playerLoss();
                    return;
                }
            }

            mGameState.onUpdate(tileArray);

            // (5.7) Add score
            for (int j = 0; j < mColumn; j++) {
                for (int i = 0; i < mRow; i++) {
                    if (tileArray[i][j].match != 0 && tileArray[i][j].isUnblockFruitOrIceCream()) {
                        mGameEngine.onGameEvent(GameEvent.PLAYER_SCORE);
                    }
                }
            }

            // (5.8) Add animation and sound
            for (int j = 0; j < mColumn; j++) {
                for (int i = 0; i < mRow; i++) {

                    // Check is match
                    if (!tileArray[i][j].empty
                            && tileArray[i][j].match != 0
                            && !tileArray[i][j].isAnimate) {

                        if (tileArray[i][j].kind == 0) {
                            // Check empty tile with ice
                            if (tileArray[i][j].ice > 0 && tileArray[i][j].wait == 2)
                                explodeIce(mIceArray[i][j], mIceArray2[i][j], tileArray[i][j]);
                            continue;
                        }

                        // Set isAnimate
                        tileArray[i][j].isAnimate = true;

                        // Check lock
                        if (tileArray[i][j].lock) {
                            explodeLock(mAdvanceArray[i + 1][j], tileArray[i][j]);
                            continue;
                        }

                        // Check is starfish
                        if (tileArray[i][j].kind == TileUtils.STAR_FISH) {
                            if (tileArray[i][j].entryPoint) {
                                mAnimationManager.explodeStarFish(tileArray[i][j]);
                                mSoundManager.playSoundForSoundEvent(SoundEvent.COLLECT_STAR_FISH);
                            } else {
                                tileArray[i][j].match = 0;
                            }
                            continue;
                        }

                        // Check breakable
                        if (tileArray[i][j].kind == TileUtils.CRACKER) {
                            // Explode cracker
                            mAnimationManager.explodeCracker(tileArray[i][j]);
                            mSoundManager.playSoundForSoundEvent(SoundEvent.CRACKER_EXPLODE);
                        } else if (tileArray[i][j].kind == TileUtils.COOKIE) {
                            // Check how many layer
                            switch (tileArray[i][j].layer) {
                                case 0:
                                    // Explode cookie
                                    tileArray[i][j].invalid = false;
                                    mAnimationManager.explodeCookie(tileArray[i][j]);
                                    mSoundManager.playSoundForSoundEvent(SoundEvent.COOKIE_EXPLODE);
                                    break;
                                case 1:
                                case 2:
                                case 3:
                                    tileArray[i][j].match = 0;
                                    tileArray[i][j].layer--;
                                    mAnimationManager.explodeCookieLayer(tileArray[i][j]);
                                    break;
                            }
                        } else {

                            // Explode ice
                            if (tileArray[i][j].ice > 0)
                                explodeIce(mIceArray[i][j], mIceArray2[i][j], tileArray[i][j]);

                            if (tileArray[i][j].direct != 'N' && !tileArray[i][j].special) {
                                tileArray[i][j].special = true;
                                tileArray[i][j].match = 0;
                                continue;
                            }

                            // Explode fruit
                            if (!tileArray[i][j].isUpgrade) {
                                mAnimationManager.explodeFruit(tileArray[i][j]);

                            }
                            // Show score
                            mAnimationManager.createScore(tileArray[i][j]);
                        }
                    }
                }
            }

            // Do nothing when transform
            if (mIsTransf)
                return;

            // (5.9) Reset
            tile2Top(tileArray);
            tileReset(tileArray);
        }

        // 6. Diagonal swapping
        updateWait(tileArray);
        diagonalSwap(tileArray);

        if (mWaitFinding) {
            tile2Top(tileArray);
            tileReset(tileArray);
        }

    }

    //----------------------------------------------------------------------------------
    // Method control refresh
    //----------------------------------------------------------------------------------
    public void playRefreshAnimation(Tile[][] tileArray) {
        // Play fade out animation
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                // Check tile state
                if (tileArray[i][j].isMovable()
                        && tileArray[i][j].isFruit()
                        && !tileArray[i][j].special) {
                    // Do not refresh special fruit
                    mAnimationManager.createRefreshAnim(tileArray[i][j].mImage);
                }
            }
        }

    }

    public void refresh(Tile[][] tileArray) {
        // Refresh the screen, and generate random fruit
        for (int j = 0; j < mColumn; j++) {
            for (int i = mRow - 1; i >= 0; i--) {
                // Check tile state
                if (tileArray[i][j].isMovable()
                        && tileArray[i][j].isFruit()
                        && !tileArray[i][j].special) {
                    // Assign new tile
                    do {
                        tileArray[i][j].setRandomFruit();
                    } while ((i < mRow - 2 && tileArray[i + 1][j].kind == tileArray[i][j].kind
                            && tileArray[i + 2][j].kind == tileArray[i][j].kind)
                            || (j >= 2 && tileArray[i][j - 1].kind == tileArray[i][j].kind
                            && tileArray[i][j - 2].kind == tileArray[i][j].kind));
                }
            }
        }

        // Resume hint
        resumeHint();
    }
    //==================================================================================

    //----------------------------------------------------------------------------------
    // Method control swap
    //----------------------------------------------------------------------------------
    public void playerSwap(Tile[][] tileArray, int row1, int col1, int row2, int col2) {
        checkSpecialCombine(tileArray[row1][col1], tileArray[row2][col2]);
        swap(tileArray, tileArray[row1][col1], tileArray[row2][col2]);
        mSwapCol = col1;
        mSwapRow = row1;
        mSwapCol2 = col2;
        mSwapRow2 = row2;
        mSwapping = true;
        mMoveTile = true;
        resumeHint();
    }

    public boolean canPlayerSwap() {
        if (mIsMoving || mMatchFinding || mWaitFinding) {
            return false;
        }
        return true;
    }

    public void checkSpecialCombine(Tile tile1, Tile tile2) {
        if (tile1.special && tile2.special) {
            if (tile1.direct == 'H') {
                switch (tile2.direct) {
                    case 'H':
                        tile1.match++;
                        tile2.match++;
                        // Check reverse tile
                        if (tile1.row == tile2.row) {
                            tile1.specialCombine = 'R';
                        } else {
                            tile2.specialCombine = 'R';
                        }
                        break;
                    case 'V':
                        tile1.match++;
                        tile2.match++;
                        break;
                    case 'S':
                        tile1.match++;
                        tile2.isExplode = true;
                        tile1.specialCombine = 'G';
                        break;
                    case 'I':
                        tile1.match++;
                        tile2.match++;
                        tile1.isExplode = true;    // So tile1 became regular fruit
                        tile2.specialCombine = 'T';
                        tile2.iceCreamTarget = tile1.kind;
                        break;
                }
            } else if (tile1.direct == 'V') {
                switch (tile2.direct) {
                    case 'H':
                        tile1.match++;
                        tile2.match++;
                        break;
                    case 'V':
                        tile1.match++;
                        tile2.match++;
                        // Check reverse tile
                        if (tile1.row == tile2.row) {
                            tile2.specialCombine = 'R';
                        } else {
                            tile1.specialCombine = 'R';
                        }
                        break;
                    case 'S':
                        tile1.match++;
                        tile2.isExplode = true;
                        tile1.specialCombine = 'G';
                        break;
                    case 'I':
                        tile1.match++;
                        tile2.match++;
                        tile1.isExplode = true;
                        tile2.specialCombine = 'T';
                        tile2.iceCreamTarget = tile1.kind;
                        break;
                }
            } else if (tile1.direct == 'S') {
                switch (tile2.direct) {
                    case 'H':
                    case 'V':
                        tile2.match++;
                        tile1.isExplode = true;
                        tile2.specialCombine = 'G';
                        break;
                    case 'S':
                        tile1.match++;
                        tile2.isExplode = true;
                        tile1.specialCombine = 'B';
                        break;
                    case 'I':
                        tile1.match++;
                        tile2.match++;
                        tile1.isExplode = true;
                        tile2.specialCombine = 'S';
                        tile2.iceCreamTarget = tile1.kind;
                        break;
                }
            } else if (tile1.direct == 'I') {
                switch (tile2.direct) {
                    case 'H':
                    case 'V':
                        tile1.match++;
                        tile2.match++;
                        tile2.isExplode = true;
                        tile1.specialCombine = 'T';
                        tile1.iceCreamTarget = tile2.kind;
                        break;
                    case 'S':
                        tile1.match++;
                        tile2.match++;
                        tile2.isExplode = true;
                        tile1.specialCombine = 'S';
                        tile1.iceCreamTarget = tile2.kind;
                        break;
                    case 'I':
                        tile1.match++;
                        tile2.match++;
                        tile1.specialCombine = 'M';
                        tile2.isExplode = true;
                        break;
                }
            }
        } else if (tile1.direct == 'I' && tile2.isFruit()) {
            tile1.match++;
            tile2.match++;
            tile1.iceCreamTarget = tile2.kind;
        } else if (tile2.direct == 'I' && tile1.isFruit()) {
            tile1.match++;
            tile2.match++;
            tile2.iceCreamTarget = tile1.kind;
        }
    }

    public void resumeHint() {
        mShowHint = true;
    }
    //==================================================================================

    private void playerWin(Tile[][] tileArray) {
        mGameEngine.onGameEvent(GameEvent.PLAYER_REACH_TARGET);

        // Unlock all the tile
        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow; i++) {

                // Check lock
                if (tileArray[i][j].lock) {
                    explodeLock(mAdvanceArray[i + 1][j], tileArray[i][j]);
                }

            }
        }

    }

    private void playerLoss() {
        mGameEngine.onGameEvent(GameEvent.PLAYER_OUT_OF_MOVE);
    }

    private void explodeIce(ImageView ice, ImageView ice2, Tile tile) {
        if (tile.ice == 1) {
            mGameEngine.onGameEvent(GameEvent.BREAK_ICE);
            if (ice != null) {
                mAnimationManager.explodeIce(ice, 10);
                mSoundManager.playSoundForSoundEvent(SoundEvent.ICE_EXPLODE);
            }
        } else if (tile.ice == 2) {
            if (ice2 != null) {
                mAnimationManager.explodeIce(ice2, 5);
                mSoundManager.playSoundForSoundEvent(SoundEvent.ICE_EXPLODE);
            }
        }
        tile.ice--;

    }

    private void explodeLock(ImageView lock, Tile tile) {
        tile.match = 0;
        tile.lock = false;
        if (tile.kind != TileUtils.COOKIE)
            tile.invalid = false;
        mAnimationManager.explodeLock(lock, tile);
        mSoundManager.playSoundForSoundEvent(SoundEvent.LOCK_EXPLODE);
    }

}
