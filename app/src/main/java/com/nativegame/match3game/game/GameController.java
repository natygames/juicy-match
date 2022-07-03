package com.nativegame.match3game.game;

import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.effect.sound.SoundManager;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.GameEvent;
import com.nativegame.match3game.engine.GameObject;
import com.nativegame.match3game.engine.InputController;
import com.nativegame.match3game.fragment.MapFragment;
import com.nativegame.match3game.fragment.WinDialogFragment;
import com.nativegame.match3game.game.algorithm.BonusTimeAlgorithm;
import com.nativegame.match3game.game.algorithm.GameAlgorithm;
import com.nativegame.match3game.game.booster.BoosterManager;
import com.nativegame.match3game.game.tile.Tile;
import com.nativegame.match3game.game.tile.TileUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * GameController control game event and algorithm
 */

public class GameController extends GameObject {
    private final GameEngine mGameEngine;
    private final MainActivity mActivity;
    private final Tile[][] mTileArray;
    private final int mRow, mColumn;
    private final int mTileSize;
    private final InputController mInputController;
    private final StateAnimation mStateAnimation;
    private GameAlgorithm mAlgorithm;
    private final BonusTimeAlgorithm mBonusTimeAlgorithm;
    private final BoosterManager mBoosterManager;
    private final SoundManager mSoundManager;
    private GameControllerState mState;
    private final Button mSkipButton;
    private int mWaitingTime;
    private int mStartPhase;

    private static final int WAITING_TIME = 1500;
    private static final int REFRESH_TIME = 800;
    private static final int SWAP_THRESHOLD = 50;

    public GameController(GameEngine gameEngine, Tile[][] TileArray) {
        mGameEngine = gameEngine;
        mActivity = (MainActivity) gameEngine.mActivity;
        mTileArray = TileArray;
        mRow = gameEngine.mLevel.mRow;
        mColumn = gameEngine.mLevel.mColumn;
        mTileSize = gameEngine.mImageSize;
        mInputController = gameEngine.mInputController;
        mStateAnimation = new StateAnimation(gameEngine);
        mBonusTimeAlgorithm = new BonusTimeAlgorithm(gameEngine);
        mBoosterManager = new BoosterManager(gameEngine);
        mSoundManager = ((MainActivity) gameEngine.mActivity).getSoundManager();
        mSkipButton = (Button) mGameEngine.mActivity.findViewById(R.id.btn_skip);
    }

