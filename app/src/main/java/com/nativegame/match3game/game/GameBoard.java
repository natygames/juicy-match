package com.nativegame.match3game.game;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.game.tile.Tile;
import com.nativegame.match3game.game.tile.TileUtils;
import com.nativegame.match3game.level.Level;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * GameBoard will generate new game from Level data
 */

public class GameBoard {
    private final GameEngine mGameEngine;
    private final Activity mActivity;
    private final Level mLevel;
    private final int mColumn, mRow, mTileSize;

    public GameBoard(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mActivity = gameEngine.mActivity;
        mLevel = gameEngine.mLevel;
        mColumn = gameEngine.mLevel.mColumn;
        mRow = gameEngine.mLevel.mRow;
        mTileSize = gameEngine.mImageSize;
    }

    public void createGridBoard(GridLayout grid_board) {
        /* Explanation:
         *
         * n for normal(default)
         * e for empty
         *
         * l for left margin
         * r for right margin
         * u for upper margin
         * d for down margin
         *
         * q for left upper corner
         * w for right upper corner
         * a for left down corner
         * s for right down corner
         *
         * L for left sole
         * R for right sole
         * U for upper sole
         * D for down sole
         *
         * h for horizontal
         * v for vertical
         *
         * o for one
         *
         */

        char[] board_char = mLevel.board.toCharArray();

        grid_board.setColumnCount(mColumn);
        grid_board.setRowCount(mRow);
        grid_board.getLayoutParams().width = mTileSize * mColumn;
        grid_board.getLayoutParams().height = mTileSize * mRow;

        // Implement blocks
        int size = board_char.length;
        for (int i = 0; i < size; i++) {
            ImageView block = new ImageView(mActivity);
            block.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
            switch (board_char[i]) {
                case ('n'):
                    block.setBackgroundResource(R.drawable.block);
                    break;
                case ('q'):
                    block.setBackgroundResource(R.drawable.block_corner);
                    break;
                case ('w'):
                    block.setBackgroundResource(R.drawable.block_corner);
                    block.setRotation(90);
                    break;
                case ('a'):
                    block.setBackgroundResource(R.drawable.block_corner);
                    block.setRotation(270);
                    break;
                case ('s'):
                    block.setBackgroundResource(R.drawable.block_corner);
                    block.setRotation(180);
                    break;
                case ('u'):
                    block.setBackgroundResource(R.drawable.block_margin);
                    break;
                case ('l'):
                    block.setBackgroundResource(R.drawable.block_margin);
                    block.setRotation(270);
                    break;
                case ('r'):
                    block.setBackgroundResource(R.drawable.block_margin);
                    block.setRotation(90);
                    break;
                case ('d'):
                    block.setBackgroundResource(R.drawable.block_margin);
                    block.setRotation(180);
                    break;
                case ('U'):
                    block.setBackgroundResource(R.drawable.block_sole);
                    break;
                case ('L'):
                    block.setBackgroundResource(R.drawable.block_sole);
                    block.setRotation(270);
                    break;
                case ('R'):
                    block.setBackgroundResource(R.drawable.block_sole);
                    block.setRotation(90);
                    break;
                case ('D'):
                    block.setBackgroundResource(R.drawable.block_sole);
                    block.setRotation(180);
                    break;
                case ('h'):
                    block.setBackgroundResource(R.drawable.block_bar);
                    break;
                case ('v'):
                    block.setBackgroundResource(R.drawable.block_bar);
                    block.setRotation(90);
                    break;
                case ('o'):
                    block.setBackgroundResource(R.drawable.block_one);
                    break;
                default:

            }
            grid_board.addView(block);
        }
    }

