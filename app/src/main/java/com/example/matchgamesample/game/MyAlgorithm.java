package com.example.matchgamesample.game;

import android.os.Handler;

import com.example.matchgamesample.effect.AnimationManager;
import com.example.matchgamesample.engine.GameEngine;

public class MyAlgorithm {
    private final int row, column;
    private final int tileSize;
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
        tileSize = gameEngine.mImageSize;
        animationManager = new AnimationManager(gameEngine);
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

}
