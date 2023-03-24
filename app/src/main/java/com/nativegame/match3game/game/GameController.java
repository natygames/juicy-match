package com.nativegame.match3game.game;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nativegame.match3game.R;
import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.game.algorithm.Algorithm;
import com.nativegame.match3game.ui.dialog.LossDialog;
import com.nativegame.match3game.ui.dialog.ScoreDialog;
import com.nativegame.match3game.ui.dialog.StartDialog;
import com.nativegame.match3game.ui.dialog.WinDialog;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.engine.event.Event;
import com.nativegame.nattyengine.engine.event.EventListener;
import com.nativegame.nattyengine.entity.Entity;
import com.nativegame.nattyengine.ui.GameActivity;

public class GameController extends Entity implements EventListener {

    private final GameActivity mParent;
    private final Algorithm mRegularTimeAlgorithm;
    private final Algorithm mBonusTimeAlgorithm;

    private GameState mState;
    private long mTotalTime;

    public GameController(GameActivity activity, Engine engine,
                          Algorithm regularTimeAlgorithm, Algorithm bonusTimeAlgorithm) {
        super(engine);
        mParent = activity;
        mRegularTimeAlgorithm = regularTimeAlgorithm;
        mBonusTimeAlgorithm = bonusTimeAlgorithm;
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        showGameView();
        mRegularTimeAlgorithm.initAlgorithm();
        mState = GameState.SHIFT_TILE;
        mTotalTime = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        switch (mState) {
            case WAITING:
                // We wait for the game event
                break;
            case SHIFT_TILE:
                mTotalTime += elapsedMillis;
                if (mTotalTime >= 1800) {
                    showStartDialog();
                    Sounds.GAME_START.play();
                    mState = GameState.SHOW_START_DIALOG;
                    mTotalTime = 0;
                }
                break;
            case SHOW_START_DIALOG:
                mTotalTime += elapsedMillis;
                if (mTotalTime >= 2500) {
                    dispatchEvent(GameEvent.START_GAME);
                    mState = GameState.WAITING;
                    mTotalTime = 0;
                }
                break;
            case SHOW_WIN_DIALOG:
                mTotalTime += elapsedMillis;
                if (mTotalTime >= 2500) {
                    mBonusTimeAlgorithm.startAlgorithm();
                    mState = GameState.WAITING;
                    mTotalTime = 0;
                }
                break;
            case SHOW_LOSS_DIALOG:
                mTotalTime += elapsedMillis;
                if (mTotalTime >= 2500) {
                    removeGameView();
                    mState = GameState.NAVIGATE_BACK;
                    mTotalTime = 0;
                }
                break;
            case SHOW_SCORE_DIALOG:
                mTotalTime += elapsedMillis;
                if (mTotalTime >= 1200) {
                    showScoreDialog();
                    mEngine.stopGame();
                    mState = GameState.WAITING;
                    mTotalTime = 0;
                }
                break;
            case NAVIGATE_BACK:
                mTotalTime += elapsedMillis;
                if (mTotalTime >= 1200) {
                    mParent.navigateBack();
                    mState = GameState.WAITING;
                    mTotalTime = 0;
                }
                break;
        }
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case PLAYER_SWAP:
            case PLAYER_USE_BOOSTER:
                mRegularTimeAlgorithm.startAlgorithm();
                break;
            case PULSE_GAME:
                pulseGameView();
                break;
            case SHAKE_GAME:
                shakeGameView();
                break;
            case GAME_WIN:
                mRegularTimeAlgorithm.removeAlgorithm();
                showWinDialog();
                Sounds.GAME_WIN.play();
                mState = GameState.SHOW_WIN_DIALOG;
                break;
            case GAME_OVER:
                showLossDialog();
                Sounds.GAME_OVER.play();
                mState = GameState.SHOW_LOSS_DIALOG;
                break;
            case BONUS_TIME_END:
                removeGameView();
                mState = GameState.SHOW_SCORE_DIALOG;
                break;
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void showGameView() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(mParent, R.anim.game_view_show);
                mParent.findViewById(R.id.game_view).startAnimation(animation);
            }
        });
    }

    private void removeGameView() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(mParent, R.anim.game_view_remove);
                mParent.findViewById(R.id.game_view).startAnimation(animation);
                mParent.findViewById(R.id.view_game_state).setVisibility(View.GONE);
                mParent.findViewById(R.id.view_game_booster).setVisibility(View.GONE);
            }
        });
    }

    private void pulseGameView() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(mParent, R.anim.game_view_pulse);
                mParent.findViewById(R.id.game_view).startAnimation(animation);
            }
        });
    }

    private void shakeGameView() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(mParent, R.anim.game_view_shake);
                mParent.findViewById(R.id.game_view).startAnimation(animation);
            }
        });
    }

    private void showStartDialog() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StartDialog startDialog = new StartDialog(mParent);
                mParent.showDialog(startDialog);
            }
        });
    }

    private void showWinDialog() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WinDialog winDialog = new WinDialog(mParent);
                mParent.showDialog(winDialog);
            }
        });
    }

    private void showLossDialog() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LossDialog lossDialog = new LossDialog(mParent);
                mParent.showDialog(lossDialog);
            }
        });
    }

    private void showScoreDialog() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ScoreDialog scoreDialog = new ScoreDialog(mParent);
                mParent.showDialog(scoreDialog);
            }
        });
    }
    //========================================================

}
