package com.example.matchgamesample.game;

import android.os.Handler;
import android.widget.ImageView;

import com.example.matchgamesample.effect.AnimationManager;
import com.example.matchgamesample.engine.GameEngine;

public class MyAlgorithm {
    private final int row, column;
    private int fruitMun;
    private final int tileSize;

    private ImageView[][] iceArray, iceArray2;
    private ImageView[][] advanceArray;
    //----------------------------------------------------------------------------------
    // Var to change state of game
    //----------------------------------------------------------------------------------
    public boolean isSwap = false, isSwapBack = false, isMoving = false;
    public char direction = 'N';
    public int swapCol, swapRow, swapCol2, swapRow2;
    private int isWait = 1;
    private boolean matchFinding = false, waitFinding = false;
    // Tile's moving SPEED
    private int SPEED = 15;
    private int WAIT_TIME = 300;
    //==================================================================================
    private final AnimationManager animationManager;
    private final Handler mHandler = new Handler();

    public MyAlgorithm(GameEngine gameEngine) {
        row = gameEngine.mLevel.row;
        column = gameEngine.mLevel.column;
        fruitMun = gameEngine.mLevel.fruitNum;
        tileSize = gameEngine.mImageSize;
        animationManager = new AnimationManager(gameEngine);
    }

    public void setIceArray(ImageView[][] iceArray, ImageView[][] iceArray2) {
        this.iceArray = iceArray;
        this.iceArray2 = iceArray2;
    }

    public void setAdvanceArray(ImageView[][] advanceArray) {
        this.advanceArray = advanceArray;
    }

