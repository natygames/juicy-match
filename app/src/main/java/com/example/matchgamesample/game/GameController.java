package com.example.matchgamesample.game;

import android.os.Handler;

import com.example.matchgamesample.effect.AnimationManager;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameEventListener;
import com.example.matchgamesample.engine.GameObject;
import com.example.matchgamesample.engine.InputController;
import com.example.matchgamesample.level.Level;

public class GameController extends GameObject implements GameEventListener {
    private final Level mLevel;
    private final Tile[][] tileArray;
    private final int mRow, mColumn;
    private final int mTileSize;
    private final InputController mInputController;
    private AnimationManager mAnimationManager;
    private final MyAlgorithm myAlgorithm;
    private final Handler mHandler = new Handler();

    //----------------------------------------------------------------------------------
    // Var to change state of game
    //----------------------------------------------------------------------------------
    public boolean isSwap = false, isSwapBack = false, isMoving = false;
    private boolean matchFinding = false, waitFinding = false;
    public char direction = 'N';
    public int swapCol, swapRow;
    private int isWait = 1;
    // Tile's moving SPEED
    private int SPEED = 15;
    private int WAIT_TIME = 300;   //300
    //==================================================================================

    public GameController(GameEngine gameEngine, Tile[][] tileMatrix) {
        mLevel = gameEngine.mLevel;
        tileArray = tileMatrix;
        mRow = gameEngine.mLevel.row;
        mColumn = gameEngine.mLevel.column;
        mTileSize = gameEngine.mImageSize;
        mInputController = gameEngine.mInputController;

        myAlgorithm = new MyAlgorithm(gameEngine);
    }

