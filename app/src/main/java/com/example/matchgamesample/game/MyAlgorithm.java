package com.example.matchgamesample.game;

import com.example.matchgamesample.effect.AnimationManager;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.InputController;
import com.example.matchgamesample.level.Level;

public class MyAlgorithm {
    private GameEngine mGameEngine;
    private InputController mInputController;

    private final Level mLevel;
    private final int row, column;
    private final int tileSize;
    private Tile[][] tileArray;

    //Fruit's moving SPEED
    private int SPEED = 15;
    private int WAIT_TIME = 300;   //300

    //Flag
    public boolean isSwap = false, isSwapBack = false, isMoving = false, isWin = false, isBonusTime = false, isRunning = true;
    private boolean showHint = true, isTransf = false, isShowGuide = true;
    private boolean matchFinding = false, waitFinding = false, fallingFinding = false;
    public char direction = 'N';
    public int swapCol, swapRow;
    private int isWait = 1;
    private int combo = 0;
    private int winningStage = 0;

    private boolean wait = false;

    private final AnimationManager mAnimationManager;

    public MyAlgorithm(GameEngine gameEngine, Level level, Tile[][] tileMatrix) {
        mGameEngine = gameEngine;
        mInputController = gameEngine.mInputController;
        mLevel = level;
        row = level.row;
        column = level.column;
        tileSize = gameEngine.mImageSize;
        tileArray = tileMatrix;

        mAnimationManager = new AnimationManager(mGameEngine);
    }

