package com.nativegame.match3game.game.algorithm;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.effect.animation.AnimationManager;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.effect.sound.SoundManager;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.game.tile.Tile;
import com.nativegame.match3game.game.tile.TileUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * BaseAlgorithm contain all the operation of ours match 3 game,
 * including find match, generate random tile, diagonal swap,
 * generate special fruit, and fruit explode. For the detail
 * algorithm, please look up update method in GameAlgorithm
 */

public class BaseAlgorithm {
    protected final GameEngine mGameEngine;
    protected final int mRow, mColumn;
    protected final int mTileSize;
    protected final AnimationManager mAnimationManager;
    protected final SoundManager mSoundManager;
    //----------------------------------------------------------------------------------
    // Var to change state of game
    //----------------------------------------------------------------------------------
    protected boolean mIsMoving = false, mMatchFinding = false, mWaitFinding = false;
    protected boolean mIsTransf = false;   // To control ice cream transform animation
    protected int mWaitingTime = 0;
    //==================================================================================

    protected BaseAlgorithm(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mRow = gameEngine.mLevel.mRow;
        mColumn = gameEngine.mLevel.mColumn;
        mTileSize = gameEngine.mImageSize;
        mAnimationManager = new AnimationManager(gameEngine);
        mSoundManager = ((MainActivity) gameEngine.mActivity).getSoundManager();
    }

    public void update(Tile[][] tileArray, long elapsedMillis) {
        // We update the game here
    }

