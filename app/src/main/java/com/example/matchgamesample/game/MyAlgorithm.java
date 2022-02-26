package com.example.matchgamesample.game;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.InputController;
import com.example.matchgamesample.level.Level;

public class MyAlgorithm {
    private GameEngine mGameEngine;
    private InputController mInputController;

    private final Level mLevel;
    private final int row, column;
    private final int tileSize;
    private Tile[][] mTileMatrix;

    //Fruit's moving SPEED
    private int SPEED = 15;
    private int WAIT_TIME = 300;   //300

    //Flag
    private boolean isSwap = false, isSwapBack = false, isMoving = false, isWin = false, isBonusTime = false, isRunning = true;
    private boolean showHint = true, isTransf = false, isShowGuide = true;
    private boolean isUsingHand = false, isUsingSpoon = false, isUsingHammer = false, isUsingProps = false;
    private boolean matchFinding = false, waitFinding = false, fallingFinding = false;
    private char direction = 'N';
    private int isWait = 1;
    private int combo = 0;
    private int winningStage = 0;

    public MyAlgorithm(GameEngine gameEngine, Level level, Tile[][] tileMatrix) {
        this.mGameEngine = gameEngine;
        this.mInputController = gameEngine.mInputController;
        this.mLevel = level;
        this.row = level.row;
        this.column = level.column;
        this.tileSize = gameEngine.mImageSize;
        this.mTileMatrix = tileMatrix;
    }

    public void run(Tile[][] tileMatrix) {

    }

    public void swap(Tile tile1, Tile tile2) {
        if(tile1.invalid || tile2.invalid)
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
        mTileMatrix[tile1.row][tile1.col] = tile1;
        mTileMatrix[tile2.row][tile2.col] = tile2;

    }

    public void useHand() {

    }

    public void useHammer() {

    }

    public void useBomb() {

    }

}
