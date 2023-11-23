package com.nativegame.juicymatch.game;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nativegame.juicymatch.MainActivity;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.ad.AdManager;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.game.algorithm.Algorithm;
import com.nativegame.juicymatch.game.tutorial.Tutorial;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.juicymatch.level.TutorialType;
import com.nativegame.juicymatch.ui.dialog.ErrorDialog;
import com.nativegame.juicymatch.ui.dialog.LossDialog;
import com.nativegame.juicymatch.ui.dialog.MoreMoveDialog;
import com.nativegame.juicymatch.ui.dialog.ScoreDialog;
import com.nativegame.juicymatch.ui.dialog.StartDialog;
import com.nativegame.juicymatch.ui.dialog.TutorialDialog;
import com.nativegame.juicymatch.ui.dialog.WinDialog;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.Entity;
import com.nativegame.natyengine.event.Event;
import com.nativegame.natyengine.event.EventListener;
import com.nativegame.natyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/02/23
 */


public class GameController extends Entity implements EventListener, AdManager.AdRewardListener {

    private final GameActivity mParent;
    private final Algorithm mRegularTimeAlgorithm;
    private final Algorithm mBonusTimeAlgorithm;

    private GameState mState;
    private long mTotalTime;
    private boolean mExtraLives = true;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public GameController(GameActivity activity, Engine engine,
                          Algorithm regularTimeAlgorithm, Algorithm bonusTimeAlgorithm) {
        super(engine);
        mParent = activity;
        mRegularTimeAlgorithm = regularTimeAlgorithm;
        mBonusTimeAlgorithm = bonusTimeAlgorithm;
    }
    //========================================================

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
            case SHOW_TUTORIAL:
                Tutorial tutorial = Level.LEVEL_DATA.getTutorialType().getTutorial(mEngine);
                tutorial.show(mParent);
                mState = GameState.WAITING;
                break;
            case SHOW_START_DIALOG:
                mTotalTime += elapsedMillis;
                if (mTotalTime >= 2500) {
                    // Check is current level has tutorial
                    if (Level.LEVEL_DATA.getTutorialType() != TutorialType.NONE) {
                        showTutorialDialog();
                    }
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
                    mEngine.stop();
                    mEngine.release();
                    showScoreDialog();
                    mState = GameState.WAITING;
                    mTotalTime = 0;
                }
                break;
            case NAVIGATE_BACK:
                mTotalTime += elapsedMillis;
                if (mTotalTime >= 1200) {
                    mEngine.stop();
                    mEngine.release();
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
            case PLAYER_OUT_OF_MOVE:
                // Check player has extra lives
                if (mExtraLives) {
                    showMoreMoveDialog();
                    mExtraLives = false;
                } else {
                    dispatchEvent(GameEvent.GAME_OVER);
                }
                break;
            case BONUS_TIME_END:
                removeGameView();
                mState = GameState.SHOW_SCORE_DIALOG;
                break;
        }
    }

    @Override
    public void onEarnReward() {
        // The ad will pause the game, so we resume it
        mEngine.resume();
        dispatchEvent(GameEvent.ADD_EXTRA_MOVES);
    }

    @Override
    public void onLossReward() {
        // The ad will pause the game, so we resume it
        mEngine.resume();
        dispatchEvent(GameEvent.GAME_OVER);
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

    private void showTutorialDialog() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TutorialDialog tutorialDialog = new TutorialDialog(mParent) {
                    @Override
                    public void showTutorial() {
                        mState = GameState.SHOW_TUTORIAL;
                    }
                };
                mParent.showDialog(tutorialDialog);
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

    private void showMoreMoveDialog() {
        mParent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MoreMoveDialog moreMoveDialog = new MoreMoveDialog(mParent) {
                    @Override
                    public void showAd() {
                        showRewardedAd();
                    }

                    @Override
                    public void quit() {
                        dispatchEvent(GameEvent.GAME_OVER);
                    }
                };
                mParent.showDialog(moreMoveDialog);
            }
        });
    }

    private void showRewardedAd() {
        // Show rewarded ad
        AdManager adManager = ((MainActivity) mParent).getAdManager();
        adManager.setListener(this);
        boolean isConnect = adManager.showRewardAd();
        // Check connection
        if (isConnect) {
            // Pause the game when loading ad, or the pause dialog will show
            mEngine.pause();
        } else {
            // Show error dialog if no internet connect
            ErrorDialog dialog = new ErrorDialog(mParent) {
                @Override
                public void retry() {
                    adManager.requestAd();
                    showRewardedAd();
                }

                @Override
                public void quit() {
                    dispatchEvent(GameEvent.GAME_OVER);
                }
            };
            mParent.showDialog(dialog);
        }
    }
    //========================================================

}
