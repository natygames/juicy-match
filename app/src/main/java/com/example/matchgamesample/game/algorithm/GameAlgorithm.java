package com.example.matchgamesample.game.algorithm;

import android.widget.ImageView;

import com.example.matchgamesample.effect.sound.SoundEvent;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.game.tile.Tile;
import com.example.matchgamesample.game.tile.TileUtils;
import com.example.matchgamesample.game.state.CollectGameState;
import com.example.matchgamesample.game.state.BaseGameState;
import com.example.matchgamesample.game.state.IceGameState;
import com.example.matchgamesample.game.state.ScoreGameState;
import com.example.matchgamesample.game.state.StarGameState;

public class GameAlgorithm extends BaseAlgorithm {
    private ImageView[][] iceArray, iceArray2;
    private ImageView[][] advanceArray;
    private BaseGameState mGameState;
    //----------------------------------------------------------------------------------
    // Var to change state of game
    //----------------------------------------------------------------------------------
    public int swapCol, swapRow, swapCol2, swapRow2;
    public boolean isSwap = false, mShowHint = false, mRefresh = false;
    private int combo = 0;
    // Tile moving control
    private static final int WAITING_TIME = 300;
    private static final int EFFECT_WAITING_TIME = 1200;
    private static final int REFRESH_WAITING_TIME = 800;
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
                mGameState = new CollectGameState(mGameEngine);
                break;
            case LEVEL_TYPE_ICE:
                mGameState = new IceGameState(mGameEngine);
                break;
            case LEVEL_TYPE_STARFISH:
                mGameState = new StarGameState(mGameEngine);
                break;
        }

    }

    public void setIceArray(ImageView[][] iceArray, ImageView[][] iceArray2) {
        this.iceArray = iceArray;
        this.iceArray2 = iceArray2;
    }

    public void setAdvanceArray(ImageView[][] advanceArray) {
        this.advanceArray = advanceArray;
    }

    @Override
    public void update(Tile[][] tileArray, long elapsedMillis) {

        // Do nothing when waiting event
        if (isTransf) {
            mWaitingTime += elapsedMillis;
            if (mWaitingTime > EFFECT_WAITING_TIME) {
                isTransf = false;
                mWaitingTime = 0;
            }
            return;
        }

        if (mRefresh) {
            mWaitingTime += elapsedMillis;
            if (mWaitingTime > REFRESH_WAITING_TIME) {

                for (int j = 0; j < mColumn; j++) {
                    for (int i = mRow - 1; i >= 0; i--) {
                        //Check tile state
                        if (tileArray[i][j].isMovable()
                                && tileArray[i][j].isFruit()
                                && !tileArray[i][j].special) {

                            //Assign new tile
                            do {
                                tileArray[i][j].setRandomFruit();
                            } while ((i < mRow - 2 && tileArray[i + 1][j].kind == tileArray[i][j].kind
                                    && tileArray[i + 2][j].kind == tileArray[i][j].kind)
                                    || (j >= 2 && tileArray[i][j - 1].kind == tileArray[i][j].kind
                                    && tileArray[i][j - 2].kind == tileArray[i][j].kind));
                        }
                    }
                }

                mGameEngine.onGameEvent(GameEvent.START_HINT);
                mRefresh = false;
                mWaitingTime = 0;
            }
            return;
        }

        // 1. Find match
        updateWait(tileArray);
        if (!waitFinding) {
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
        if (!isMoving && !waitFinding) {
            // Play fruit bouncing sound when tile stop
            if (mMoveTile && !isSwap)
                mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_BOUNCING);
            mMoveTile = false;
        } else {
            //Check is player swapping
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
        if (!isMoving) {
            if (matchFinding) {
                combo++;
                if (combo == 1) {
                    mSoundManager.playSoundForSoundEvent(SoundEvent.COMB01);
                } else if (combo == 2) {
                    mSoundManager.playSoundForSoundEvent(SoundEvent.COMB02);
                } else if (combo == 3) {
                    mSoundManager.playSoundForSoundEvent(SoundEvent.COMB03);
                } else {
                    mSoundManager.playSoundForSoundEvent(SoundEvent.COMBO4);
                }
            } else {
                if (!waitFinding) {
                    if (combo >= 4) {
                        if (combo == 4) {
                            mGameEngine.onGameEvent(GameEvent.COMBO_4);
                        } else if (combo == 5) {
                            mGameEngine.onGameEvent(GameEvent.COMBO_5);
                        } else {
                            mGameEngine.onGameEvent(GameEvent.COMBO_6);
                        }

                        combo = 0;
                        return;
                    }
                    combo = 0;
                }
            }
        }

        // Check hint
        if (!isSwap && !isMoving) {
            //Check is potential moving
            if (!matchFinding && !waitFinding && mShowHint) {
                //Show hint
                mGameEngine.onGameEvent(GameEvent.START_HINT);
                mShowHint = false;
            }
        }

        // 4. Swap back if no match
        if (isSwap && !isMoving) {
            if (!matchFinding) {
                // Player make an invalid swap
                swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow2][swapCol2]);
                mMoveTile = true;
            } else {
                // Player make valid swap
                mGameEngine.onGameEvent(GameEvent.PLAYER_SWAP);
            }
            isSwap = false;
        }

        // 5. Update tile
        if (!isMoving) {

            // (5.1) Check special fruit
            for (int j = 0; j < mColumn; j++) {
                for (int i = 0; i < mRow; i++) {
                    //Check is special fruit
                    if (tileArray[i][j].special
                            && tileArray[i][j].match != 0
                            && !tileArray[i][j].lock) {
                        // Check direction
                        if (tileArray[i][j].direct == 'H') {
                            //Check special combine
                            if (tileArray[i][j].specialCombine == 'R') {
                                explodeV(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].specialCombine == 'G') {
                                explodeBigH(tileArray, tileArray[i][j]);
                            } else {
                                if (!tileArray[i][j].isExplode)
                                    explodeH(tileArray, tileArray[i][j]);
                            }
                        } else if (tileArray[i][j].direct == 'V') {
                            //Check special combine
                            if (tileArray[i][j].specialCombine == 'R') {
                                explodeH(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].specialCombine == 'G') {
                                explodeBigV(tileArray, tileArray[i][j]);
                            } else {
                                if (!tileArray[i][j].isExplode)
                                    explodeV(tileArray, tileArray[i][j]);
                            }
                        } else if (tileArray[i][j].direct == 'S' && !tileArray[i][j].isExplode) {
                            //Check special combine
                            if (tileArray[i][j].specialCombine == 'B') {
                                explodeBigS(tileArray, tileArray[i][j]);
                            } else {
                                explodeS(tileArray, tileArray[i][j]);
                            }
                        } else if (tileArray[i][j].direct == 'I' && !tileArray[i][j].isExplode) {
                            //Check special combine
                            if (tileArray[i][j].specialCombine == 'T') {
                                transI(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].specialCombine == 'S') {
                                transI(tileArray, tileArray[i][j]);
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
                    //Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        //Check mRow for 3
                        if (tileArray[i][j].kind == tileArray[i][j - 1].kind &&
                                tileArray[i][j].kind == tileArray[i][j + 1].kind) {
                            //Check potential match
                            if (i > 0 && tileArray[i][j].kind == tileArray[i - 1][j - 1].kind
                                    && tileArray[i - 1][j - 1].match > 0) {            //Top left
                                //If tile is coco, do not add
                                if (!tileArray[i][j - 1].special) {
                                    if (i > 1 && tileArray[i - 2][j - 1].kind == tileArray[i][j].kind) {
                                        /* O
                                         * O
                                         * X O O
                                         */
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j - 1], 'L', 1);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j - 1].isUpgrade = true;
                                        tileArray[i - 2][j - 1].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j - 1].direct = 'S';
                                    } else if (i < mRow - 1 && tileArray[i + 1][j - 1].kind == tileArray[i][j].kind) {
                                        /* O
                                         * X O O
                                         * O
                                         */
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j - 1], 'L', 2);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j - 1].isUpgrade = true;
                                        tileArray[i + 1][j - 1].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j - 1].direct = 'S';
                                    }
                                }
                            } else if (i < mRow - 1 && tileArray[i][j].kind == tileArray[i + 1][j - 1].kind
                                    && tileArray[i + 1][j - 1].match > 0) {         //Bottom Left
                                //If tile is coco, do not add
                                if (!tileArray[i][j - 1].special) {
                                    if (i < mRow - 2 && tileArray[i + 2][j - 1].kind == tileArray[i][j].kind) {
                                        /* X O O
                                         * O
                                         * O
                                         */
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j - 1], 'L', 3);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i + 1][j - 1].isUpgrade = true;
                                        tileArray[i + 2][j - 1].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j - 1].direct = 'S';
                                    }
                                }
                            } else if (i > 0 && tileArray[i][j].kind == tileArray[i - 1][j].kind && tileArray[i - 1][j].match > 0) {            //Top Center
                                //If tile is coco, do not add
                                if (!tileArray[i][j].special) {
                                    if (i > 1 && tileArray[i - 2][j].kind == tileArray[i][j].kind) {
                                        /*   O
                                         *   O
                                         * O X O
                                         */
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j], 'C', 1);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j].isUpgrade = true;
                                        tileArray[i - 2][j].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j].direct = 'S';
                                    } else if (i < mRow - 1 && tileArray[i + 1][j].kind == tileArray[i][j].kind) {
                                        /*   O
                                         * O X O
                                         *   O
                                         */
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j], 'C', 2);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j].isUpgrade = true;
                                        tileArray[i + 1][j].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j].direct = 'S';
                                    }
                                }
                            } else if (i < mRow - 1 && tileArray[i][j].kind == tileArray[i + 1][j].kind
                                    && tileArray[i + 1][j].match > 0) {              //Bottom Center
                                //If tile is coco, do not add
                                if (!tileArray[i][j].special) {
                                    if (i < mRow - 2 && tileArray[i + 2][j].kind == tileArray[i][j].kind) {
                                        /* O X O
                                         *   O
                                         *   O
                                         */
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j], 'C', 3);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i + 1][j].isUpgrade = true;
                                        tileArray[i + 2][j].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j].direct = 'S';
                                    }
                                }
                            } else if (i > 0 && tileArray[i][j].kind == tileArray[i - 1][j + 1].kind && tileArray[i - 1][j + 1].match > 0) {           //Top Right
                                //If tile is coco, do not add
                                if (!tileArray[i][j + 1].special) {
                                    if (i > 1 && tileArray[i - 2][j + 1].kind == tileArray[i][j].kind) {
                                        /*     O
                                         *     O
                                         * O O X
                                         */
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j + 1], 'R', 1);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i - 1][j + 1].isUpgrade = true;
                                        tileArray[i - 2][j + 1].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j + 1].direct = 'S';
                                    } else if (i < mRow - 1 && tileArray[i + 1][j + 1].kind == tileArray[i][j].kind) {
                                        /*     O
                                         * O O X
                                         *     O
                                         */
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j + 1], 'R', 2);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i - 1][j + 1].isUpgrade = true;
                                        tileArray[i + 1][j + 1].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j + 1].direct = 'S';
                                    }
                                }
                            } else if (i < mRow - 1 && tileArray[i][j].kind == tileArray[i + 1][j + 1].kind && tileArray[i + 1][j + 1].match > 0) {                 //Bottom Right
                                //If tile is coco, do not add
                                if (!tileArray[i][j + 1].special) {
                                    if (i < mRow - 2 && tileArray[i + 2][j + 1].kind == tileArray[i][j].kind) {
                                        /* O O X
                                         *     O
                                         *     O
                                         */
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2S(tileArray[i][j + 1], 'R', 3);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i + 1][j + 1].isUpgrade = true;
                                        tileArray[i + 2][j + 1].isUpgrade = true;
                                        //Make it special
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
                    //Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        // Check mRow for >= 4
                        if (tileArray[i][j].match > 0 && tileArray[i][j + 1].match > 0
                                && tileArray[i][j].kind == tileArray[i][j + 1].kind
                                && tileArray[i][j].kind == tileArray[i][j + 2].kind
                                && tileArray[i][j].kind == tileArray[i][j + 3].kind) {
                            //Check mRow for 5
                            if (j < mColumn - 4 && tileArray[i][j].kind == tileArray[i][j + 4].kind) {
                                //If tile is already special, do not add
                                if (tileArray[i][j + 2].direct == 'N' && !tileArray[i][j + 2].isUpgrade) {
                                    //Add upgrade animation
                                    mAnimationManager.upgrade2I_h(tileArray[i][j + 2]);
                                    mSoundManager.playSoundForSoundEvent(SoundEvent.ICE_CREAM_UPGRADE);
                                    tileArray[i][j].isUpgrade = true;
                                    tileArray[i][j + 1].isUpgrade = true;
                                    tileArray[i][j + 3].isUpgrade = true;
                                    tileArray[i][j + 4].isUpgrade = true;
                                    //Make it special
                                    tileArray[i][j + 2].direct = 'I';
                                    tileArray[i][j + 2].kind = TileUtils.ICE_CREAM;
                                }
                            } else {
                                //Check is right or left be chosen
                                if ((i == swapRow && j + 1 == swapCol) || (i == swapRow2 && j + 1 == swapCol2)) {
                                    //If tile is already special, do not add
                                    if (tileArray[i][j + 1].direct == 'N' && !tileArray[i][j + 1].isUpgrade) {
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2H_left(tileArray[i][j + 1]);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 2].isUpgrade = true;
                                        tileArray[i][j + 3].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j + 1].direct = 'V';
                                    }
                                } else {
                                    //If tile is already special, do not add
                                    if (tileArray[i][j + 2].direct == 'N' && !tileArray[i][j + 2].isUpgrade) {
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2H_right(tileArray[i][j + 2]);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i][j + 3].isUpgrade = true;
                                        //Make it special
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
                    //Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        //Check mColumn for >= 4
                        if (tileArray[i][j].match > 0 && tileArray[i + 1][j].match > 0
                                && tileArray[i][j].kind == tileArray[i + 1][j].kind
                                && tileArray[i][j].kind == tileArray[i + 2][j].kind
                                && tileArray[i][j].kind == tileArray[i + 3][j].kind) {
                            //Check mRow for 5
                            if (i < mRow - 4 && tileArray[i][j].kind == tileArray[i + 4][j].kind) {
                                //If tile is already special, do not add
                                if (tileArray[i + 2][j].direct == 'N' && !tileArray[i + 2][j].isUpgrade) {
                                    //Add upgrade animation
                                    mAnimationManager.upgrade2I_v(tileArray[i + 2][j]);
                                    mSoundManager.playSoundForSoundEvent(SoundEvent.ICE_CREAM_UPGRADE);
                                    tileArray[i][j].isUpgrade = true;
                                    tileArray[i + 1][j].isUpgrade = true;
                                    tileArray[i + 3][j].isUpgrade = true;
                                    tileArray[i + 4][j].isUpgrade = true;
                                    //Make it special
                                    tileArray[i + 2][j].direct = 'I';
                                    tileArray[i + 2][j].kind = TileUtils.ICE_CREAM;
                                }
                            } else {
                                //Check is top or bottom be chosen
                                if ((i + 1 == swapRow && j == swapCol)
                                        || (i + 1 == swapRow2 && j == swapCol2)) {
                                    //If tile is already special, do not add
                                    if (tileArray[i + 1][j].direct == 'N' && !tileArray[i + 1][j].isUpgrade) {
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2V_top(tileArray[i + 1][j]);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i + 2][j].isUpgrade = true;
                                        tileArray[i + 3][j].isUpgrade = true;
                                        //Make it special
                                        tileArray[i + 1][j].direct = 'H';
                                    }
                                } else {
                                    //If tile is already special, do not add
                                    if (tileArray[i + 2][j].direct == 'N' && !tileArray[i + 2][j].isUpgrade) {
                                        //Add upgrade animation
                                        mAnimationManager.upgrade2V_bottom(tileArray[i + 2][j]);
                                        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_UPGRADE);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i + 1][j].isUpgrade = true;
                                        tileArray[i + 3][j].isUpgrade = true;
                                        //Make it special
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
                            // Add match to whole mColumn if not waiting
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
                            // Check up 3 in mRow, if any mColumn tile is available, then can falling down
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

                                    // Add match to whole mColumn if not waiting
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
                if (!matchFinding && !waitFinding) {
                    playerWin(tileArray);
                    return;
                }
            }

            if (mGameState.isPlayerOutOfMove()) {
                if (!matchFinding && !waitFinding && !isTransf) {
                    playerLoss();
                    return;
                }
            }

            mGameState.onUpdate(tileArray);

            // (5.7) Add score
            for (int j = 0; j < mColumn; j++) {
                for (int i = 0; i < mRow; i++) {
                    if (tileArray[i][j].match != 0 && tileArray[i][j].isValidFruit()) {
                        mGameEngine.onGameEvent(GameEvent.PLAYER_SCORE);
                    }
                }
            }

            // (5.8) Add animation and sound
            for (int j = 0; j < mColumn; j++) {
                for (int i = 0; i < mRow; i++) {
                    //Check is match
                    if (!tileArray[i][j].empty
                            && tileArray[i][j].match != 0
                            && tileArray[i][j].kind != 0
                            && !tileArray[i][j].isAnimate) {

                        // Set isAnimate
                        tileArray[i][j].isAnimate = true;

                        //Check lock
                        if (tileArray[i][j].lock) {
                            explodeLock(advanceArray[i + 1][j], tileArray[i][j]);
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
                                    //Explode cookie
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
                                explodeIce(iceArray[i][j], iceArray2[i][j], tileArray[i][j]);

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
            if (isTransf)
                return;

            // (5.9) Reset
            tile2Top(tileArray);
            tileReset(tileArray);
        }

        // 6. Diagonal swapping
        updateWait(tileArray);
        diagonalSwap(tileArray);

        if (waitFinding) {
            tile2Top(tileArray);
            tileReset(tileArray);
        }

    }

    public void refresh(Tile[][] tileArray) {

        mRefresh = true;
        mWaitingTime = 0;

        //Refresh fruit
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                //Check tile state
                if (!tileArray[i][j].invalid
                        && tileArray[i][j].isFruit()
                        && !tileArray[i][j].special) {
                    //Do not refresh special fruit
                    mAnimationManager.createRefreshAnim(tileArray[i][j].mImage);
                }
            }
        }

    }

    //----------------------------------------------------------------------------------
    // Method control swap
    //----------------------------------------------------------------------------------
    public boolean canPlayerSwap() {
        if (isMoving || matchFinding || waitFinding) {
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
                        tile1.specialCombine = 'R';
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
                        tile2.specialCombine = 'R';
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
    //==================================================================================

    private void playerWin(Tile[][] tileArray) {
        mGameEngine.onGameEvent(GameEvent.PLAYER_REACH_TARGET);

        // Unlock all the tile
        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow; i++) {

                //Check lock
                if (tileArray[i][j].lock) {
                    explodeLock(advanceArray[i + 1][j], tileArray[i][j]);
                }

            }
        }

    }

    private void playerLoss() {
        mGameEngine.onGameEvent(GameEvent.PLAYER_OUT_OF_MOVE);
    }

    private void explodeIce(ImageView ice, ImageView ice2, Tile tile) {
        if (tile.ice == 1) {
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
