package com.example.matchgamesample.level;

import android.content.Context;
import com.example.matchgamesample.game.TileID;

/* This class let you design mLevel
 * Assign mLevel data with Level class
 * Use getmLevel() to get data
 */

public class LevelManager {
    //Current mLevel
    private final Level mLevel;
    //Board
    private char[] board_char;
    private char[][] fruit_char;
    private char[][] advance_char;
    private char[][] ice_char;

    public LevelManager(Context context, int currentLevel) {
        XMLPuller XMLPuller = new XMLPuller(context, currentLevel);
        mLevel = XMLPuller.getLevel();
        initializeParameter(mLevel);
        initializeGameBoard(mLevel);
    }

    public Level getLevel() {
        return this.mLevel;
    }

    public char[] getBoardChar() {
        return this.board_char;
    }

    public char[][] getFruitChar() {
        return this.fruit_char;
    }

    public char[][] getAdvanceChar() {
        return this.advance_char;
    }

    public char[][] getIceChar(){
        return this.ice_char;
    }

    private void initializeParameter(Level level) {
        // Set fruitID array
        int size = level.fruitNum;
        if(size == 0){
            // Default fruit count is 4
            size = 4;
        }
        TileID.FRUITS_TO_USE = new int[size];
        System.arraycopy(TileID.FRUITS, 0, TileID.FRUITS_TO_USE, 0, size);
    }

    private void initializeGameBoard(Level level) {
        //Set grid board
        board_char = level.board.toCharArray();

        //Set fruit board
        fruit_char = new char[level.row][level.column];
        for (int i = 0; i < level.row; i++) {
            for (int j = 0; j < level.column; j++) {
                char type = level.fruit.charAt(j + i * level.column);
                fruit_char[i][j] = type;
            }
        }

        //Set advance board (if necessary)
        if(level.advance.length() > 0) {
            advance_char = new char[level.row + 2][level.column];
            for (int i = 0; i < level.row + 2; i++) {
                for (int j = 0; j < level.column; j++) {
                    char type = level.advance.charAt(j + i * level.column);
                    advance_char[i][j] = type;
                }
            }
        }

        //Set ice board (if necessary)
        if(level.ice.length() > 0){
            ice_char = new char[level.row][level.column];
            for (int i = 0; i < level.row; i++) {
                for (int j = 0; j < level.column; j++) {
                    char type = level.ice.charAt(j + i * level.column);
                    ice_char[i][j] = type;
                }
            }
        }
    }

}
