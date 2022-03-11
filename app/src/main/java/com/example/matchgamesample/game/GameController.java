package com.example.matchgamesample.game;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameObject;
import com.example.matchgamesample.engine.InputController;
import com.example.matchgamesample.game.state.GameStateAnim;

public class GameController extends GameObject {
    private final GameEngine mGameEngine;
    private final Tile[][] mTileArray;
    private final int mRow, mColumn;
    private final int mTileSize;
    private final InputController mInputController;
    private final GameStateAnim mGameStateAnim;
    private MyAlgorithm mAlgorithm;

    public GameController(GameEngine gameEngine, Tile[][] TileArray) {
        mGameEngine = gameEngine;
        mTileArray = TileArray;
        mRow = gameEngine.mLevel.mRow;
        mColumn = gameEngine.mLevel.mColumn;
        mTileSize = gameEngine.mImageSize;
        mInputController = gameEngine.mInputController;
        mGameStateAnim = new GameStateAnim(gameEngine);
    }

    public void setMyAlgorithm(MyAlgorithm myAlgorithm) {
        mAlgorithm = myAlgorithm;
    }

    @Override
    public void startGame() {
        // Start animation
        mGameStateAnim.startGame(mGameEngine.mLevel.mLevelType);
        // Start the tile
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                mTileArray[i][j].startGame();
            }
        }
    }

    @Override
    public void onUpdate() {
        mAlgorithm.update(mTileArray);
    }

    @Override
    public void onDraw() {
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                mTileArray[i][j].onDraw();
            }
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        switch (gameEvents) {
            case PLAYER_TOUCH:
                if (!mAlgorithm.canPlayerSwap()) {
                    return;
                }
                int touchCol = mInputController.mX_Down / mTileSize;
                int touchRow = mInputController.mY_Down / mTileSize;
                mTileArray[touchRow][touchCol].isChosen = true;
                break;
            case PLAYER_RELEASE:
                if (!mAlgorithm.canPlayerSwap()) {
                    return;
                }
                int releaseCol = mInputController.mX_Down / mTileSize;
                int releaseRow = mInputController.mY_Down / mTileSize;
                mTileArray[releaseRow][releaseCol].isChosen = false;
                break;
            case PLAYER_MOVE:
                if (!mAlgorithm.canPlayerSwap()) {
                    return;
                }
                playerSwap();
                break;
            case PLAYER_WIN:
                mGameStateAnim.gameOver(GameEvent.PLAYER_WIN);
                break;
            case PLAYER_LOSS:
                mGameStateAnim.gameOver(GameEvent.PLAYER_LOSS);
                break;
            case BONUS_TIME:
                mGameStateAnim.startBonusTime();
                break;
            case REFRESH:
                mGameStateAnim.refreshGame();
                break;
            case COMBO_4:
                mGameStateAnim.startCombo(GameEvent.COMBO_4);
                break;
            case COMBO_5:
                mGameStateAnim.startCombo(GameEvent.COMBO_5);
                break;
            case COMBO_6:
                mGameStateAnim.startCombo(GameEvent.COMBO_6);
                break;
        }
    }

    private void playerSwap() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mX_Down / mTileSize;
        int swapRow = mInputController.mY_Down / mTileSize;
        mAlgorithm.swapCol = swapCol;
        mAlgorithm.swapRow = swapRow;
        mAlgorithm.isSwap = true;

        if (mInputController.mX_Down - mInputController.mX_Up < -50) {
            // Swap right
            if (swapCol == mColumn - 1)
                return;
            mAlgorithm.swapCol2 = swapCol + 1;
            mAlgorithm.swapRow2 = swapRow;
            mAlgorithm.direction = 'R';
            mAlgorithm.checkSpecialCombine(mTileArray[swapRow][swapCol], mTileArray[swapRow][swapCol + 1]);
            mAlgorithm.swap(mTileArray, mTileArray[swapRow][swapCol], mTileArray[swapRow][swapCol + 1]);
        } else if (mInputController.mX_Down - mInputController.mX_Up > 50) {
            // Swap left
            if (swapCol == 0)
                return;
            mAlgorithm.swapCol2 = swapCol - 1;
            mAlgorithm.swapRow2 = swapRow;
            mAlgorithm.direction = 'L';
            mAlgorithm.checkSpecialCombine(mTileArray[swapRow][swapCol], mTileArray[swapRow][swapCol - 1]);
            mAlgorithm.swap(mTileArray, mTileArray[swapRow][swapCol], mTileArray[swapRow][swapCol - 1]);
        } else if (mInputController.mY_Down - mInputController.mY_Up > 50) {
            // Swap up
            if (swapRow == 0)
                return;
            mAlgorithm.swapCol2 = swapCol;
            mAlgorithm.swapRow2 = swapRow - 1;
            mAlgorithm.direction = 'U';
            mAlgorithm.checkSpecialCombine(mTileArray[swapRow][swapCol], mTileArray[swapRow - 1][swapCol]);
            mAlgorithm.swap(mTileArray, mTileArray[swapRow][swapCol], mTileArray[swapRow - 1][swapCol]);
        } else if (mInputController.mY_Down - mInputController.mY_Up < -50) {
            // Swap down
            if (swapRow == mRow - 1)
                return;
            mAlgorithm.swapCol2 = swapCol;
            mAlgorithm.swapRow2 = swapRow + 1;
            mAlgorithm.direction = 'D';
            mAlgorithm.checkSpecialCombine(mTileArray[swapRow][swapCol], mTileArray[swapRow + 1][swapCol]);
            mAlgorithm.swap(mTileArray, mTileArray[swapRow][swapCol], mTileArray[swapRow + 1][swapCol]);
        }

    }

}
