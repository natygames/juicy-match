package com.example.matchgamesample.game;

import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.matchgamesample.MainActivity;
import com.example.matchgamesample.R;
import com.example.matchgamesample.effect.sound.SoundEvent;
import com.example.matchgamesample.effect.sound.SoundManager;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameObject;
import com.example.matchgamesample.engine.InputController;
import com.example.matchgamesample.fragment.MapFragment;
import com.example.matchgamesample.fragment.WinDialogFragment;
import com.example.matchgamesample.game.algorithm.BonusTimeAlgorithm;
import com.example.matchgamesample.game.algorithm.GameAlgorithm;
import com.example.matchgamesample.game.booster.BoosterManager;
import com.example.matchgamesample.game.tile.Tile;
import com.example.matchgamesample.game.tile.TileUtils;

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
        mStateAnimation.startGameBoard();
        mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
        mState = GameControllerState.START_GAME;
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        switch (mState) {
            case START_GAME:
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > WAITING_TIME) {
                    // Start animation
                    mStateAnimation.startGame(mGameEngine.mLevel.mLevelType);
                    mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                    mSoundManager.playSoundForSoundEvent(SoundEvent.GAME_INTRO);

                    // We disable player move when waiting
                    mState = GameControllerState.WAITING;
                    mWaitingTime = 0;

                    // Start hint
                    mGameEngine.onGameEvent(GameEvent.START_HINT);
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
                    mSoundManager.playSoundForSoundEvent(SoundEvent.GAME_INTRO);
                    mState = GameControllerState.PLAY_GAME;
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
            case GAME_OVER:
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > 2300) {
                    showGameOverDialog();
                    mWaitingTime = 0;
                }
                break;
            case GAME_COMPLETE:
                mWaitingTime += elapsedMillis;
                if (mWaitingTime > 700) {
                    showGameCompleteDialog();
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
                int touchCol = mInputController.mX_Down / mTileSize;
                int touchRow = mInputController.mY_Down / mTileSize;
                mTileArray[touchRow][touchCol].isChosen = true;
                break;
            case PLAYER_RELEASE:
                // Check current state
                if (mState != GameControllerState.PLAY_GAME || !mAlgorithm.canPlayerSwap()) {
                    return;
                }
                int releaseCol = mInputController.mX_Down / mTileSize;
                int releaseRow = mInputController.mY_Down / mTileSize;
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
                mState = GameControllerState.GAME_OVER;
                break;
            case BONUS_TIME_COMPLETE:
                clearView(0);
                mState = GameControllerState.GAME_COMPLETE;
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
                    mAlgorithm.mShowHint = true;
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
                    mAlgorithm.mShowHint = true;
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
                    mAlgorithm.mShowHint = true;
                }

                break;
            case PLAYER_USE_BOMB:
                useBomb();
                break;
        }
    }

    private void useHammer() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mX_Down / mTileSize;
        int swapRow = mInputController.mY_Down / mTileSize;

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
        mAlgorithm.mShowHint = true;
    }

    private void useGloves() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mX_Down / mTileSize;
        int swapRow = mInputController.mY_Down / mTileSize;
        int swapCol2 = 0;
        int swapRow2 = 0;

        if (mInputController.mX_Down - mInputController.mX_Up < -SWAP_THRESHOLD) {
            // Swap right
            if (swapCol >= mColumn - 1)
                return;
            swapCol2 = swapCol + 1;
            swapRow2 = swapRow;
        } else if (mInputController.mX_Down - mInputController.mX_Up > SWAP_THRESHOLD) {
            // Swap left
            if (swapCol <= 0)
                return;
            swapCol2 = swapCol - 1;
            swapRow2 = swapRow;
        } else if (mInputController.mY_Down - mInputController.mY_Up > SWAP_THRESHOLD) {
            // Swap up
            if (swapRow <= 0)
                return;
            swapCol2 = swapCol;
            swapRow2 = swapRow - 1;
        } else if (mInputController.mY_Down - mInputController.mY_Up < -SWAP_THRESHOLD) {
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
        mAlgorithm.mShowHint = true;
    }

    private void useBomb() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mX_Down / mTileSize;
        int swapRow = mInputController.mY_Down / mTileSize;

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
        mAlgorithm.mShowHint = true;
    }

    private void swapTile() {
        // Get the tile player press from inputController
        int swapCol = mInputController.mX_Down / mTileSize;
        int swapRow = mInputController.mY_Down / mTileSize;
        int swapCol2 = 0;
        int swapRow2 = 0;

        if (mInputController.mX_Down - mInputController.mX_Up < -SWAP_THRESHOLD) {
            // Swap right
            if (swapCol >= mColumn - 1)
                return;
            swapCol2 = swapCol + 1;
            swapRow2 = swapRow;
        } else if (mInputController.mX_Down - mInputController.mX_Up > SWAP_THRESHOLD) {
            // Swap left
            if (swapCol <= 0)
                return;
            swapCol2 = swapCol - 1;
            swapRow2 = swapRow;
        } else if (mInputController.mY_Down - mInputController.mY_Up > SWAP_THRESHOLD) {
            // Swap up
            if (swapRow <= 0)
                return;
            swapCol2 = swapCol;
            swapRow2 = swapRow - 1;
        } else if (mInputController.mY_Down - mInputController.mY_Up < -SWAP_THRESHOLD) {
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

        // Update Algorithm state
        mAlgorithm.checkSpecialCombine(mTileArray[swapRow][swapCol], mTileArray[swapRow2][swapCol2]);
        mAlgorithm.swap(mTileArray, mTileArray[swapRow][swapCol], mTileArray[swapRow2][swapCol2]);
        mAlgorithm.mSwapCol = swapCol;
        mAlgorithm.mSwapRow = swapRow;
        mAlgorithm.mSwapCol2 = swapCol2;
        mAlgorithm.mSwapRow2 = swapRow2;
        mAlgorithm.mSwapping = true;
        mAlgorithm.mMoveTile = true;
        mAlgorithm.mShowHint = true;

    }

    private void addSkipButton() {
        mSkipButton.animate().setStartDelay(300).setDuration(400)
                .scaleX(2).scaleY(2).alpha(1).setInterpolator(new OvershootInterpolator());
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

    private void showGameOverDialog() {
        // We stop the game here, or the pause dialog will pop up
        mGameEngine.stopGame();
        MainActivity mainActivity = (MainActivity) mGameEngine.mActivity;
        mainActivity.navigateToFragment(new MapFragment());
    }

    private void showGameCompleteDialog() {
        // We stop the game here, or the pause dialog will pop up
        mGameEngine.stopGame();
        MainActivity mainActivity = (MainActivity) mGameEngine.mActivity;
        mainActivity.navigateToFragment(WinDialogFragment
                .newInstance(mGameEngine.mLevel.mLevel,
                        mGameEngine.mLevel.mScore,
                        mGameEngine.mLevel.mStar));
    }

}
