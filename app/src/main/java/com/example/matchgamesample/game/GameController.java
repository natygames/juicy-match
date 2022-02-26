package com.example.matchgamesample.game;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameEventListener;
import com.example.matchgamesample.engine.GameObject;
import com.example.matchgamesample.engine.InputController;
import com.example.matchgamesample.level.Level;

public class GameController extends GameObject implements GameEventListener {
    private final Tile[][] mTileMatrix;
    private final int mRow, mColumn;
    private final int mTileSize;
    private final InputController mInputController;

    private final MyAlgorithm myAlgorithm;

    public GameController(GameEngine gameEngine, Tile[][] tileMatrix, Level level) {
        mTileMatrix = tileMatrix;
        mRow = level.row;
        mColumn = level.column;
        mTileSize = gameEngine.mImageSize;
        mInputController = gameEngine.mInputController;

        myAlgorithm = new MyAlgorithm(gameEngine, level, tileMatrix);
    }

    @Override
    public void startGame() {
        // Set up the tile
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                mTileMatrix[i][j].startGame();
            }
        }
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Run my algorithm
        myAlgorithm.run(mTileMatrix);

        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                mTileMatrix[i][j].onUpdate(elapsedMillis, gameEngine);
            }
        }
    }

    @Override
    public void onDraw() {
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                mTileMatrix[i][j].onDraw();
            }
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        switch (gameEvents){
            case Swap:
                playerSwap();
                break;
            case PropsHand:
                myAlgorithm.useHand();
                break;
            case PropsHammer:
                myAlgorithm.useHammer();
                break;
            case PropsBomb:
                myAlgorithm.useBomb();
                break;
        }
    }

    private void playerSwap() {
        // Get the tile player press from inputController
        int tileColumn = mInputController.mX_Down / mTileSize;
        int tileRow = mInputController.mY_Down / mTileSize;

        if(mInputController.mX_Down - mInputController.mX_Up < -50){
            // Swap right
            if(tileColumn == mColumn - 1)
                return;
            myAlgorithm.swap(mTileMatrix[tileRow][tileColumn], mTileMatrix[tileRow][tileColumn + 1]);
        } else if (mInputController.mX_Down - mInputController.mX_Up > 50){
            // Swap left
            if(tileColumn == 0)
                return;
            myAlgorithm.swap(mTileMatrix[tileRow][tileColumn], mTileMatrix[tileRow][tileColumn - 1]);
        } else if (mInputController.mY_Down - mInputController.mY_Up > 50){
            // Swap up
            if(tileRow == 0)
                return;
            myAlgorithm.swap(mTileMatrix[tileRow][tileColumn], mTileMatrix[tileRow - 1][tileColumn]);
        } else if (mInputController.mY_Down - mInputController.mY_Up < -50){
            // Swap down
            if(tileRow == mRow - 1)
                return;
            myAlgorithm.swap(mTileMatrix[tileRow][tileColumn], mTileMatrix[tileRow + 1][tileColumn]);
        }

    }

}
