package com.example.matchgamesample.game;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.matchgamesample.R;
import com.example.matchgamesample.level.Level;

/* This class let you design fruit board
 * Assign fruit board's block with char[]
 * Use createGameBoard() to get char[] value
 */

public class Game {
    private final Context context;
    private final Level mLevel;
    private final int column, row, tileSize;

    public Game(Context context, Level level, int tileSize) {
        this.context = context;
        mLevel = level;
        this.column = level.column;
        this.row = level.row;
        this.tileSize = tileSize;
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

        grid_board.setColumnCount(column);
        grid_board.setRowCount(row);
        grid_board.getLayoutParams().width = tileSize * column;
        grid_board.getLayoutParams().height = tileSize * row;

        //Implement blocks
        for (int x = 0; x < board_char.length; x++) {
            ImageView block = new ImageView(context);
            block.setLayoutParams(new ViewGroup.LayoutParams(tileSize, tileSize));
            switch (board_char[x]) {
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

        //Set fruit board
        char[][] fruit_char = new char[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                char type = mLevel.fruit.charAt(j + i * column);
                fruit_char[i][j] = type;
            }
        }

        //Create fruit board
        fruit_board.setColumnCount(column);
        fruit_board.setRowCount(row);
        fruit_board.getLayoutParams().width = tileSize * column;
        fruit_board.getLayoutParams().height = tileSize * row;

        //Set tile's parameter
        for (int j = 0; j < column; j++) {
            for (int i = row - 1; i >= 0; i--) {

                tileArray[i][j].row = i;
                tileArray[i][j].col = j;
                tileArray[i][j].x = j * tileSize;
                tileArray[i][j].y = i * tileSize;

                //Set fruit kind
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
                        tileArray[i][j].kind = TileID.ICE_CREAM;
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
                        tileArray[i][j].kind = TileID.STAR_FISH;
                        continue;
                    case ('w'):
                        tileArray[i][j].wait = 2;
                        continue;
                    case ('c'):
                        tileArray[i][j].kind = TileID.CRACKER;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('o'):
                        tileArray[i][j].kind = TileID.COOKIE;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('O'):
                        tileArray[i][j].kind = TileID.COOKIE;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 1;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('q'):
                        tileArray[i][j].kind = TileID.COOKIE;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 2;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('Q'):
                        tileArray[i][j].kind = TileID.COOKIE;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 3;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('p'):
                        tileArray[i][j].kind = TileID.PIE_1;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 2;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('k'):
                        tileArray[i][j].kind = TileID.PIE_2;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 2;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('a'):
                        tileArray[i][j].kind = TileID.PIE_3;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 2;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('b'):
                        tileArray[i][j].kind = TileID.PIE_4;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 2;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('P'):
                        tileArray[i][j].kind = TileID.PIE_1;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 4;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('K'):
                        tileArray[i][j].kind = TileID.PIE_2;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 4;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('A'):
                        tileArray[i][j].kind = TileID.PIE_3;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 4;
                        tileArray[i][j].breakable = true;
                        continue;
                    case ('B'):
                        tileArray[i][j].kind = TileID.PIE_4;
                        tileArray[i][j].invalid = true;
                        tileArray[i][j].layer = 4;
                        tileArray[i][j].breakable = true;
                        continue;
                    default:

                }

                // Set random fruit
                do {
                    tileArray[i][j].setRandomFruit();
                } while ((i < row - 2 && tileArray[i + 1][j].kind == tileArray[i][j].kind && tileArray[i + 2][j].kind == tileArray[i][j].kind)
                        || (j >= 2 && tileArray[i][j - 1].kind == tileArray[i][j].kind && tileArray[i][j - 2].kind == tileArray[i][j].kind));
            }
        }

    }
}
