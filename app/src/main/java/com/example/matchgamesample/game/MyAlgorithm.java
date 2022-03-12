package com.example.matchgamesample.game;

import android.os.Handler;
import android.widget.ImageView;

import com.example.matchgamesample.effect.AnimationManager;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.game.state.CollectGameState;
import com.example.matchgamesample.game.state.GameState;
import com.example.matchgamesample.game.state.IceGameState;
import com.example.matchgamesample.game.state.ScoreGameState;
import com.example.matchgamesample.game.state.StarGameState;

public class MyAlgorithm {
    private final GameEngine mGameEngine;
    private final int row, column;
    private int fruitMun;
    private final int tileSize;

    private ImageView[][] iceArray, iceArray2;
    private ImageView[][] advanceArray;
    //----------------------------------------------------------------------------------
    // Var to change state of game
    //----------------------------------------------------------------------------------
    public int swapCol, swapRow, swapCol2, swapRow2;
    public boolean isSwap = false, isMoving = false;
    private boolean matchFinding = false, waitFinding = false;
    // Tile's moving control
    private static final int WAITING_TIME = 300;
    private int mWaitingTime = 0;
    public boolean mMoveTile = false;

    // Tile's other state
    private boolean isTransf = false;
    private int combo = 0;
    //==================================================================================
    private GameState mGameState;
    private final AnimationManager animationManager;
    private final Handler mHandler = new Handler();