    public void setMyAlgorithm(GameAlgorithm gameAlgorithm) {
        mAlgorithm = gameAlgorithm;
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
        mStartPhase = 1;
        mStateAnimation.startGameBoard();
        mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
        mState = GameControllerState.START_GAME;
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        switch (mState) {
            case START_GAME:
                mWaitingTime += elapsedMillis;
                if (mStartPhase == 1 && mWaitingTime > WAITING_TIME) {
                    // Start animation
                    mStateAnimation.startGame(mGameEngine.mLevel.mLevelType);
                    mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                    mSoundManager.playSoundForSoundEvent(SoundEvent.GAME_INTRO);
                    mStartPhase = 2;
                    mWaitingTime = 0;
                }
                if (mStartPhase == 2 && mWaitingTime > 2000) {
                    // Start hint
                    mAlgorithm.resumeHint();

                    // We disable player move when waiting
                    mState = GameControllerState.PLAY_GAME;
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
                    mWaitingTime = 0;
                }
                break;
            case REFRESHING:
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > REFRESH_TIME) {
                    mAlgorithm.refresh(mTileArray);
                    mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_APPEAR);
                    // Set interval for the next refresh
                    mState = GameControllerState.WAITING;
                    mWaitingTime = 0;
                }
                break;
            case BONUS_TIME:
                mBonusTimeAlgorithm.update(mTileArray, elapsedMillis);
                break;
            case BONUS_TIME_WAITING:
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > 1800) {
                    mStateAnimation.startBonusTime();
                    mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                    addSkipButton();
                    mState = GameControllerState.BONUS_TIME;
                    mWaitingTime = 0;
                }
                break;
            case NAVIGATE_TO_MAP:
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > 2300) {
                    navigateToMap();
                    mWaitingTime = 0;
                }
                break;
            case NAVIGATE_TO_WIN_DIALOG:
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > 700) {
                    navigateToWinDialog();
                    mWaitingTime = 0;
                }
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
                // Check current state
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }
                int touchCol = mInputController.mXDown / mTileSize;
                int touchRow = mInputController.mYDown / mTileSize;
                mTileArray[touchRow][touchCol].isChosen = true;
                break;
            case PLAYER_RELEASE:
                // Check current state
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }
                int releaseCol = mInputController.mXDown / mTileSize;
                int releaseRow = mInputController.mYDown / mTileSize;
                mTileArray[releaseRow][releaseCol].isChosen = false;
                break;
            case PLAYER_MOVE:
                // Check current state
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }
                swapTile();
                break;
            case PLAYER_REACH_TARGET:
                mStateAnimation.gameOver(GameEvent.PLAYER_REACH_TARGET);
                mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                mSoundManager.playSoundForSoundEvent(SoundEvent.GAME_WIN);
                mState = GameControllerState.BONUS_TIME_WAITING;
                break;
            case PLAYER_OUT_OF_MOVE:
                mStateAnimation.gameOver(GameEvent.PLAYER_OUT_OF_MOVE);
                mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                mSoundManager.playSoundForSoundEvent(SoundEvent.GAME_OVER);
                clearView(1600);
                mState = GameControllerState.NAVIGATE_TO_MAP;
                break;
            case BONUS_TIME_COMPLETE:
                clearView(0);
                mState = GameControllerState.NAVIGATE_TO_WIN_DIALOG;
                break;
            case REFRESH:
                mStateAnimation.refreshGame();
                mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                mAlgorithm.playRefreshAnimation(mTileArray);
                mState = GameControllerState.REFRESHING;
                break;
            case COMBO_4:
                mStateAnimation.startCombo(GameEvent.COMBO_4);
                mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                mState = GameControllerState.WAITING;
                break;
            case COMBO_5:
                mStateAnimation.startCombo(GameEvent.COMBO_5);
                mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                mState = GameControllerState.WAITING;
                break;
            case COMBO_6:
                mStateAnimation.startCombo(GameEvent.COMBO_6);
                mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                mState = GameControllerState.WAITING;
                break;
            case PLAYER_PRESS_HAMMER:
                // Check current state
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }

                // Check player enable or disable the item
                if (!mInputController.mUsingHammer) {
                    // Notifying inputController and show highlight
                    mInputController.mUsingHammer = true;
                    mBoosterManager.showHighlight();
                } else {
                    // Notifying inputController and clear highlight
                    mInputController.mUsingHammer = false;
                    mBoosterManager.clearHighlight();

                    // Resume hint
                    mAlgorithm.resumeHint();
                }

                break;
            case PLAYER_USE_HAMMER:
                useHammer();
                break;
            case PLAYER_PRESS_GLOVES:
                // Check current state
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }

                if (!mInputController.mUsingGloves) {
                    // Notifying inputController and show highlight
                    mInputController.mUsingGloves = true;
                    mBoosterManager.showHighlight();
                } else {
                    // Notifying inputController and clear highlight
                    mInputController.mUsingGloves = false;
                    mBoosterManager.clearHighlight();

                    // Resume hint
                    mAlgorithm.resumeHint();
                }

                break;
            case PLAYER_USE_GLOVES:
                useGloves();
                break;
            case PLAYER_PRESS_BOMB:
                // Check current state
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }

                if (!mInputController.mUsingBomb) {
                    // Notifying inputController and show highlight
                    mInputController.mUsingBomb = true;
                    mBoosterManager.showHighlight();
                } else {
                    // Notifying inputController and clear highlight
                    mInputController.mUsingBomb = false;
                    mBoosterManager.clearHighlight();

                    // Resume hint
                    mAlgorithm.resumeHint();
                }

                break;
            case PLAYER_USE_BOMB:
                useBomb();
                break;
        }
    }

    private void useHammer() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mXDown / mTileSize;
        int swapRow = mInputController.mYDown / mTileSize;

        // Check is valid tile
        if (mTileArray[swapRow][swapCol].empty
                || (mTileArray[swapRow][swapCol].kind == TileUtils.STAR_FISH && !mTileArray[swapRow][swapCol].lock))
            return;

        // We wait for the animation
        mState = GameControllerState.WAITING;

        // Play booster animation
        mBoosterManager.useHammer(mTileArray[swapRow][swapCol]);

        // Resume booster state
        mInputController.mUsingHammer = false;

        // Resume hint
        mAlgorithm.resumeHint();
    }

    private void useGloves() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mXDown / mTileSize;
        int swapRow = mInputController.mYDown / mTileSize;
        int swapCol2 = 0;
        int swapRow2 = 0;

        if (mInputController.mXDown - mInputController.mXUp < -SWAP_THRESHOLD) {
            // Swap right
            if (swapCol >= mColumn - 1)
                return;
            swapCol2 = swapCol + 1;
            swapRow2 = swapRow;
        } else if (mInputController.mXDown - mInputController.mXUp > SWAP_THRESHOLD) {
            // Swap left
            if (swapCol <= 0)
                return;
            swapCol2 = swapCol - 1;
            swapRow2 = swapRow;
        } else if (mInputController.mYDown - mInputController.mYUp > SWAP_THRESHOLD) {
            // Swap up
            if (swapRow <= 0)
                return;
            swapCol2 = swapCol;
            swapRow2 = swapRow - 1;
        } else if (mInputController.mYDown - mInputController.mYUp < -SWAP_THRESHOLD) {
            // Swap down
            if (swapRow >= mRow - 1)
                return;
            swapCol2 = swapCol;
            swapRow2 = swapRow + 1;
        } else {
            return;
        }

        if (!mTileArray[swapRow][swapCol].isMovable()
                || !mTileArray[swapRow2][swapCol2].isMovable()) {
            return;
        }

        // We swap these tiles without losing move
        mAlgorithm.swap(mTileArray, mTileArray[swapRow][swapCol], mTileArray[swapRow2][swapCol2]);
        mAlgorithm.mMoveTile = true;
        // We disable mAlgorithm.mSwapping, so it won't swap back

        // We wait for the animation
        mState = GameControllerState.WAITING;

        // Play booster animation
        mBoosterManager.useGloves(mTileArray[swapRow2][swapCol2], mTileArray[swapRow][swapCol]);

        // Resume booster state
        mInputController.mUsingGloves = false;

        // Resume hint
        mAlgorithm.resumeHint();
    }

    private void useBomb() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mXDown / mTileSize;
        int swapRow = mInputController.mYDown / mTileSize;

        // Check is valid tile
        if (mTileArray[swapRow][swapCol].empty)
            return;

        // We wait for the animation
        mState = GameControllerState.WAITING;

        // Play booster animation
        mBoosterManager.useBomb(mTileArray[swapRow][swapCol], mTileArray);

        // Resume booster state
        mInputController.mUsingBomb = false;

        // Resume hint
        mAlgorithm.resumeHint();
    }

    private void swapTile() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mXDown / mTileSize;
        int swapRow = mInputController.mYDown / mTileSize;
        int swapCol2 = 0;
        int swapRow2 = 0;

        if (mInputController.mXDown - mInputController.mXUp < -SWAP_THRESHOLD) {
            // Swap right
            if (swapCol >= mColumn - 1)
                return;
            swapCol2 = swapCol + 1;
            swapRow2 = swapRow;
        } else if (mInputController.mXDown - mInputController.mXUp > SWAP_THRESHOLD) {
            // Swap left
            if (swapCol <= 0)
                return;
            swapCol2 = swapCol - 1;
            swapRow2 = swapRow;
        } else if (mInputController.mYDown - mInputController.mYUp > SWAP_THRESHOLD) {
            // Swap up
            if (swapRow <= 0)
                return;
            swapCol2 = swapCol;
            swapRow2 = swapRow - 1;
        } else if (mInputController.mYDown - mInputController.mYUp < -SWAP_THRESHOLD) {
            // Swap down
            if (swapRow >= mRow - 1)
                return;
            swapCol2 = swapCol;
            swapRow2 = swapRow + 1;
        } else {
            return;
        }

        if (!mTileArray[swapRow][swapCol].isMovable()
                || !mTileArray[swapRow2][swapCol2].isMovable()) {
            return;
        }

        // Notify Algorithm
        mAlgorithm.playerSwap(mTileArray, swapRow, swapCol, swapRow2, swapCol2);
    }

    private void addSkipButton() {
        mSkipButton.animate().setStartDelay(300).setDuration(400)
                .scaleX(1).scaleY(1).alpha(1).setInterpolator(new OvershootInterpolator());
        mSkipButton.setVisibility(View.VISIBLE);
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBonusTimeAlgorithm.skip();
                mSkipButton.setVisibility(View.INVISIBLE);
            }
        });

        ConstraintLayout board_button = (ConstraintLayout) mGameEngine.mActivity.findViewById(R.id.board_button);
        board_button.setVisibility(View.INVISIBLE);
    }

    private void clearView(int delay) {
        // Broad disappear
        mStateAnimation.clearGameBoard(delay);
        mSkipButton.setVisibility(View.GONE);
    }

    private void navigateToMap() {
        // We stop the game here, or the pause dialog will pop up
        mGameEngine.stopGame();
        MainActivity mainActivity = (MainActivity) mGameEngine.mActivity;
        mainActivity.navigateToFragment(new MapFragment());
    }

    private void navigateToWinDialog() {
        // We stop the game here, or the pause dialog will pop up
        mGameEngine.stopGame();
        MainActivity mainActivity = (MainActivity) mGameEngine.mActivity;
        mainActivity.navigateToFragment(WinDialogFragment
                .newInstance(mGameEngine.mLevel.mLevel,
                        mGameEngine.mLevel.mScore,
                        mGameEngine.mLevel.mStar));
    }

}
