package com.example.matchgamesample.game;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameEventListener;
import com.example.matchgamesample.engine.GameObject;
import com.example.matchgamesample.engine.InputController;

public class GameController extends GameObject implements GameEventListener {
    private final Tile[][] tileArray;
    private final int mRow, mColumn;
    private final int mTileSize;
    private final InputController mInputController;
    private final MyAlgorithm myAlgorithm;

    public GameController(GameEngine gameEngine, Tile[][] tileMatrix) {
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
        myAlgorithm.update(tileArray);
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
        }
    }

    private void playerSwap() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mX_Down / mTileSize;
        int swapRow = mInputController.mY_Down / mTileSize;
        myAlgorithm.swapCol = swapCol;
        myAlgorithm.swapRow = swapRow;
        myAlgorithm.isSwap = true;

        if (mInputController.mX_Down - mInputController.mX_Up < -50) {
            // Swap right
            if (swapCol == mColumn - 1)
                return;
            myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow][swapCol + 1]);
            myAlgorithm.direction = 'R';
        } else if (mInputController.mX_Down - mInputController.mX_Up > 50) {
            // Swap left
            if (swapCol == 0)
                return;
            myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow][swapCol - 1]);
            myAlgorithm.direction = 'L';
        } else if (mInputController.mY_Down - mInputController.mY_Up > 50) {
            // Swap up
            if (swapRow == 0)
                return;
            myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow - 1][swapCol]);
            myAlgorithm.direction = 'U';
        } else if (mInputController.mY_Down - mInputController.mY_Up < -50) {
            // Swap down
            if (swapRow == mRow - 1)
                return;
            myAlgorithm.swap(tileArray, tileArray[swapRow][swapCol], tileArray[swapRow + 1][swapCol]);
            myAlgorithm.direction = 'D';
        }

    }

}