    @Override
    public void startGame() {
        // Set up the tile
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                tileArray[i][j].startGame();
            }
        }
    }

    @Override
    public void onUpdate() {
        update();
    }

    @Override
    public void onDraw() {
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                tileArray[i][j].onDraw();
            }
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        switch (gameEvents) {
            case Swap:
                playerSwap();
                break;
            case PropsHand:
                // myAlgorithm.useHand();
                break;
            case PropsHammer:
                // myAlgorithm.useHammer();
                break;
            case PropsBomb:
                // myAlgorithm.useBomb();
                break;
        }
    }

    private void update() {
        updateWait();
        if (!waitFinding) {
            myAlgorithm.findMatch(tileArray);
        }

        isMoving = false;
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                int diff_x = 0, diff_y = 0;
                for (int n = 0; n < SPEED; n++) {
                    diff_x = tileArray[i][j].x - tileArray[i][j].col * mTileSize;
                    diff_y = tileArray[i][j].y - tileArray[i][j].row * mTileSize;
                    if (diff_x != 0) {
                        if (isWait == 3) {
                            // Check diagonal swapping position
                            if (tileArray[i][j].y >= tileArray[i][j].diagonal * mTileSize)
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
                                    if (diff_y <= -mTileSize * 4) {
                                        tileArray[i][j].bounce = 2;
                                    } else {
                                        tileArray[i][j].bounce = 1;
                                    }
                                }
                                //Go down
                                tileArray[i][j].y -= diff_y / Math.abs(diff_y);
                                //Set bounce when stop falling
                                if (tileArray[i][j].y == tileArray[i][j].row * mTileSize) {
                                    if (!isSwap && !isSwapBack) {
                                        if (tileArray[i][j].bounce == 1) {
                                            mAnimationManager.createLightBounceAnim(tileArray[i][j].mImage);
                                        } else {
                                            mAnimationManager.createHeavyBounceAnim(tileArray[i][j].mImage);
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

        updateMove();

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

        updateMatch();

        //Swap back if no match
        if (isSwap && !isMoving) {
            if (!matchFinding) {
                switch (direction) {
                    case 'U':
                        myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow - 1][swapCol]);
                        break;
                    case 'D':
                        myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow + 1][swapCol]);
                        break;
                    case 'L':
                        myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow][swapCol - 1]);
                        break;
                    case 'R':
                        myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow][swapCol + 1]);
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
            for (int i = 0; i < mRow; i++) {
                for (int j = 0; j < mColumn; j++) {
                    //Check is match
                    if (!tileArray[i][j].empty
                            && tileArray[i][j].match != 0
                            && tileArray[i][j].kind != 0
                            && !tileArray[i][j].isAnimate) {

                        // Set isAnimate
                        tileArray[i][j].isAnimate = true;

                        //Explode fruit
                        if (!tileArray[i][j].isUpgrade)
                            mAnimationManager.explodeFruit(tileArray[i][j]);
                        //Show score
                        mAnimationManager.createScore(tileArray[i][j]);
                    }
                }
            }

            myAlgorithm.tile2Top(tileArray);
            myAlgorithm.tileReset(tileArray);
        }

        updateWait();
        // Diagonal swapping
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {

                // First, check wait = 2 top down

                //Find waiting tile
                if (tileArray[i][j].wait != 0) {

                    //Look up
                    for (int n = i - 1; n >= 0; n--) {

                        //Find obstacle
                        if ((tileArray[n][j].invalid && !tileArray[n][j].tube) || tileArray[n][j].wait == 2) {

                            //Check is blocked
                            if ((tileArray[n][j - 1].invalid || tileArray[n][j - 1].wait == 2)
                                    && (tileArray[n][j + 1].invalid || tileArray[n][j + 1].wait == 2)) {
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
        for (int i = mRow - 1; i >= 0; i--) {
            for (int j = 0; j < mColumn; j++) {

                // Then, diagonal swap bottom up

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
                                if (tileArray[m][j + 1].invalid || (tileArray[m][j + 1].y - tileArray[m][j + 1].row * mTileSize != 0)) {
                                    break;
                                } else if (tileArray[m][j + 1].wait == 0) {
                                    /*    O X
                                     *
                                     *    O
                                     */
                                    tileArray[i][j].match++;
                                    tileArray[i][j].wait = 0;
                                    tileArray[m][j + 1].diagonal = n;
                                    myAlgorithm.swap(tileArray, tileArray[m][j + 1], tileArray[i][j]);
                                    break outer;
                                }
                            }

                            //Look left
                            for (int m = n; m >= 0; m--) {
                                if (tileArray[m][j - 1].invalid || (tileArray[m][j - 1].y - tileArray[m][j - 1].row * mTileSize != 0)) {
                                    break;
                                } else if (tileArray[m][j - 1].wait == 0) {
                                    /*  X O
                                     *
                                     *    O
                                     */
                                    tileArray[i][j].match++;
                                    tileArray[i][j].wait = 0;
                                    tileArray[m][j - 1].diagonal = n;
                                    myAlgorithm.swap(tileArray, tileArray[m][j - 1], tileArray[i][j]);
                                    break outer;
                                }
                            }

                            break;
                        }
                    }
                }
            }
        }

        if (waitFinding) {
            myAlgorithm.tile2Top(tileArray);
            myAlgorithm.tileReset(tileArray);
        }
    }

    private void playerSwap() {
        // Get the tile player press from inputController
        swapCol = mInputController.mX_Down / mTileSize;
        swapRow = mInputController.mY_Down / mTileSize;
        isSwap = true;

        if (mInputController.mX_Down - mInputController.mX_Up < -50) {
            // Swap right
            if (swapCol == mColumn - 1)
                return;
            myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow][swapCol + 1]);
            direction = 'R';
        } else if (mInputController.mX_Down - mInputController.mX_Up > 50) {
            // Swap left
            if (swapCol == 0)
                return;
            myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow][swapCol - 1]);
            direction = 'L';
        } else if (mInputController.mY_Down - mInputController.mY_Up > 50) {
            // Swap up
            if (swapRow == 0)
                return;
            myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow - 1][swapCol]);
            direction = 'U';
        } else if (mInputController.mY_Down - mInputController.mY_Up < -50) {
            // Swap down
            if (swapRow == mRow - 1)
                return;
            myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow + 1][swapCol]);
            direction = 'D';
        }

    }

    public void setAnimationManager(AnimationManager animationManager) {
        mAnimationManager = animationManager;
    }

    private void tileWait() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isWait = 3;
            }
        }, WAIT_TIME);
    }

    private void updateMove() {
        isMoving = false;
        outer:
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                if (tileArray[i][j].isMoving()) {
                    isMoving = true;
                    break outer;
                }
            }
        }
    }

    private void updateWait() {
        waitFinding = false;
        outer:
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                if (tileArray[i][j].wait == 1) {
                    waitFinding = true;
                    break outer;
                }
            }
        }
    }

    private void updateMatch() {
        matchFinding = false;
        outer:
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                if (tileArray[i][j].match != 0) {
                    matchFinding = true;
                    break outer;
                }
            }
        }
    }

}
