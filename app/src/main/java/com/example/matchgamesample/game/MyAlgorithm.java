package com.example.matchgamesample.game;

public class MyAlgorithm {
    private final int row, column;
    private final int tileSize;

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

    public MyAlgorithm(int row, int column, int tileSize){
        this.row = row;
        this.column = column;
        this.tileSize = tileSize;
    }

    public void run(Tile[][] tileMatrix){

    }

}