    //----------------------------------------------------------------------------------
    // Method of Algorithm
    //----------------------------------------------------------------------------------
    protected void findMatch(Tile[][] tileArray) {

        // Check match 3 in column
        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow - 2; i++) {
                //Check state
                if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {
                    // Check is there a sequence
                    if ((tileArray[i][j].kind == tileArray[i + 1][j].kind) &&
                            (tileArray[i][j].kind == tileArray[i + 2][j].kind)) {
                        // Add match and explode around
                        for (int n = 0; n <= 2; n++) {
                            tileArray[i + n][j].match = 1;
                            explodeAround(tileArray, tileArray[i + n][j]);
                        }

                    }
                }
            }
        }

        // Check match 3 in row
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 2; j++) {
                // Check state
                if (tileArray[i][j].isFruit() && tileArray[i][j].wait == 0) {

                    // Check is there a sequence
                    if ((tileArray[i][j].kind == tileArray[i][j + 1].kind) &&
                            (tileArray[i][j].kind == tileArray[i][j + 2].kind)) {
                        // Add match and explode around
                        for (int n = 0; n <= 2; n++) {
                            tileArray[i][j + n].match = 1;
                            explodeAround(tileArray, tileArray[i][j + n]);
                        }

                    }
                }
            }
        }
    }

    protected void tile2Top(Tile[][] tileArray) {
        for (int j = 0; j < mColumn; j++) {
            for (int i = mRow - 1; i >= 0; i--) {
                // Check match
                if (tileArray[i][j].match != 0) {
                    // Swap tile
                    for (int n = i - 1; n >= 0; n--) {
                        // If empty tube do not swap
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

    protected void tileReset(Tile[][] tileArray) {
        for (int j = 0; j < mColumn; j++) {
            for (int i = mRow - 1, n = 1; i >= 0; i--) {

                // Breakable may cause 0 match
                if (tileArray[i][j].isAnimate) {
                    tileArray[i][j].isAnimate = false;
                }

                if (tileArray[i][j].match != 0) {

                    // Go to top
                    tileArray[i][j].y = -mTileSize * n++;
                    tileArray[i][j].x = tileArray[i][j].col * mTileSize;
                    // Reset fruit
                    tileArray[i][j].reset();

                }
            }
        }
    }

    protected void diagonalSwap(Tile[][] tileArray) {

        // First, check wait = 2 top down
        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow; i++) {

                // Find waiting tile
                if (tileArray[i][j].wait != 0) {

                    // Look up
                    for (int n = i - 1; n >= 0; n--) {

                        // Find obstacle
                        if ((tileArray[n][j].invalid && !tileArray[n][j].tube) || tileArray[n][j].wait == 2) {

                            // Check is blocked
                            if ((j == 0 || tileArray[n][j - 1].invalid || tileArray[n][j - 1].wait == 2)
                                    && (j == mColumn - 1 || tileArray[n][j + 1].invalid || tileArray[n][j + 1].wait == 2)) {
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
        for (int j = 0; j < mColumn; j++) {
            for (int i = mRow - 1; i >= 0; i--) {

                // Find waiting tile
                if (tileArray[i][j].wait != 0) {

                    // Look up
                    outer:
                    for (int n = i - 1; n >= 0; n--) {

                        // Find obstacle
                        if ((tileArray[n][j].invalid && !tileArray[n][j].tube)
                                || tileArray[n][j].wait == 2) {

                            if (tileArray[n + 1][j].tube) {
                                /* The tile can only go though tube vertically from top
                                 *     x o x  <-- tile (No diagonal swapping)
                                 *      | |
                                 *      | |   <-- tube
                                 */
                                break;
                            }

                            // Look right
                            for (int m = n; m >= 0; m--) {
                                if (j == mColumn - 1 || tileArray[m][j + 1].invalid
                                        || (tileArray[m][j + 1].y - tileArray[m][j + 1].row * mTileSize != 0)) {
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

                            // Look left
                            for (int m = n; m >= 0; m--) {
                                if (j == 0 || tileArray[m][j - 1].invalid
                                        || (tileArray[m][j - 1].y - tileArray[m][j - 1].row * mTileSize != 0)) {
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

    protected void updateMove(Tile[][] tileArray) {
        mIsMoving = false;
        outer:
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                if (tileArray[i][j].isMoving()) {
                    mIsMoving = true;
                    break outer;
                }
            }
        }
    }

    protected void updateWait(Tile[][] tileArray) {
        mWaitFinding = false;
        outer:
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                if (tileArray[i][j].wait == 1) {
                    mWaitFinding = true;
                    break outer;
                }
            }
        }
    }

    protected void updateMatch(Tile[][] tileArray) {
        mMatchFinding = false;
        outer:
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                if (tileArray[i][j].match != 0) {
                    mMatchFinding = true;
                    break outer;
                }
            }
        }
    }

    public void swap(Tile[][] tileArray, Tile tile1, Tile tile2) {
        // Exchange row
        int temp_row = tile1.row;
        tile1.row = tile2.row;
        tile2.row = temp_row;

        // Exchange column
        int temp_col = tile1.col;
        tile1.col = tile2.col;
        tile2.col = temp_col;

        // If ice do not swap
        if (tile1.ice != 0 || tile2.ice != 0) {
            int temp = tile1.ice;
            tile1.ice = tile2.ice;
            tile2.ice = temp;
        }

        // If entryPoint do not swap
        if (tile1.entryPoint || tile2.entryPoint) {
            boolean temp = tile1.entryPoint;
            tile1.entryPoint = tile2.entryPoint;
            tile2.entryPoint = temp;
        }

        // Exchange tile
        tileArray[tile1.row][tile1.col] = tile1;
        tileArray[tile2.row][tile2.col] = tile2;

    }
    //==================================================================================

    //----------------------------------------------------------------------------------
    // Method of special fruit
    //----------------------------------------------------------------------------------
    protected void explodeH(Tile[][] tileArray, Tile tile) {
        // Mark isExplode
        tile.isExplode = true;

        // Add horizontal flash
        mAnimationManager.createHorizontalFlash(tile);
        mSoundManager.playSoundForSoundEvent(SoundEvent.VERTICAL_EXPLODE);

        // Add match in row
        for (int j = 0; j < mColumn; j++) {
            // Check is empty fruit
            if (!tileArray[tile.row][j].empty) {

                // Add match
                tileArray[tile.row][j].match++;

                // Check fruit is special in row
                if (tileArray[tile.row][j].special) {
                    // Check is explode
                    if (!tileArray[tile.row][j].isExplode && !tileArray[tile.row][j].lock) {
                        // Check direct
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

    protected void explodeV(Tile[][] tileArray, Tile tile) {
        // Mark isExplode
        tile.isExplode = true;

        // Add vertical flash
        mAnimationManager.createVerticalFlash(tile);
        mSoundManager.playSoundForSoundEvent(SoundEvent.VERTICAL_EXPLODE);

        // Add match in column
        for (int i = 0; i < mRow; i++) {
            // Check is empty fruit
            if (!tileArray[i][tile.col].empty) {

                // Add match
                tileArray[i][tile.col].match++;

                // Check fruit is special in column
                if (tileArray[i][tile.col].special) {
                    // Check is explode
                    if (!tileArray[i][tile.col].isExplode && !tileArray[i][tile.col].lock) {
                        // Check direct
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

    protected void explodeS(Tile[][] tileArray, Tile tile) {
        int row = tile.row;
        int col = tile.col;

        // Mark isExplode
        tile.isExplode = true;

        // Add square flash
        mAnimationManager.createScaleAnim();
        mAnimationManager.createSquareFlash(tile);
        mSoundManager.playSoundForSoundEvent(SoundEvent.SQUARE_EXPLODE);

        // Add match in square
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {

                if (i < 0 || i >= mRow || j < 0 || j >= mColumn)
                    continue;

                // Check is empty fruit
                if (!tileArray[i][j].empty) {

                    // Add match
                    tileArray[i][j].match++;

                    // Check fruit is special in square
                    if (tileArray[i][j].special) {
                        // Check is explode
                        if (!tileArray[i][j].isExplode && !tileArray[i][j].lock) {
                            // Check direct
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

    protected void explodeBigH(Tile[][] tileArray, Tile tile) {
        int row = tile.row;
        int col = tile.col;

        if (row == 0) {
            explodeH(tileArray, tileArray[row][col]);
            explodeH(tileArray, tileArray[row + 1][col]);
        } else if (row == this.mRow - 1) {
            explodeH(tileArray, tileArray[row - 1][col]);
            explodeH(tileArray, tileArray[row][col]);
        } else {
            explodeH(tileArray, tileArray[row - 1][col]);
            explodeH(tileArray, tileArray[row][col]);
            explodeH(tileArray, tileArray[row + 1][col]);
        }
        mAnimationManager.createVerticalShakingAnim();
        mAnimationManager.createExplodeWave_small(tileArray[row][col]);
        mAnimationManager.createSquareFlash(tileArray[row][col]);
    }

    protected void explodeBigV(Tile[][] tileArray, Tile tile) {
        int row = tile.row;
        int col = tile.col;

        if (col == 0) {
            explodeV(tileArray, tileArray[row][col]);
            explodeV(tileArray, tileArray[row][col + 1]);
        } else if (col == this.mColumn - 1) {
            explodeV(tileArray, tileArray[row][col - 1]);
            explodeV(tileArray, tileArray[row][col]);
        } else {
            explodeV(tileArray, tileArray[row][col - 1]);
            explodeV(tileArray, tileArray[row][col]);
            explodeV(tileArray, tileArray[row][col + 1]);
        }
        mAnimationManager.createHorizontalShakingAnim();
        mAnimationManager.createExplodeWave_small(tileArray[row][col]);
        mAnimationManager.createSquareFlash(tileArray[row][col]);
    }

    protected void explodeBigS(Tile[][] tileArray, Tile tile) {
        int row = tile.row;
        int col = tile.col;

        // Mark isExplode
        tile.isExplode = true;

        // Add square flash
        mAnimationManager.createScaleAnim();
        mAnimationManager.createSquareFlash_big(tile);
        mSoundManager.playSoundForSoundEvent(SoundEvent.SQUARE_EXPLODE);

        // Add match in 5x5 square
        for (int i = row - 2; i <= row + 2; i++) {
            for (int j = col - 2; j <= col + 2; j++) {

                // Check index not out of bound
                if (i < 0 || i >= mRow || j < 0 || j >= mColumn)
                    continue;

                // Check is empty fruit
                if (!tileArray[i][j].empty) {

                    // Add match
                    tileArray[i][j].match++;

                    // Check fruit is special in square
                    if (tileArray[i][j].special) {
                        // Check is explode
                        if (!tileArray[i][j].isExplode && !tileArray[i][j].lock) {
                            // Check direct
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

    protected void explodeI(Tile[][] tileArray, Tile tile) {
        // Mark isExplode
        tile.isExplode = true;

        // Get target fruit
        int target = 0;
        if (tile.iceCreamTarget == 0) {
            // Get a random fruit
            target = TileUtils.FRUITS[(int) (Math.random() * Tile.mFruitNum)];
        } else {
            target = tile.iceCreamTarget;
        }

        mSoundManager.playSoundForSoundEvent(SoundEvent.ICE_CREAM_EXPLODE);

        // Check same fruit kind
        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow; i++) {

                // Check state
                if (!tileArray[i][j].empty && !tileArray[i][j].isExplode && tileArray[i][j].kind != 0) {

                    // Check is target fruit
                    if (target == tileArray[i][j].kind) {

                        // Add lightning animation
                        mAnimationManager.createLightning(tile, tileArray[i][j]);
                        mAnimationManager.createLightning_fruit(tileArray[i][j], false);

                        // Add match to target and explode around
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

    protected void explodeBigI(Tile[][] tileArray, Tile tile) {
        // Mark isExplode
        tile.isExplode = true;

        // Add animation
        mAnimationManager.createBigShakingAnim();
        mAnimationManager.createExplodeWave(tile);
        mSoundManager.playSoundForSoundEvent(SoundEvent.ICE_CREAM_EXPLODE);

        // Check same fruit kind
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                if (!tileArray[i][j].empty
                        && tileArray[i][j].wait == 0
                        && !tileArray[i][j].isExplode) {

                    // Add lightning animation
                    mAnimationManager.createLightning(tile, tileArray[i][j]);
                    mAnimationManager.createLightning_fruit(tileArray[i][j], false);

                    // Add match
                    tileArray[i][j].match++;

                    // Check fruit is special
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

    protected void transI(Tile[][] tileArray, Tile tile) {

        // Transform flag
        mIsTransf = true;
        mWaitingTime = 0;

        // Mark isExplode
        tile.isExplode = true;

        // Reset all the tile except the ice cream and target
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                if (!tileArray[i][j].isExplode)
                    tileArray[i][j].match = 0;
            }
        }

        mSoundManager.playSoundForSoundEvent(SoundEvent.ICE_CREAM_EXPLODE);

        // Check same fruit kind
        int target = tile.iceCreamTarget;
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {

                // Check state
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].isExplode
                        && tileArray[i][j].isFruit()) {

                    // Check ice cream target
                    if ((target == tileArray[i][j].kind) && !tileArray[i][j].special) {

                        // Add lightning animation
                        mAnimationManager.createLightning(tile, tileArray[i][j]);
                        mAnimationManager.createLightning_fruit(tileArray[i][j], true);

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
        if (row_temp < mRow - 1 && tileArray[row_temp + 1][col_temp].breakable
                && !tileArray[row_temp + 1][col_temp].lock) {
            tileArray[row_temp + 1][col_temp].match++;
        }

        // Check left
        if (col_temp > 0 && tileArray[row_temp][col_temp - 1].breakable
                && !tileArray[row_temp][col_temp - 1].lock) {
            tileArray[row_temp][col_temp - 1].match++;
        }

        // Check right
        if (col_temp < mColumn - 1 && tileArray[row_temp][col_temp + 1].breakable
                && !tileArray[row_temp][col_temp + 1].lock) {
            tileArray[row_temp][col_temp + 1].match++;
        }

    }

}
