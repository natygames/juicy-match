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

    private GameControllerState mState;
    private int mWaitingTime;
    private static final int WAITING_TIME = 2000;

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
        // Start the tile
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {
                mTileArray[i][j].startGame();
            }
        }

        mWaitingTime = 0;
        mState = GameControllerState.START_GAME;
        mGameStateAnim.startGameBoard();
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        switch (mState) {
            case START_GAME:
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > WAITING_TIME) {
                    // Start animation
                    mGameStateAnim.startGame(mGameEngine.mLevel.mLevelType);
                    mState = GameControllerState.WAITING;
                    mWaitingTime = 0;
                }
                break;
            case PLAY_GAME:
                mAlgorithm.update(mTileArray, elapsedMillis);
                break;
            case WAITING:
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > WAITING_TIME) {
                    mState = GameControllerState.PLAY_GAME;
                }
                break;
            case PLAYER_WIN:
                mGameStateAnim.gameOver(GameEvent.PLAYER_REACH_TARGET);
                mState = GameControllerState.BONUS_TIME;
                break;
            case PLAYER_LOSS:
                mGameStateAnim.gameOver(GameEvent.PLAYER_OUT_OF_MOVE);
                mState = GameControllerState.GAME_OVER;
                break;
            case BONUS_TIME:

                break;
            case GAME_PASS:

                break;
            case GAME_OVER:

                break;
        }
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
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }
                int touchCol = mInputController.mX_Down / mTileSize;
                int touchRow = mInputController.mY_Down / mTileSize;
                mTileArray[touchRow][touchCol].isChosen = true;
                break;
            case PLAYER_RELEASE:
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }
                int releaseCol = mInputController.mX_Down / mTileSize;
                int releaseRow = mInputController.mY_Down / mTileSize;
                mTileArray[releaseRow][releaseCol].isChosen = false;
                break;
            case PLAYER_MOVE:
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }
                playerSwap();
                break;
            case PLAYER_REACH_TARGET:
                mState = GameControllerState.PLAYER_WIN;
                break;
            case PLAYER_OUT_OF_MOVE:
                mState = GameControllerState.PLAYER_LOSS;
                break;
            case REFRESH:
                mState = GameControllerState.WAITING;
                mWaitingTime = 0;
                mGameStateAnim.refreshGame();
                break;
            case COMBO_4:
                mState = GameControllerState.WAITING;
                mWaitingTime = 0;
                mGameStateAnim.startCombo(GameEvent.COMBO_4);
                break;
            case COMBO_5:
                mState = GameControllerState.WAITING;
                mWaitingTime = 0;
                mGameStateAnim.startCombo(GameEvent.COMBO_5);
                break;
            case COMBO_6:
                mState = GameControllerState.WAITING;
                mWaitingTime = 0;
                mGameStateAnim.startCombo(GameEvent.COMBO_6);
                break;
        }
    }

    private void playerSwap() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mX_Down / mTileSize;
        int swapRow = mInputController.mY_Down / mTileSize;
        int swapCol2 = 0;
        int swapRow2 = 0;

        if (mInputController.mX_Down - mInputController.mX_Up < -50) {
            // Swap right
            if (swapCol >= mColumn - 1)
                return;
            swapCol2 = swapCol + 1;
            swapRow2 = swapRow;
        } else if (mInputController.mX_Down - mInputController.mX_Up > 50) {
            // Swap left
            if (swapCol <= 0)
                return;
            swapCol2 = swapCol - 1;
            swapRow2 = swapRow;
        } else if (mInputController.mY_Down - mInputController.mY_Up > 50) {
            // Swap up
            if (swapRow <= 0)
                return;
            swapCol2 = swapCol;
            swapRow2 = swapRow - 1;
        } else if (mInputController.mY_Down - mInputController.mY_Up < -50) {
            // Swap down
            if (swapRow >= mRow - 1)
                return;
            swapCol2 = swapCol;
            swapRow2 = swapRow + 1;
        } else {
            return;
        }

        // Update Algorithm state
        mAlgorithm.checkSpecialCombine(mTileArray[swapRow][swapCol], mTileArray[swapRow2][swapCol2]);
        mAlgorithm.swap(mTileArray, mTileArray[swapRow][swapCol], mTileArray[swapRow2][swapCol2]);
        mAlgorithm.swapCol = swapCol;
        mAlgorithm.swapRow = swapRow;
        mAlgorithm.swapCol2 = swapCol2;
        mAlgorithm.swapRow2 = swapRow2;
        mAlgorithm.isSwap = true;
        mAlgorithm.mMoveTile = true;

    }

}