    public void run(Tile[][] tileArray) {
        findMatch(tileArray);

        isMoving = false;
        outer:
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

                if (tileArray[i][j].isMoving()) {
                    isMoving = true;
                    break outer;
                } else {
                    if (isSwap) {
                        isSwapBack = false;
                    }
                }

            }
        }

        matchFinding = false;
        outer:
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if(tileArray[i][j].match != 0){
                    matchFinding = true;
                    break outer;
                }
            }
        }

        swapBack(tileArray);

        if (!isMoving) {
            wait = false;
            addAnimation(tileArray);
            tile2Top(tileArray);
            tileReset(tileArray);
        }
    }

    private void findMatch(Tile[][] tileMatrix) {
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row - 2; i++) {
                //Check state
                if (!tileMatrix[i][j].empty
                        && tileMatrix[i][j].wait == 0
                        && !tileMatrix[i][j].breakable
                        && tileMatrix[i][j].kind != 0
                        && tileMatrix[i][j].kind != TileID.STAR_FISH) {
                    //Check match 3 in column
                    if ((tileMatrix[i][j].kind == tileMatrix[i + 1][j].kind) &&
                            (tileMatrix[i][j].kind == tileMatrix[i + 2][j].kind)) {
                        //Add match and explode around
                        for (int n = 0; n <= 2; n++) {
                            tileMatrix[i + n][j].match++;
                            // explodeAround(tileMatrix[i + n][j]);
                        }

                    }
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column - 2; j++) {
                //Check state
                if (!tileMatrix[i][j].empty
                        && tileMatrix[i][j].wait == 0
                        && !tileMatrix[i][j].breakable
                        && tileMatrix[i][j].kind != 0
                        && tileMatrix[i][j].kind != TileID.STAR_FISH) {

                    //Check match 3 in row
                    if ((tileMatrix[i][j].kind == tileMatrix[i][j + 1].kind) &&
                            (tileMatrix[i][j].kind == tileMatrix[i][j + 2].kind)) {
                        //Add match and explode around
                        for (int n = 0; n <= 2; n++) {
                            tileMatrix[i][j + n].match++;
                            // explodeAround(tileMatrix[i][j + n]);
                        }

                    }
                }
            }
        }
    }

    private void swapBack(Tile[][] tileArray){
        //Swap back if no match
        if(isSwap && !isMoving) {
            if(!matchFinding) {
                switch (direction) {
                    case 'u':
                        swap(tileArray[swapRow][swapCol], tileArray[swapRow - 1][swapCol]);
                        break;
                    case 'd':
                        swap(tileArray[swapRow][swapCol], tileArray[swapRow + 1][swapCol]);
                        break;
                    case 'l':
                        swap(tileArray[swapRow][swapCol], tileArray[swapRow][swapCol - 1]);
                        break;
                    case 'r':
                        swap(tileArray[swapRow][swapCol], tileArray[swapRow][swapCol + 1]);
                        break;
                }
                direction = 'N';
                isSwapBack = true;
            }else{
                //Player move
                //reduceMove();
                //Restart hint
                //hint.stopHint();
                //showHint = true;
            }
            isSwap = false;
        }
    }

    private void addAnimation(Tile[][] tileMatrix) {
        //(5.7.3) Add animation
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                //Check is match
                if (!tileMatrix[i][j].empty
                        && tileMatrix[i][j].match != 0
                        && tileMatrix[i][j].kind != 0
                        && !tileMatrix[i][j].isAnimate) {

                    // Set isAnimate
                    tileMatrix[i][j].isAnimate = true;

                    //Explode fruit
                    if (!tileMatrix[i][j].isUpgrade)
                        mAnimationManager.explodeFruit(tileMatrix[i][j]);
                    //Show score
                    mAnimationManager.createScore(tileMatrix[i][j]);
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

                            swap(tileArray[n][j], tileArray[i][j]);
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
                    tileArray[i][j].match = 0;
                    if (tileArray[i][j].special) {
                        tileArray[i][j].special = false;
                        tileArray[i][j].direct = 'N';
                        tileArray[i][j].specialCombine = 'N';
                        tileArray[i][j].iceCreamTarget = 0;
                    }
                    if (tileArray[i][j].isUpgrade) {
                        tileArray[i][j].isUpgrade = false;
                    }
                    if (tileArray[i][j].breakable) {
                        tileArray[i][j].breakable = false;
                    }

                    tileArray[i][j].isExplode = false;   //Do not put this in "if (fruitArray[i][j].special)", or it won't detect

                    //Assign fruit kind
                    tileArray[i][j].kind = TileID.FRUITS_TO_USE[(int) (Math.random() * TileID.FRUITS_TO_USE.length)];
                    /*
                    if(!isWin) {
                        if (tileArray[i][j].machine == 'H') {
                            if (cherry_num < CHERRY_MAX) {
                                cherry_num++;
                            } else {
                                cherry_num = 0;
                                tileArray[i][j].kind = R.drawable.cherry;
                                tileArray[i][j].special = true;
                                tileArray[i][j].direct = 'H';
                                animationManager.createMachineAnim(advanceArray[0][j - 1]);
                            }
                        }else if (tileArray[i][j].machine == 'V') {
                            if (cherry_num < CHERRY_MAX) {
                                cherry_num ++;
                            }else{
                                cherry_num = 0;
                                tileArray[i][j].kind = R.drawable.cherry;
                                tileArray[i][j].special = true;
                                tileArray[i][j].direct = 'V';
                                animationManager.createMachineAnim(advanceArray[0][j - 1]);
                            }
                        }else if (tileArray[i][j].machine == 'S') {
                            if (strawberry_num < STRAWBERRY_MAX) {
                                strawberry_num ++;
                            }else{
                                strawberry_num = 0;
                                tileArray[i][j].kind = R.drawable.strawberry;
                                tileArray[i][j].special = true;
                                tileArray[i][j].direct = 'S';
                                animationManager.createMachineAnim(advanceArray[0][j - 1]);
                            }
                        } else if (tileArray[i][j].machine == 'O') {
                            if (ice_cream_num < ICE_CREAM_MAX) {
                                ice_cream_num ++;
                            }else{
                                ice_cream_num = 0;
                                tileArray[i][j].kind = TileID.ICE_CREAM;
                                tileArray[i][j].special = true;
                                tileArray[i][j].direct = 'I';
                                animationManager.createMachineAnim(advanceArray[0][j - 1]);
                            }
                        } else if (tileArray[i][j].machine == 'X') {
                            if(star_fish_num < STAR_FISH_MAX){
                                if (Math.random() < 0.5) {
                                    star_fish_num++;
                                    tileArray[i][j].kind = TileID.STAR_FISH;
                                    animationManager.createMachineAnim(advanceArray[0][j - 1]);
                                }
                            }
                        }
                    }

                     */
                }
            }
        }
    }

    public void swap(Tile tile1, Tile tile2) {
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

}