    public void update(Tile[][] tileArray) {
        updateWait(tileArray);
        if (!waitFinding) {
            findMatch(tileArray);
        }

        isMoving = false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int diff_x = 0, diff_y = 0;
                for (int n = 0; n < SPEED; n++) {
                    diff_x = tileArray[i][j].x - tileArray[i][j].col * tileSize;
                    diff_y = tileArray[i][j].y - tileArray[i][j].row * tileSize;
                    if (diff_x != 0) {
                        if (isWait == 3) {
                            // Check diagonal swapping position
                            if (tileArray[i][j].y >= tileArray[i][j].diagonal * tileSize)
                                //Go left or right
                                tileArray[i][j].x -= diff_x / Math.abs(diff_x);
                        }
                    } else {
                        tileArray[i][j].diagonal = 0;
                    }
                    if (diff_y != 0) {
                        if (diff_y < 0) {
                            if (isWait == 3) {
                                //Measure dropping distance
                                if (tileArray[i][j].bounce == 0) {
                                    if (diff_y <= -tileSize * 4) {
                                        tileArray[i][j].bounce = 2;
                                    } else {
                                        tileArray[i][j].bounce = 1;
                                    }
                                }
                                //Go down
                                tileArray[i][j].y -= diff_y / Math.abs(diff_y);
                                //Set bounce when stop falling
                                if (tileArray[i][j].y == tileArray[i][j].row * tileSize) {
                                    if (!isSwap && !isSwapBack) {
                                        if (tileArray[i][j].bounce == 1) {
                                            animationManager.createLightBounceAnim(tileArray[i][j].mImage);
                                        } else {
                                            animationManager.createHeavyBounceAnim(tileArray[i][j].mImage);
                                        }
                                        tileArray[i][j].bounce = 0;
                                    }
                                }
                            }
                        } else {
                            //Go up
                            tileArray[i][j].y -= diff_y / Math.abs(diff_y);
                        }
                    }
                }
            }
        }

        updateMove(tileArray);

        if (!isMoving)
            isSwapBack = false;

        if (!isMoving && !waitFinding) {
            isWait = 1;
        } else {
            //Check is player swapping
            if (!isSwap && !isSwapBack) {
                if (isWait == 1) {
                    tileWait();
                    isWait = 2;
                }
            } else {
                //Player is swapping
                isWait = 3;
            }
        }

        updateMatch(tileArray);

        //Swap back if no match
        if (isSwap && !isMoving) {
            if (!matchFinding) {
                switch (direction) {
                    case 'U':
                        swapRow2 = swapRow - 1;
                        swapCol2 = swapCol;
                        swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow2][swapCol2]);
                        break;
                    case 'D':
                        swapRow2 = swapRow + 1;
                        swapCol2 = swapCol;
                        swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow2][swapCol2]);
                        break;
                    case 'L':
                        swapRow2 = swapRow;
                        swapCol2 = swapCol - 1;
                        swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow2][swapCol2]);
                        break;
                    case 'R':
                        swapRow2 = swapRow;
                        swapCol2 = swapCol + 1;
                        swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow2][swapCol2]);
                        break;
                }
                direction = 'N';
                isSwapBack = true;
            } else {
                //Player move
                //reduceMove();
                //Restart hint
                //hint.stopHint();
                //showHint = true;
            }
            isSwap = false;
        }

        if (!isMoving) {

            //(5.2) Add square special fruit
            for (int i = 0; i < row; i++) {
                for (int j = 1; j < column - 1; j++) {
                    //Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        //Check row for 3
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
                                        tileArray[i][j - 1].match = 0;
                                        tileArray[i][j - 1].special = true;
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
                                        tileArray[i][j - 1].match = 0;
                                        tileArray[i][j - 1].special = true;
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
                                        tileArray[i][j - 1].match = 0;
                                        tileArray[i][j - 1].special = true;
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
                                        tileArray[i][j].match = 0;
                                        tileArray[i][j].special = true;
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
                                        tileArray[i][j].match = 0;
                                        tileArray[i][j].special = true;
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
                                        tileArray[i][j].match = 0;
                                        tileArray[i][j].special = true;
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
                                        tileArray[i][j + 1].match = 0;
                                        tileArray[i][j + 1].special = true;
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
                                        tileArray[i][j + 1].match = 0;
                                        tileArray[i][j + 1].special = true;
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
                                        tileArray[i][j + 1].match = 0;
                                        tileArray[i][j + 1].special = true;
                                        tileArray[i][j + 1].direct = 'S';
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //(5.3) Add vertical special fruit
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column - 3; j++) {
                    //Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        // Check row for >= 4
                        if (tileArray[i][j].match > 0 && tileArray[i][j + 1].match > 0
                                && tileArray[i][j].kind == tileArray[i][j + 1].kind
                                && tileArray[i][j].kind == tileArray[i][j + 2].kind
                                && tileArray[i][j].kind == tileArray[i][j + 3].kind) {
                            //Check row for 5
                            if (j < column - 4 && tileArray[i][j].kind == tileArray[i][j + 4].kind) {
                                //Add upgrade animation
                                animationManager.upgrade2I_h(tileArray[i][j + 2]);
                                tileArray[i][j].isUpgrade = true;
                                tileArray[i][j + 1].isUpgrade = true;
                                tileArray[i][j + 3].isUpgrade = true;
                                tileArray[i][j + 4].isUpgrade = true;
                                //Make it special
                                tileArray[i][j + 2].match = 0;     //Unlock won't detect
                                tileArray[i][j + 2].special = true;
                                tileArray[i][j + 2].direct = 'I';
                                tileArray[i][j + 2].kind = TileID.ICE_CREAM;
                            } else {
                                //Check is right or left be chosen
                                if ((i == swapRow && j + 1 == swapCol) || (i == swapRow2 && j + 1 == swapCol2)) {
                                    //If tile is already special, do not add
                                    if (!tileArray[i][j + 1].special && !tileArray[i][j + 1].isUpgrade) {
                                        //Add upgrade animation
                                        animationManager.upgrade2H_left(tileArray[i][j + 1]);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 2].isUpgrade = true;
                                        tileArray[i][j + 3].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j + 1].match = 0;     //Unlock won't detect
                                        tileArray[i][j + 1].special = true;
                                        tileArray[i][j + 1].direct = 'V';
                                    }
                                } else {
                                    //If tile is already special, do not add
                                    if (!tileArray[i][j + 2].special && !tileArray[i][j + 2].isUpgrade) {
                                        //Add upgrade animation
                                        animationManager.upgrade2H_right(tileArray[i][j + 2]);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i][j + 1].isUpgrade = true;
                                        tileArray[i][j + 3].isUpgrade = true;
                                        //Make it special
                                        tileArray[i][j + 2].match = 0;     //Unlock won't detect
                                        tileArray[i][j + 2].special = true;
                                        tileArray[i][j + 2].direct = 'V';
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //(5.4) Add horizontal special fruit
            for (int j = 0; j < column; j++) {
                for (int i = 0; i < row - 3; i++) {
                    //Check state
                    if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                        //Check column for >= 4
                        if (tileArray[i][j].match > 0 && tileArray[i + 1][j].match > 0
                                && tileArray[i][j].kind == tileArray[i + 1][j].kind
                                && tileArray[i][j].kind == tileArray[i + 2][j].kind
                                && tileArray[i][j].kind == tileArray[i + 3][j].kind) {
                            //Check row for 5
                            if (i < row - 4 && tileArray[i][j].kind == tileArray[i + 4][j].kind) {
                                //Add upgrade animation
                                animationManager.upgrade2I_v(tileArray[i + 2][j]);
                                tileArray[i][j].isUpgrade = true;
                                tileArray[i + 1][j].isUpgrade = true;
                                tileArray[i + 3][j].isUpgrade = true;
                                tileArray[i + 4][j].isUpgrade = true;
                                //Make it special
                                tileArray[i + 2][j].match = 0;     //Unlock won't detect
                                tileArray[i + 2][j].special = true;
                                tileArray[i + 2][j].direct = 'I';
                                tileArray[i + 2][j].kind = TileID.ICE_CREAM;
                            } else {
                                //Check is top or bottom be chosen
                                if ((i + 1 == swapRow && j == swapCol) || (i + 1 == swapRow2 && j == swapCol2)) {
                                    //If tile is already special, do not add
                                    if (!tileArray[i + 1][j].special && !tileArray[i + 1][j].isUpgrade) {
                                        //Add upgrade animation
                                        animationManager.upgrade2V_top(tileArray[i + 1][j]);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i + 2][j].isUpgrade = true;
                                        tileArray[i + 3][j].isUpgrade = true;
                                        //Make it special
                                        tileArray[i + 1][j].match = 0;     //Unlock won't detect
                                        tileArray[i + 1][j].special = true;
                                        tileArray[i + 1][j].direct = 'H';
                                    }
                                } else {
                                    //If tile is already special, do not add
                                    if (!tileArray[i + 2][j].special && !tileArray[i + 2][j].isUpgrade) {
                                        //Add upgrade animation
                                        animationManager.upgrade2V_bottom(tileArray[i + 2][j]);
                                        tileArray[i][j].isUpgrade = true;
                                        tileArray[i + 1][j].isUpgrade = true;
                                        tileArray[i + 3][j].isUpgrade = true;
                                        //Make it special
                                        tileArray[i + 2][j].match = 0;     //Unlock won't detect
                                        tileArray[i + 2][j].special = true;
                                        tileArray[i + 2][j].direct = 'H';
                                    }

                                }
                            }
                        }
                    }
                }
            }

            //(5.5) Check special fruit
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
                                //explodeBigH(tileArray[i][j]);
                            } else {
                                if (!tileArray[i][j].isExplode)
                                    explodeH(tileArray, tileArray[i][j]);
                            }
                        } else if (tileArray[i][j].direct == 'V') {
                            //Check special combine
                            if (tileArray[i][j].specialCombine == 'R') {
                                explodeH(tileArray, tileArray[i][j]);
                            } else if (tileArray[i][j].specialCombine == 'G') {
                                //explodeBigV(tileArray[i][j]);
                            } else {
                                if (!tileArray[i][j].isExplode)
                                    explodeV(tileArray, tileArray[i][j]);
                            }
                        } else if (tileArray[i][j].direct == 'S' && !tileArray[i][j].isExplode) {
                            //Check special combine
                            if (tileArray[i][j].specialCombine == 'B') {
                                //explodeBigS(tileArray[i][j]);
                            } else {
                                explodeS(tileArray, tileArray[i][j]);
                            }
                        } else if (tileArray[i][j].direct == 'I' && !tileArray[i][j].isExplode) {
                            //Check special combine
                            if (tileArray[i][j].specialCombine == 'T') {
                                //transI(tileArray[i][j]);
                            } else if (tileArray[i][j].specialCombine == 'S') {
                                // transI(tileArray[i][j]);
                            } else if (tileArray[i][j].specialCombine == 'M') {
                                //  massI(tileArray[i][j]);
                            } else {
                                explodeI(tileArray, tileArray[i][j]);
                            }
                        }
                    }
                }
            }

            //(5.7.3) Add animation
            for (int j = 0; j < column; j++) {
                for (int i = 0; i < row; i++) {
                    //Check is match
                    if (!tileArray[i][j].empty
                            && tileArray[i][j].match != 0
                            && tileArray[i][j].kind != 0
                            && !tileArray[i][j].isAnimate) {

                        // Set isAnimate
                        tileArray[i][j].isAnimate = true;

                        //Explode fruit
                        if (!tileArray[i][j].isUpgrade)
                            animationManager.explodeFruit(tileArray[i][j]);
                        //Show score
                        animationManager.createScore(tileArray[i][j]);
                    }
                }
            }

            tile2Top(tileArray);
            tileReset(tileArray);
        }

        updateWait(tileArray);
        diagonalSwap(tileArray);

        if (waitFinding) {
            tile2Top(tileArray);
            tileReset(tileArray);
        }
    }

    //----------------------------------------------------------------------------------
    // Method of Algorithm
    //----------------------------------------------------------------------------------
    private void findMatch(Tile[][] tileArray) {

        // Check match 3 in column
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
                            // explodeAround(tileMatrix[i + n][j]);
                        }

                    }
                }
            }
        }

        // Check match 3 in row
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
                            // explodeAround(tileMatrix[i][j + n]);
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
    //==================================================================================

    public void swap(Tile[][] tileArray, Tile tile1, Tile tile2) {
        if (tile1.invalid || tile2.invalid)
            return;

        //Exchange row
        int temp_row = tile1.row;
        tile1.row = tile2.row;
        tile2.row = temp_row;

        //Exchange column
        int temp_col = tile1.col;
        tile1.col = tile2.col;
        tile2.col = temp_col;

        //Exchange tile
        tileArray[tile1.row][tile1.col] = tile1;
        tileArray[tile2.row][tile2.col] = tile2;

    }

    public void checkSpecialCombine(Tile tile1, Tile tile2) {
        if (tile1.special && tile2.special) {
            if (tile1.direct == 'H') {
                if (tile2.direct == 'H') {
                    tile1.match++;
                    tile2.match++;
                    tile1.specialCombine = 'R';
                } else if (tile2.direct == 'V') {
                    tile1.match++;
                    tile2.match++;
                } else if (tile2.direct == 'S') {
                    tile1.match++;
                    tile2.isExplode = true;
                    tile1.specialCombine = 'G';
                } else if (tile2.direct == 'I') {
                    tile1.match++;
                    tile2.match++;
                    tile1.isExplode = true;    // So tile1 became regular fruit
                    tile2.specialCombine = 'T';
                    tile2.iceCreamTarget = tile1.kind;
                }
            } else if (tile1.direct == 'V') {
                if (tile2.direct == 'H') {
                    tile1.match++;
                    tile2.match++;
                } else if (tile2.direct == 'V') {
                    tile1.match++;
                    tile2.match++;
                    tile2.specialCombine = 'R';
                } else if (tile2.direct == 'S') {
                    tile1.match++;
                    tile2.isExplode = true;
                    tile1.specialCombine = 'G';
                } else if (tile2.direct == 'I') {
                    tile1.match++;
                    tile2.match++;
                    tile1.isExplode = true;
                    tile2.specialCombine = 'T';
                    tile2.iceCreamTarget = tile1.kind;
                }
            } else if (tile1.direct == 'S') {
                if (tile2.direct == 'H') {
                    tile2.match++;
                    tile1.isExplode = true;
                    tile2.specialCombine = 'G';
                } else if (tile2.direct == 'V') {
                    tile2.match++;
                    tile1.isExplode = true;
                    tile2.specialCombine = 'G';
                } else if (tile2.direct == 'S') {
                    tile1.match++;
                    tile2.isExplode = true;
                    tile1.specialCombine = 'B';
                } else if (tile2.direct == 'I') {
                    tile1.match++;
                    tile2.match++;
                    tile1.isExplode = true;
                    tile2.specialCombine = 'S';
                    tile2.iceCreamTarget = tile1.kind;
                }
            } else if (tile1.direct == 'I') {
                if (tile2.direct == 'H') {
                    tile1.match++;
                    tile2.match++;
                    tile2.isExplode = true;
                    tile1.specialCombine = 'T';
                    tile1.iceCreamTarget = tile2.kind;
                } else if (tile2.direct == 'V') {
                    tile1.match++;
                    tile2.match++;
                    tile2.isExplode = true;
                    tile1.specialCombine = 'T';
                    tile1.iceCreamTarget = tile2.kind;
                } else if (tile2.direct == 'S') {
                    tile1.match++;
                    tile2.match++;
                    tile2.isExplode = true;
                    tile1.specialCombine = 'S';
                    tile1.iceCreamTarget = tile2.kind;
                } else if (tile2.direct == 'I') {
                    //here
                    tile1.match++;
                    tile2.match++;
                    tile1.specialCombine = 'M';
                    tile2.isExplode = true;
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

    private void tileWait() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isWait = 3;
            }
        }, WAIT_TIME);
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

    private void explodeH(Tile[][] tileArray, Tile tile) {
        //Mark isExplode
        tile.isExplode = true;
        //Add horizontal flash
        animationManager.createHorizontalFlash(tile);
        //Add match in row
        for (int j = 0; j < column; j++) {
            //Check is empty fruit
            if (!tileArray[tile.row][j].empty) {

                // Add match
                tileArray[tile.row][j].match++;

                // Explode cake
                //explodePie(tileArray[tile.row][n]);

                //Check fruit is special in row
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
                            //explodeI(tileArray[tile.row][n]);
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
        //Add match in column
        for (int i = 0; i < row; i++) {
            //Check is empty fruit
            if (!tileArray[i][tile.col].empty) {

                // Add match
                tileArray[i][tile.col].match++;

                // Explode cake
                //explodePie(tileArray[n][tile.col]);

                //Check fruit is special in column
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
                            //explodeI(tileArray[n][tile.col]);
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
                                //explodeI(tileArray[r][c]);
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

        // Get target fruit
        int target = 0;
        if (tile.iceCreamTarget == 0) {
            // Get a random fruit
            target = TileID.FRUITS[(int) (Math.random() * fruitMun)];
        } else {
            target = tile.iceCreamTarget;
        }

        // Check same fruit kind
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {

                // Check state
                if (!tileArray[i][j].empty && !tileArray[i][j].isExplode && tileArray[i][j].kind != 0) {

                    //Check is target fruit
                    if (target == tileArray[i][j].kind) {

                        // Add lightning animation
                        animationManager.createLightning(tile, tileArray[i][j]);
                        animationManager.createLightning_fruit(tileArray[i][j], false);

                        // Add match to target and explode around
                        tileArray[i][j].match++;
                        //explodeAround(tileArray[i][j]);

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

}