    public void createFruitBoard(GridLayout fruit_board, Tile[][] tileArray) {
        /* Explanation:
         *
         * n for normal
         * e for empty
         * t for tube
         * w for wait
         * z for strawberry
         * Z for lemon
         * x for starfish
         * h for horizontal special fruit
         * v for vertical special fruit
         * s for square special fruit
         * i for ice cream
         * c for cracker
         * o O q Q for cookie
         * p P for pie1
         * k K for pie2
         * a A for pie3
         * b B for pie4
         *
         */

        // Implement tiles
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                tileArray[i][j] = new Tile(mGameEngine);
                fruit_board.addView(tileArray[i][j].mImage);
            }
        }

        // Set fruit board
        char[][] fruit_char = new char[mRow][mColumn];
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                char type = mLevel.fruit.charAt(j + i * mColumn);
                fruit_char[i][j] = type;
            }
        }

        // Create fruit board
        fruit_board.setColumnCount(mColumn);
        fruit_board.setRowCount(mRow);
        fruit_board.getLayoutParams().width = mTileSize * mColumn;
        fruit_board.getLayoutParams().height = mTileSize * mRow;

        // Set tile's parameter
        for (int j = 0; j < mColumn; j++) {
            for (int i = mRow - 1; i >= 0; i--) {

                tileArray[i][j].row = i;
                tileArray[i][j].col = j;
                tileArray[i][j].x = j * mTileSize;
                tileArray[i][j].y = i * mTileSize;

                // Set fruit kind
                switch (fruit_char[i][j]) {
                    case ('n'):
                        break;
                    case ('h'):
                        tileArray[i][j].special = true;
                        tileArray[i][j].direct = 'H';
                        break;
                    case ('v'):
                        tileArray[i][j].special = true;
                        tileArray[i][j].direct = 'V';
                        break;
                    case ('s'):
                        tileArray[i][j].special = true;
                        tileArray[i][j].direct = 'S';
                        break;
                    case ('i'):
                        tileArray[i][j].special = true;
                        tileArray[i][j].direct = 'I';
                        tileArray[i][j].kind = TileUtils.ICE_CREAM;
                        continue;
                    case ('e'):
                        tileArray[i][j].empty = true;
                        tileArray[i][j].invalid = true;
                        continue;
                    case ('t'):
                        tileArray[i][j].empty = true;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].tube = true;
                        continue;
                    case ('z'):
                        tileArray[i][j].kind = R.drawable.strawberry;
                        continue;
                    case ('Z'):
                        tileArray[i][j].kind = R.drawable.lemon;
                        continue;
                    case ('x'):
                        tileArray[i][j].kind = TileUtils.STAR_FISH;
                        continue;
                    case ('w'):
                        tileArray[i][j].wait = 2;
                        continue;
                    case ('c'):
                        tileArray[i][j].kind = TileUtils.CRACKER;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('o'):
                        tileArray[i][j].kind = TileUtils.COOKIE;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('O'):
                        tileArray[i][j].kind = TileUtils.COOKIE;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 1;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('q'):
                        tileArray[i][j].kind = TileUtils.COOKIE;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 2;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('Q'):
                        tileArray[i][j].kind = TileUtils.COOKIE;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 3;
                        tileArray[i][j].breakable = true;
                        continue;
                    default:

                }

                // Set random fruit
                do {
                    tileArray[i][j].setRandomFruit();
                } while ((i < mRow - 2 && tileArray[i + 1][j].kind == tileArray[i][j].kind
                        && tileArray[i + 2][j].kind == tileArray[i][j].kind)
                        || (j >= 2 && tileArray[i][j - 1].kind == tileArray[i][j].kind
                        && tileArray[i][j - 2].kind == tileArray[i][j].kind));
            }
        }

    }

    public void createIceBoard(GridLayout ice_board,
                               ImageView[][] iceArray,
                               GridLayout ice_board2,
                               ImageView[][] iceArray2,
                               Tile[][] tileArray) {
        /* Explanation:
         *
         * n for no ice (default)
         * i for one layer ice
         * I for double layer ice
         *
         */

        char[] board_char = mLevel.board.toCharArray();
        char[][] ice_char = new char[mRow][mColumn];

        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                char type = mLevel.ice.charAt(j + i * mColumn);
                ice_char[i][j] = type;
            }
        }

        ice_board.setColumnCount(mColumn);
        ice_board.setRowCount(mRow);
        ice_board.getLayoutParams().width = mTileSize * mColumn;
        ice_board.getLayoutParams().height = mTileSize * mRow;
        ice_board2.setColumnCount(mColumn);
        ice_board2.setRowCount(mRow);
        ice_board2.getLayoutParams().width = mTileSize * mColumn;
        ice_board2.getLayoutParams().height = mTileSize * mRow;


        // Set ice image
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {

                // First layer
                ImageView ice = new ImageView(mActivity);
                ice.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));

                // Second layer
                ImageView ice2 = new ImageView(mActivity);
                ice2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));

                if (ice_char[i][j] == 'n') {
                    iceArray[i][j] = ice;
                    ice_board.addView(ice);
                    iceArray2[i][j] = ice2;
                    ice_board2.addView(ice2);
                    continue;
                }

                // Set ice
                tileArray[i][j].ice++;
                // Set image according to board
                switch (board_char[i * mColumn + j]) {
                    case ('n'):
                        ice.setBackgroundResource(R.drawable.ice);
                        break;
                    case ('q'):
                        ice.setBackgroundResource(R.drawable.ice_corner);
                        break;
                    case ('w'):
                        ice.setBackgroundResource(R.drawable.ice_corner);
                        ice.setRotation(90);
                        break;
                    case ('a'):
                        ice.setBackgroundResource(R.drawable.ice_corner);
                        ice.setRotation(270);
                        break;
                    case ('s'):
                        ice.setBackgroundResource(R.drawable.ice_corner);
                        ice.setRotation(180);
                        break;
                    case ('u'):
                        ice.setBackgroundResource(R.drawable.ice_margin);
                        break;
                    case ('l'):
                        ice.setBackgroundResource(R.drawable.ice_margin);
                        ice.setRotation(270);
                        break;
                    case ('r'):
                        ice.setBackgroundResource(R.drawable.ice_margin);
                        ice.setRotation(90);
                        break;
                    case ('d'):
                        ice.setBackgroundResource(R.drawable.ice_margin);
                        ice.setRotation(180);
                        break;
                    case ('U'):
                        ice.setBackgroundResource(R.drawable.ice_sole);
                        break;
                    case ('L'):
                        ice.setBackgroundResource(R.drawable.ice_sole);
                        ice.setRotation(270);
                        break;
                    case ('R'):
                        ice.setBackgroundResource(R.drawable.ice_sole);
                        ice.setRotation(90);
                        break;
                    case ('D'):
                        ice.setBackgroundResource(R.drawable.ice_sole);
                        ice.setRotation(180);
                        break;
                    case ('h'):
                        ice.setBackgroundResource(R.drawable.ice_bar);
                        break;
                    case ('v'):
                        ice.setBackgroundResource(R.drawable.ice_bar);
                        ice.setRotation(90);
                        break;
                    case ('o'):
                        ice.setBackgroundResource(R.drawable.ice_one);
                        break;
                    default:

                }

                if (ice_char[i][j] == 'I') {
                    // Set ice
                    tileArray[i][j].ice++;
                    // Set image according to board
                    switch (board_char[i * mColumn + j]) {
                        case ('n'):
                            ice2.setBackgroundResource(R.drawable.ice2);
                            break;
                        case ('q'):
                            ice2.setBackgroundResource(R.drawable.ice2_corner);
                            break;
                        case ('w'):
                            ice2.setBackgroundResource(R.drawable.ice2_corner);
                            ice2.setRotation(90);
                            break;
                        case ('a'):
                            ice2.setBackgroundResource(R.drawable.ice2_corner);
                            ice2.setRotation(270);
                            break;
                        case ('s'):
                            ice2.setBackgroundResource(R.drawable.ice2_corner);
                            ice2.setRotation(180);
                            break;
                        case ('u'):
                            ice2.setBackgroundResource(R.drawable.ice2_margin);
                            break;
                        case ('l'):
                            ice2.setBackgroundResource(R.drawable.ice2_margin);
                            ice2.setRotation(270);
                            break;
                        case ('r'):
                            ice2.setBackgroundResource(R.drawable.ice2_margin);
                            ice2.setRotation(90);
                            break;
                        case ('d'):
                            ice2.setBackgroundResource(R.drawable.ice2_margin);
                            ice2.setRotation(180);
                            break;
                        case ('U'):
                            ice2.setBackgroundResource(R.drawable.ice2_sole);
                            break;
                        case ('L'):
                            ice2.setBackgroundResource(R.drawable.ice2_sole);
                            ice2.setRotation(270);
                            break;
                        case ('R'):
                            ice2.setBackgroundResource(R.drawable.ice2_sole);
                            ice2.setRotation(90);
                            break;
                        case ('D'):
                            ice2.setBackgroundResource(R.drawable.ice2_sole);
                            ice2.setRotation(180);
                            break;
                        case ('h'):
                            ice2.setBackgroundResource(R.drawable.ice2_bar);
                            break;
                        case ('v'):
                            ice2.setBackgroundResource(R.drawable.ice2_bar);
                            ice2.setRotation(90);
                            break;
                        case ('o'):
                            ice2.setBackgroundResource(R.drawable.ice2_one);
                            break;
                        default:

                    }

                }

                iceArray[i][j] = ice;
                ice_board.addView(ice);
                iceArray2[i][j] = ice2;
                ice_board2.addView(ice2);

            }
        }

    }

    public void createAdvanceBoard(GridLayout advance_board,
                                   ImageView[][] advanceArray,
                                   Tile[][] tileArray) {
        /* Explanation:
         *
         * n for normal (default)
         * x for lock
         * A for arrow
         * I for tube
         *
         */

        char[][] advance_char = new char[mRow + 2][mColumn];
        for (int i = 0; i < mRow + 2; i++) {
            for (int j = 0; j < mColumn; j++) {
                char type = mLevel.advance.charAt(j + i * mColumn);
                advance_char[i][j] = type;
            }
        }

        // Arrow animation
        final Animation arrow_anim = AnimationUtils.loadAnimation(mActivity, R.anim.arrow_pulse);

        // Create advance board
        advance_board.setColumnCount(mColumn);
        advance_board.setRowCount(mRow + 2);
        advance_board.getLayoutParams().width = mTileSize * mColumn;
        advance_board.getLayoutParams().height = mTileSize * (mRow + 2);

        // Set advance image
        for (int i = 0; i < mRow + 2; i++) {
            for (int j = 0; j < mColumn; j++) {
                // First layer
                ImageView advance = new ImageView(mActivity);
                advance.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));

                // Set Advance
                switch (advance_char[i][j]) {
                    case ('n'):
                        advanceArray[i][j] = advance;
                        advance_board.addView(advance);
                        break;
                    case ('x'):
                        // Add lock
                        tileArray[i - 1][j].lock = true;
                        tileArray[i - 1][j].invalid = true;
                        advance.setBackgroundResource(R.drawable.lock);
                        advanceArray[i][j] = advance;
                        advance_board.addView(advance);
                        break;
                    case ('A'):
                        // Add entry point
                        tileArray[i - 2][j].entryPoint = true;
                        advance.setBackgroundResource(R.drawable.arrow);
                        advance.setAlpha(0.5f);
                        advance.startAnimation(arrow_anim);
                        advance.animate().translationY((float) -mTileSize / 2);
                        advance_board.addView(advance);
                        break;
                    case ('I'):
                        advance.setBackgroundResource(R.drawable.tube);
                        advance.setAlpha(0.7f);
                        advance_board.addView(advance);
                        break;
                    default:

                }

            }
        }
    }

}