    public MyAlgorithm(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        row = gameEngine.mLevel.mRow;
        column = gameEngine.mLevel.mColumn;
        fruitMun = gameEngine.mLevel.mFruitNum;
        tileSize = gameEngine.mImageSize;
        animationManager = new AnimationManager(gameEngine);
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

    public void update(Tile[][] tileArray, long elapsedMillis) {

        // Do nothing when waiting event
        if (isTransf)
            return;

        // 1. Find match
        updateWait(tileArray);
        if (!waitFinding) {
            findMatch(tileArray);
        }

        // 2. Moving
        if (mMoveTile) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    tileArray[i][j].onUpdate(elapsedMillis);
                    // Start bouncing animation
                    if(tileArray[i][j].bounce == 1){
                        animationManager.createLightBounceAnim(tileArray[i][j].mImage);
                    } else if(tileArray[i][j].bounce == 2){
                        animationManager.createHeavyBounceAnim(tileArray[i][j].mImage);
                    }
                }
            }
        }

        updateMove(tileArray);

        // 3. Fruit wait
        if (!isMoving && !waitFinding) {
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

        //Check combo
        if (!isMoving) {
            if (matchFinding) {
                combo++;
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

        // 4. Swap back if no match
        if (isSwap && !isMoving) {
            if (!matchFinding) {
                // Player make an invalid swap
                swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow2][swapCol2]);
                mMoveTile = true;
            } else {
                // Player make valid swap
                mGameEngine.onGameEvent(GameEvent.PLAYER_SWAP);
                //Restart hint
                //hint.stopHint();
                //showHint = true;
            }
            isSwap = false;
        }

        // 5. Update tile
        if (!isMoving) {

            // (5.1) Check special fruit
            for (int j = 0; j < column; j++) {
                for (int i = 0; i < row; i++) {
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
            for (int i = 0; i < row; i++) {
                for (int j = 1; j < column - 1; j++) {
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
                                        animationManager.upgrade2S(tileArray[i][j - 1], 'L', 1);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j - 1].isUpgrade = true;
                                        tileArray[i - 2][j - 1].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j - 1].direct = 'S';
                                    } else if (i < row - 1 && tileArray[i + 1][j - 1].kind == tileArray[i][j].kind) {
                                        /* O
                                         * X O O
                                         * O
                                         */
                                        //Add upgrade animation
                                        animationManager.upgrade2S(tileArray[i][j - 1], 'L', 2);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j - 1].isUpgrade = true;
                                        tileArray[i + 1][j - 1].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j - 1].direct = 'S';
                                    }
                                }
                            } else if (i < row - 1 && tileArray[i][j].kind == tileArray[i + 1][j - 1].kind
                                    && tileArray[i + 1][j - 1].match > 0) {         //Bottom Left
                                //If tile is coco, do not add
                                if (!tileArray[i][j - 1].special) {
                                    if (i < row - 2 && tileArray[i + 2][j - 1].kind == tileArray[i][j].kind) {
                                        /* X O O
                                         * O
                                         * O
                                         */
                                        //Add upgrade animation
                                        animationManager.upgrade2S(tileArray[i][j - 1], 'L', 3);
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
                                        animationManager.upgrade2S(tileArray[i][j], 'C', 1);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j].isUpgrade = true;
                                        tileArray[i - 2][j].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j].direct = 'S';
                                    } else if (i < row - 1 && tileArray[i + 1][j].kind == tileArray[i][j].kind) {
                                        /*   O
                                         * O X O
                                         *   O
                                         */
                                        //Add upgrade animation
                                        animationManager.upgrade2S(tileArray[i][j], 'C', 2);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i - 1][j].isUpgrade = true;
                                        tileArray[i + 1][j].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j].direct = 'S';
                                    }
                                }
                            } else if (i < row - 1 && tileArray[i][j].kind == tileArray[i + 1][j].kind
                                    && tileArray[i + 1][j].match > 0) {              //Bottom Center
                                //If tile is coco, do not add
                                if (!tileArray[i][j].special) {
                                    if (i < row - 2 && tileArray[i + 2][j].kind == tileArray[i][j].kind) {
                                        /* O X O
                                         *   O
                                         *   O
                                         */
                                        //Add upgrade animation
                                        animationManager.upgrade2S(tileArray[i][j], 'C', 3);
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
                                        animationManager.upgrade2S(tileArray[i][j + 1], 'R', 1);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i - 1][j + 1].isUpgrade = true;
                                        tileArray[i - 2][j + 1].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j + 1].direct = 'S';
                                    } else if (i < row - 1 && tileArray[i + 1][j + 1].kind == tileArray[i][j].kind) {
                                        /*     O
                                         * O O X
                                         *     O
                                         */
                                        //Add upgrade animation
                                        animationManager.upgrade2S(tileArray[i][j + 1], 'R', 2);
                                        tileArray[i][j - 1].isUpgrade = true;
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i - 1][j + 1].isUpgrade = true;
                                        tileArray[i + 1][j + 1].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j + 1].direct = 'S';
                                    }
                                }
                            } else if (i < row - 1 && tileArray[i][j].kind == tileArray[i + 1][j + 1].kind && tileArray[i + 1][j + 1].match > 0) {                 //Bottom Right
                                //If tile is coco, do not add
                                if (!tileArray[i][j + 1].special) {
                                    if (i < row - 2 && tileArray[i + 2][j + 1].kind == tileArray[i][j].kind) {
                                        /* O O X
                                         *     O
                                         *     O
                                         */
                                        //Add upgrade animation
                                        animationManager.upgrade2S(tileArray[i][j + 1], 'R', 3);
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
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column - 3; j++) {
                    //Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        // Check mRow for >= 4
                        if (tileArray[i][j].match > 0 && tileArray[i][j + 1].match > 0
                                && tileArray[i][j].kind == tileArray[i][j + 1].kind
                                && tileArray[i][j].kind == tileArray[i][j + 2].kind
                                && tileArray[i][j].kind == tileArray[i][j + 3].kind) {
                            //Check mRow for 5
                            if (j < column - 4 && tileArray[i][j].kind == tileArray[i][j + 4].kind) {
                                //Add upgrade animation
                                animationManager.upgrade2I_h(tileArray[i][j + 2]);
                                tileArray[i][j].isUpgrade = true;
                                tileArray[i][j + 1].isUpgrade = true;
                                tileArray[i][j + 3].isUpgrade = true;
                                tileArray[i][j + 4].isUpgrade = true;
                                //Make it special
                                tileArray[i][j + 2].direct = 'I';
                                tileArray[i][j + 2].kind = TileUtils.ICE_CREAM;
                            } else {
                                //Check is right or left be chosen
                                if ((i == swapRow && j + 1 == swapCol) || (i == swapRow2 && j + 1 == swapCol2)) {
                                    //If tile is already special, do not add
                                    if (tileArray[i][j + 1].direct == 'N' && !tileArray[i][j + 1].isUpgrade) {
                                        //Add upgrade animation
                                        animationManager.upgrade2H_left(tileArray[i][j + 1]);
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
                                        animationManager.upgrade2H_right(tileArray[i][j + 2]);
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
            for (int j = 0; j < column; j++) {
                for (int i = 0; i < row - 3; i++) {
                    //Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        //Check mColumn for >= 4
                        if (tileArray[i][j].match > 0 && tileArray[i + 1][j].match > 0
                                && tileArray[i][j].kind == tileArray[i + 1][j].kind
                                && tileArray[i][j].kind == tileArray[i + 2][j].kind
                                && tileArray[i][j].kind == tileArray[i + 3][j].kind) {
                            //Check mRow for 5
                            if (i < row - 4 && tileArray[i][j].kind == tileArray[i + 4][j].kind) {
                                //Add upgrade animation
                                animationManager.upgrade2I_v(tileArray[i + 2][j]);
                                tileArray[i][j].isUpgrade = true;
                                tileArray[i + 1][j].isUpgrade = true;
                                tileArray[i + 3][j].isUpgrade = true;
                                tileArray[i + 4][j].isUpgrade = true;
                                //Make it special
                                tileArray[i + 2][j].direct = 'I';
                                tileArray[i + 2][j].kind = TileUtils.ICE_CREAM;
                            } else {
                                //Check is top or bottom be chosen
                                if ((i + 1 == swapRow && j == swapCol)
                                        || (i + 1 == swapRow2 && j == swapCol2)) {
                                    //If tile is already special, do not add
                                    if (tileArray[i + 1][j].direct == 'N' && !tileArray[i + 1][j].isUpgrade) {
                                        //Add upgrade animation
                                        animationManager.upgrade2V_top(tileArray[i + 1][j]);
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
                                        animationManager.upgrade2V_bottom(tileArray[i + 2][j]);
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
            for (int i = row - 1; i >= 0; i--) {
                for (int j = 0; j < column; j++) {

                    // Check invalid tile match
                    if (tileArray[i][j].invalid && tileArray[i][j].match != 0) {

                        if (i == 0) {
                            // Add match to whole mColumn if not waiting
                            for (int m = i + 1; m < row; m++) {
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

                                if (n < 0 || n >= column)
                                    continue;

                                // If find tile can fill
                                if (!tileArray[i - 1][n].invalid || (tileArray[i - 1][n].tube && n == j)) {

                                    /* The tile can only go though tube vertically from bottom
                                     *      | |   <-- tube
                                     *      | |
                                     *     x o x  <-- tile (No diagonal swapping)
                                     */

                                    // Add match to whole mColumn if not waiting
                                    for (int m = i + 1; m < row; m++) {
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
                    playerWin();
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
            for (int j = 0; j < column; j++) {
                for (int i = 0; i < row; i++) {
                    if (tileArray[i][j].match != 0 && tileArray[i][j].isFruit()) {
                        mGameEngine.onGameEvent(GameEvent.PLAYER_SCORE);
                    }
                }
            }

            // (5.8) Add animation
            for (int j = 0; j < column; j++) {
                for (int i = 0; i < row; i++) {
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
                                animationManager.explodeStarFish(tileArray[i][j]);
                            } else {
                                tileArray[i][j].match = 0;
                            }
                            continue;
                        }

                        // Check breakable
                        if (tileArray[i][j].kind == TileUtils.CRACKER) {
                            // Explode cracker
                            animationManager.explodeCracker(tileArray[i][j]);
                        } else if (tileArray[i][j].kind == TileUtils.COOKIE) {
                            // Check how many layer
                            switch (tileArray[i][j].layer) {
                                case 0:
                                    //Explode cookie
                                    tileArray[i][j].invalid = false;
                                    animationManager.explodeCookie(tileArray[i][j]);
                                    break;
                                case 1:
                                case 2:
                                case 3:
                                    tileArray[i][j].match = 0;
                                    tileArray[i][j].layer--;
                                    animationManager.explodeCookieLayer(tileArray[i][j]);
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
                            if (!tileArray[i][j].isUpgrade)
                                animationManager.explodeFruit(tileArray[i][j]);
                            // Show score
                            animationManager.createScore(tileArray[i][j]);
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

    private void playerWin() {
        mGameEngine.onGameEvent(GameEvent.PLAYER_REACH_TARGET);
    }

    private void playerLoss() {
        mGameEngine.onGameEvent(GameEvent.PLAYER_OUT_OF_MOVE);
    }

    //----------------------------------------------------------------------------------
    // Method of Algorithm
    //----------------------------------------------------------------------------------
    private void findMatch(Tile[][] tileArray) {

        // Check match 3 in mColumn
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row - 2; i++) {
                //Check state
                if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                    // Check is there a sequence
                    if ((tileArray[i][j].kind == tileArray[i + 1][j].kind) &&
                            (tileArray[i][j].kind == tileArray[i + 2][j].kind)) {
                        //Add match and explode around
                        for (int n = 0; n <= 2; n++) {
                            tileArray[i + n][j].match = 1;
                            explodeAround(tileArray, tileArray[i + n][j]);
                        }

                    }
                }
            }
        }

        // Check match 3 in mRow
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column - 2; j++) {
                //Check state
                if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {

                    // Check is there a sequence
                    if ((tileArray[i][j].kind == tileArray[i][j + 1].kind) &&
                            (tileArray[i][j].kind == tileArray[i][j + 2].kind)) {
                        //Add match and explode around
                        for (int n = 0; n <= 2; n++) {
                            tileArray[i][j + n].match = 1;
                            explodeAround(tileArray, tileArray[i][j + n]);
                        }

                    }
                }
            }
        }
    }

    private void tile2Top(Tile[][] tileArray) {
        for (int j = 0; j < column; j++) {
            for (int i = row - 1; i >= 0; i--) {
                //Check match
                if (tileArray[i][j].match != 0) {
                    //Swap tile
                    for (int n = i - 1; n >= 0; n--) {
                        //If empty tube do not swap
                        if (tileArray[n][j].match == 0 && !tileArray[n][j].tube) {

                            if (tileArray[n][j].invalid) {
                                tileArray[i][j].kind = 0;
                                tileArray[i][j].match = 0;
                                tileArray[i][j].wait = 1;
                                break;
                            }

                            swap(tileArray, tileArray[n][j], tileArray[i][j]);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void tileReset(Tile[][] tileArray) {
        for (int j = 0; j < column; j++) {
            for (int i = row - 1, n = 1; i >= 0; i--) {

                // Breakable may cause 0 match
                if (tileArray[i][j].isAnimate) {
                    tileArray[i][j].isAnimate = false;
                }

                if (tileArray[i][j].match != 0) {

                    // Go to top
                    tileArray[i][j].y = -tileSize * n++;
                    tileArray[i][j].x = tileArray[i][j].col * tileSize;
                    // Reset fruit
                    tileArray[i][j].reset();

                }
            }
        }
    }

    private void diagonalSwap(Tile[][] tileArray) {

        // First, check wait = 2 top down
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {

                //Find waiting tile
                if (tileArray[i][j].wait != 0) {

                    //Look up
                    for (int n = i - 1; n >= 0; n--) {

                        //Find obstacle
                        if ((tileArray[n][j].invalid && !tileArray[n][j].tube) || tileArray[n][j].wait == 2) {

                            //Check is blocked
                            if ((j == 0 || tileArray[n][j - 1].invalid || tileArray[n][j - 1].wait == 2)
                                    && (j == column - 1 || tileArray[n][j + 1].invalid || tileArray[n][j + 1].wait == 2)) {
                                tileArray[i][j].wait = 2;
                                break;
                            } else if (tileArray[n + 1][j].tube) {
                                tileArray[i][j].wait = 2;
                                break;
                            } else {
                                tileArray[i][j].wait = 1;
                            }

                            break;
                        }
                    }
                }
            }
        }

        // Then, diagonal swap bottom up
        for (int j = 0; j < column; j++) {
            for (int i = row - 1; i >= 0; i--) {

                //Find waiting tile
                if (tileArray[i][j].wait != 0) {

                    //Look up
                    outer:
                    for (int n = i - 1; n >= 0; n--) {

                        //Find obstacle
                        if ((tileArray[n][j].invalid && !tileArray[n][j].tube) || tileArray[n][j].wait == 2) {

                            if (tileArray[n + 1][j].tube) {
                                /* The tile can only go though tube vertically from top
                                 *     x o x  <-- tile (No diagonal swapping)
                                 *      | |
                                 *      | |   <-- tube
                                 */
                                break;
                            }

                            //Look right
                            for (int m = n; m >= 0; m--) {
                                if (j == column - 1 || tileArray[m][j + 1].invalid || (tileArray[m][j + 1].y - tileArray[m][j + 1].row * tileSize != 0)) {
                                    break;
                                } else if (tileArray[m][j + 1].wait == 0) {
                                    /*    O X
                                     *
                                     *    O
                                     */
                                    tileArray[i][j].match++;
                                    tileArray[i][j].wait = 0;
                                    tileArray[m][j + 1].diagonal = n;
                                    swap(tileArray, tileArray[m][j + 1], tileArray[i][j]);
                                    break outer;
                                }
                            }

                            //Look left
                            for (int m = n; m >= 0; m--) {
                                if (j == 0 || tileArray[m][j - 1].invalid || (tileArray[m][j - 1].y - tileArray[m][j - 1].row * tileSize != 0)) {
                                    break;
                                } else if (tileArray[m][j - 1].wait == 0) {
                                    /*  X O
                                     *
                                     *    O
                                     */
                                    tileArray[i][j].match++;
                                    tileArray[i][j].wait = 0;
                                    tileArray[m][j - 1].diagonal = n;
                                    swap(tileArray, tileArray[m][j - 1], tileArray[i][j]);
                                    break outer;
                                }
                            }

                            break;
                        }
                    }
                }
            }
        }
    }

    private void updateMove(Tile[][] tileArray) {
        isMoving = false;
        outer:
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (tileArray[i][j].isMoving()) {
                    isMoving = true;
                    break outer;
                }
            }
        }
    }

    private void updateWait(Tile[][] tileArray) {
        waitFinding = false;
        outer:
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (tileArray[i][j].wait == 1) {
                    waitFinding = true;
                    break outer;
                }
            }
        }
    }

    private void updateMatch(Tile[][] tileArray) {
        matchFinding = false;
        outer:
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (tileArray[i][j].match != 0) {
                    matchFinding = true;
                    break outer;
                }
            }
        }
    }
    //==================================================================================

    //----------------------------------------------------------------------------------
    // Method control swap
    //----------------------------------------------------------------------------------
    public void swap(Tile[][] tileArray, Tile tile1, Tile tile2) {
        if (tile1.invalid || tile2.invalid)
            return;

        //Exchange mRow
        int temp_row = tile1.row;
        tile1.row = tile2.row;
        tile2.row = temp_row;

        //Exchange mColumn
        int temp_col = tile1.col;
        tile1.col = tile2.col;
        tile2.col = temp_col;

        //If ice do not swap
        if (tile1.ice != 0 || tile2.ice != 0) {
            int temp = tile1.ice;
            tile1.ice = tile2.ice;
            tile2.ice = temp;
        }

        //If entryPoint do not swap
        if (tile1.entryPoint || tile2.entryPoint) {
            boolean temp = tile1.entryPoint;
            tile1.entryPoint = tile2.entryPoint;
            tile2.entryPoint = temp;
        }

        //Exchange tile
        tileArray[tile1.row][tile1.col] = tile1;
        tileArray[tile2.row][tile2.col] = tile2;

    }

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

    //----------------------------------------------------------------------------------
    // Method of special fruit
    //----------------------------------------------------------------------------------
    private void explodeH(Tile[][] tileArray, Tile tile) {
        //Mark isExplode
        tile.isExplode = true;
        //Add horizontal flash
        animationManager.createHorizontalFlash(tile);
        //Add match in mRow
        for (int j = 0; j < column; j++) {
            //Check is empty fruit
            if (!tileArray[tile.row][j].empty) {

                // Add match
                tileArray[tile.row][j].match++;

                //Check fruit is special in mRow
                if (tileArray[tile.row][j].special) {
                    //Check is explode
                    if (!tileArray[tile.row][j].isExplode && !tileArray[tile.row][j].lock) {
                        //Check direct
                        if (tileArray[tile.row][j].direct == 'V') {
                            explodeV(tileArray, tileArray[tile.row][j]);
                        } else if (tileArray[tile.row][j].direct == 'H') {
                            explodeH(tileArray, tileArray[tile.row][j]);
                        } else if (tileArray[tile.row][j].direct == 'S') {
                            explodeS(tileArray, tileArray[tile.row][j]);
                        } else if (tileArray[tile.row][j].direct == 'I') {
                            explodeI(tileArray, tileArray[tile.row][j]);
                        }
                    }
                }
            }
        }
    }

    private void explodeV(Tile[][] tileArray, Tile tile) {
        //Mark isExplode
        tile.isExplode = true;
        //Add vertical flash
        animationManager.createVerticalFlash(tile);
        //Add match in mColumn
        for (int i = 0; i < row; i++) {
            //Check is empty fruit
            if (!tileArray[i][tile.col].empty) {

                // Add match
                tileArray[i][tile.col].match++;

                //Check fruit is special in mColumn
                if (tileArray[i][tile.col].special) {
                    //Check is explode
                    if (!tileArray[i][tile.col].isExplode && !tileArray[i][tile.col].lock) {
                        //Check direct
                        if (tileArray[i][tile.col].direct == 'H') {
                            explodeH(tileArray, tileArray[i][tile.col]);
                        } else if (tileArray[i][tile.col].direct == 'V') {
                            explodeV(tileArray, tileArray[i][tile.col]);
                        } else if (tileArray[i][tile.col].direct == 'S') {
                            explodeS(tileArray, tileArray[i][tile.col]);
                        } else if (tileArray[i][tile.col].direct == 'I') {
                            explodeI(tileArray, tileArray[i][tile.col]);
                        }
                    }
                }
            }
        }
    }

    private void explodeS(Tile[][] tileArray, Tile tile) {
        //Mark isExplode
        tile.isExplode = true;
        //Add square flash
        animationManager.createSquareFlash(tile);
        //Add match in square
        for (int r = tile.row - 1; r <= tile.row + 1; r++) {
            for (int c = tile.col - 1; c <= tile.col + 1; c++) {

                if (r < 0 || r >= row || c < 0 || c >= column)
                    continue;

                //Check is empty fruit
                if (!tileArray[r][c].empty) {

                    // Add match
                    tileArray[r][c].match++;

                    //Check fruit is special in square
                    if (tileArray[r][c].special) {
                        //Check is explode
                        if (!tileArray[r][c].isExplode && !tileArray[r][c].lock) {
                            //Check direct
                            if (tileArray[r][c].direct == 'H') {
                                explodeH(tileArray, tileArray[r][c]);
                            } else if (tileArray[r][c].direct == 'V') {
                                explodeV(tileArray, tileArray[r][c]);
                            } else if (tileArray[r][c].direct == 'S') {
                                explodeS(tileArray, tileArray[r][c]);
                            } else if (tileArray[r][c].direct == 'I') {
                                explodeI(tileArray, tileArray[r][c]);
                            }
                        }
                    }
                }
            }
        }
    }

    private void explodeBigH(Tile[][] tileArray, Tile tile) {
        int row = tile.row;
        int col = tile.col;

        if (row == 0) {
            explodeH(tileArray, tileArray[row][col]);
            explodeH(tileArray, tileArray[row + 1][col]);
        } else if (row == this.row - 1) {
            explodeH(tileArray, tileArray[row - 1][col]);
            explodeH(tileArray, tileArray[row][col]);
        } else {
            explodeH(tileArray, tileArray[row - 1][col]);
            explodeH(tileArray, tileArray[row][col]);
            explodeH(tileArray, tileArray[row + 1][col]);
        }
        animationManager.createShakingAnim_small();
        animationManager.createExplodeWave_small(tileArray[row][col]);
        animationManager.createSquareFlash(tileArray[row][col]);
    }

    private void explodeBigV(Tile[][] tileArray, Tile tile) {
        int row = tile.row;
        int col = tile.col;

        if (col == 0) {
            explodeV(tileArray, tileArray[row][col]);
            explodeV(tileArray, tileArray[row][col + 1]);
        } else if (col == this.column - 1) {
            explodeV(tileArray, tileArray[row][col - 1]);
            explodeV(tileArray, tileArray[row][col]);
        } else {
            explodeV(tileArray, tileArray[row][col - 1]);
            explodeV(tileArray, tileArray[row][col]);
            explodeV(tileArray, tileArray[row][col + 1]);
        }
        animationManager.createShakingAnim_small();
        animationManager.createExplodeWave_small(tileArray[row][col]);
        animationManager.createSquareFlash(tileArray[row][col]);
    }

    private void explodeBigS(Tile[][] tileArray, Tile tile) {
        int row = tile.row;
        int col = tile.col;
        //Mark isExplode
        tile.isExplode = true;
        //Add square flash
        animationManager.createShakingAnim_small();
        animationManager.createSquareFlash_big(tile);
        //Add match in 5x5 square
        for (int i = row - 2; i <= row + 2; i++) {
            for (int j = col - 2; j <= col + 2; j++) {
                //Check index not out of boarder
                if (i < 0 || i >= row || j < 0 || j >= col)
                    continue;

                //Check is empty fruit
                if (!tileArray[i][j].empty) {

                    // Add match
                    tileArray[i][j].match++;

                    //Check fruit is special in square
                    if (tileArray[i][j].special) {
                        //Check is explode
                        if (!tileArray[i][j].isExplode && !tileArray[i][j].lock) {
                            //Check direct
                            if (tileArray[i][j].direct == 'H') {
                                explodeH(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].direct == 'V') {
                                explodeV(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].direct == 'S') {
                                explodeS(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].direct == 'I') {
                                explodeI(tileArray, tileArray[i][j]);
                            }
                        }
                    }
                }
            }
        }
    }

    private void explodeI(Tile[][] tileArray, Tile tile) {
        // Mark isExplode
        tile.isExplode = true;

        // Get mTarget fruit
        int target = 0;
        if (tile.iceCreamTarget == 0) {
            // Get a random fruit
            target = TileUtils.FRUITS[(int) (Math.random() * fruitMun)];
        } else {
            target = tile.iceCreamTarget;
        }

        // Check same fruit kind
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {

                // Check state
                if (!tileArray[i][j].empty && !tileArray[i][j].isExplode && tileArray[i][j].kind != 0) {

                    //Check is mTarget fruit
                    if (target == tileArray[i][j].kind) {

                        // Add lightning animation
                        animationManager.createLightning(tile, tileArray[i][j]);
                        animationManager.createLightning_fruit(tileArray[i][j], false);

                        // Add match to mTarget and explode around
                        tileArray[i][j].match = 1;
                        explodeAround(tileArray, tileArray[i][j]);

                        // Check fruit is special
                        if (tileArray[i][j].special && !tileArray[i][j].isExplode && !tileArray[i][j].lock) {
                            if (tileArray[i][j].direct == 'H') {
                                explodeH(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].direct == 'V') {
                                explodeV(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].direct == 'S') {
                                explodeS(tileArray, tileArray[i][j]);
                            }
                        }
                    }
                }
            }
        }
    }

    private void explodeBigI(Tile[][] tileArray, Tile tile) {
        //Mark isExplode
        tile.isExplode = true;
        //Add animation
        animationManager.createShakingAnim();
        animationManager.createExplodeWave(tile);
        //Check same fruit kind
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].isExplode) {

                    //Add lightning animation
                    animationManager.createLightning(tile, tileArray[i][j]);
                    animationManager.createLightning_fruit(tileArray[i][j], false);

                    // Add match
                    tileArray[i][j].match++;

                    //Check fruit is special
                    if (tileArray[i][j].special && !tileArray[i][j].lock) {
                        if (tileArray[i][j].direct == 'H') {
                            explodeH(tileArray, tileArray[i][j]);
                        } else if (tileArray[i][j].direct == 'V') {
                            explodeV(tileArray, tileArray[i][j]);
                        } else if (tileArray[i][j].direct == 'S') {
                            explodeS(tileArray, tileArray[i][j]);
                        }
                    }
                }
            }
        }
    }

    private void transI(Tile[][] tileArray, Tile tile) {

        // Transform flag
        transfWait();

        // Let hint delay
        //hintWait();

        // Mark isExplode
        tile.isExplode = true;

        // Check same fruit kind
        int target = tile.iceCreamTarget;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

                // Check state
                if (!tileArray[i][j].empty && !tileArray[i][j].isExplode && tileArray[i][j].kind != 0) {

                    // Check ice cream mTarget
                    if ((target == tileArray[i][j].kind) && !tileArray[i][j].special) {

                        // Add lightning animation
                        animationManager.createLightning(tile, tileArray[i][j]);
                        animationManager.createLightning_fruit(tileArray[i][j], true);

                        // Set direct
                        if (tile.specialCombine == 'T') {
                            tileArray[i][j].special = true;
                            tileArray[i][j].direct = (Math.random() > 0.5 ? 'H' : 'V');
                        } else {
                            tileArray[i][j].special = true;
                            tileArray[i][j].direct = 'S';
                        }
                    }
                }
            }
        }
    }
    //==================================================================================

    private void transfWait() {
        //Bug is ice cream's wait may cause error
        isTransf = true;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isTransf = false;
            }
        }, 1200);
    }

    private void explodeAround(Tile[][] tileArray, Tile tile) {
        // Do nothing if locked
        if (tile.lock)
            return;

        int col_temp = tile.col;
        int row_temp = tile.row;

        // Check up
        if (row_temp > 0 && tileArray[row_temp - 1][col_temp].breakable
                && !tileArray[row_temp - 1][col_temp].lock) {
            tileArray[row_temp - 1][col_temp].match++;
        }

        // Check down
        if (row_temp < row - 1 && tileArray[row_temp + 1][col_temp].breakable
                && !tileArray[row_temp + 1][col_temp].lock) {
            tileArray[row_temp + 1][col_temp].match++;
        }

        // Check left
        if (col_temp > 0 && tileArray[row_temp][col_temp - 1].breakable
                && !tileArray[row_temp][col_temp - 1].lock) {
            tileArray[row_temp][col_temp - 1].match++;
        }

        // Check right
        if (col_temp < column - 1 && tileArray[row_temp][col_temp + 1].breakable
                && !tileArray[row_temp][col_temp + 1].lock) {
            tileArray[row_temp][col_temp + 1].match++;
        }

    }

    private void explodeIce(ImageView ice, ImageView ice2, Tile tile) {
        if (tile.ice == 1) {
            if (ice != null) {
                animationManager.explodeIce(ice, 10);
            }
        } else if (tile.ice == 2) {
            if (ice2 != null) {
                animationManager.explodeIce(ice2, 5);
            }
        }
        tile.ice--;

    }

    private void explodeLock(ImageView lock, Tile tile) {
        tile.match = 0;
        tile.lock = false;
        if (tile.kind != TileUtils.COOKIE)
            tile.invalid = false;
        animationManager.explodeLock(lock, tile);
    }

}
