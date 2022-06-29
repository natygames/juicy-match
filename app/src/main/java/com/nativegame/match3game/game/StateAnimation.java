package com.nativegame.match3game.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.GameEvent;
import com.nativegame.match3game.level.LevelType;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class StateAnimation {

    private final MainActivity mActivity;
    private final RelativeLayout mRoot;
    private final int mTileSize;
    // Duration
    private static final int FALL_TIME_SHORT = 300;
    private static final int FALL_TIME_LONG = 500;
    private static final int PAUSE_TIME_SHORT = 200;
    private static final int PAUSE_TIME_LONG = 400;
    private static final int RETRY_TIME = 500;
    // Interpolator
    private final AnticipateInterpolator mAnticipateInterpolator = new AnticipateInterpolator();
    private final OvershootInterpolator mOvershootInterpolator = new OvershootInterpolator();
    private final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();

    public StateAnimation(GameEngine gameEngine) {
        mActivity = (MainActivity) gameEngine.mActivity;
        mTileSize = gameEngine.mImageSize;
        mRoot = gameEngine.mActivity.findViewById(R.id.guide_board);
    }

    public void startGameBoard() {
        View view = mActivity.findViewById(R.id.board_frame);
        view.setX(-mTileSize * 10);
        view.animate().setDuration(600).x(0)
                .setInterpolator(mOvershootInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mActivity.getSoundManager().playSoundForSoundEvent(SoundEvent.FRUIT_APPEAR);
                    }
                });

    }

    public void clearGameBoard(int delay) {
        View view = mActivity.findViewById(R.id.board_frame);
        view.animate().setStartDelay(delay).setDuration(600).x(view.getWidth())
                .setInterpolator(mAnticipateInterpolator);

        ConstraintLayout board_info = (ConstraintLayout) mActivity.findViewById(R.id.board_game);
        board_info.animate().setStartDelay(delay).setDuration(0).alpha(0);
        ConstraintLayout board_button = (ConstraintLayout) mActivity.findViewById(R.id.board_button);
        board_button.animate().setStartDelay(delay).setDuration(0).alpha(0);
    }

    public void startGame(LevelType type) {
        // Add black screen
        createBlackScreen();

        // Add board
        LinearLayout board = new LinearLayout(mActivity);
        board.setOrientation(LinearLayout.HORIZONTAL);
        board.setGravity(Gravity.CENTER);
        board.setBackgroundResource(R.drawable.guide_board);
        board.setX(0);
        board.setY(-mTileSize * 4);
        board.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTileSize * 4));
        mRoot.addView(board);
        // Add guide
        ImageView guide = new ImageView(mActivity);
        if (type == LevelType.LEVEL_TYPE_SCORE) {
            guide.setImageResource(R.drawable.guide_taeget_score);
        } else if (type == LevelType.LEVEL_TYPE_ICE) {
            guide.setImageResource(R.drawable.guide_target_ice);
            ImageView ice = new ImageView(mActivity);
            ice.setBackgroundResource(R.drawable.ice);
            ice.setLayoutParams(new ViewGroup.LayoutParams((int) (mTileSize * 1.5), (int) (mTileSize * 1.5)));
            ice.animate().setStartDelay(200).setDuration(200).rotation(-30)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ice.animate().setStartDelay(0).setDuration(400).rotation(30)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            ice.animate().setDuration(400).rotation(-30)
                                                    .setListener(new AnimatorListenerAdapter() {
                                                        @Override
                                                        public void onAnimationEnd(Animator animation) {
                                                            ice.animate().setDuration(400).rotation(30)
                                                                    .setListener(new AnimatorListenerAdapter() {
                                                                        @Override
                                                                        public void onAnimationEnd(Animator animation) {
                                                                            animation.cancel();
                                                                        }
                                                                    });
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
            board.addView(ice);
        } else {
            guide.setImageResource(R.drawable.guide_target_collect);
        }
        guide.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 7, mTileSize * 2));
        board.addView(guide);

        // Set animation (total 2000 ms)
        board.animate().setDuration(FALL_TIME_LONG)
                .y((float) (mRoot.getHeight() / 2 - mTileSize * 2))
                .setInterpolator(mOvershootInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        board.animate().setDuration(PAUSE_TIME_LONG * 2).alpha(1)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        mActivity.getSoundManager().playSoundForSoundEvent(SoundEvent.SWEEP2);
                                        //Set retry
                                        board.animate().setDuration(RETRY_TIME).y(mRoot.getHeight())
                                                .setInterpolator(mAnticipateInterpolator)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        mRoot.removeAllViews();
                                                    }
                                                });
                                    }
                                });
                    }
                });

    }

    public void refreshGame() {
        // Add guide
        ImageView refresh = new ImageView(mActivity);
        refresh.setImageResource(R.drawable.guide_refresh);
        refresh.setX((int) (mRoot.getWidth() / 2 - mTileSize * 4));
        refresh.setY(-mTileSize * 3);
        refresh.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 8, mTileSize * 3));
        mRoot.addView(refresh);

        // Set animation (total 1600 ms)
        refresh.animate().setDuration(FALL_TIME_LONG)
                .y((float) (mRoot.getHeight() / 2 - mTileSize * 1.5))
                .setInterpolator(mOvershootInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        refresh.animate().setDuration(PAUSE_TIME_LONG).alpha(1)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        mActivity.getSoundManager().playSoundForSoundEvent(SoundEvent.SWEEP2);
                                        //Set retry
                                        refresh.animate().setDuration(RETRY_TIME).y(-mTileSize * 3)
                                                .setInterpolator(mAnticipateInterpolator)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        mRoot.removeView(refresh);
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    public void startCombo(GameEvent gameEvent) {
        // Add guide
        ImageView guide = new ImageView(mActivity);

        switch (gameEvent) {
            case COMBO_4:
                guide.setImageResource(R.drawable.guide_nice);
                break;
            case COMBO_5:
                guide.setImageResource(R.drawable.guide_great);
                break;
            case COMBO_6:
                guide.setImageResource(R.drawable.guide_wonderful);
                break;
        }

        guide.setX((int) (mRoot.getWidth() / 2 - mTileSize * 4));
        guide.setY(-mTileSize * 3);
        guide.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 8, mTileSize * 3));
        mRoot.addView(guide);

        // Set animation (total 1300 ms)
        guide.animate().setDuration(FALL_TIME_SHORT)
                .y((float) (mRoot.getHeight() / 2 - mTileSize * 1.5))
                .setInterpolator(mDecelerateInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //Set retry
                        guide.animate().setDuration(PAUSE_TIME_LONG).alpha(1)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        mActivity.getSoundManager().playSoundForSoundEvent(SoundEvent.SWEEP2);
                                        //Set retry
                                        guide.animate().setDuration(RETRY_TIME).y(-mTileSize * 3)
                                                .setInterpolator(mAnticipateInterpolator)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        mRoot.removeView(guide);
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    public void startBonusTime() {
        // Add guide
        ImageView bonusTime = new ImageView(mActivity);
        bonusTime.setImageResource(R.drawable.guide_bonus);
        bonusTime.setX((int) (mRoot.getWidth() / 2 - mTileSize * 4));
        bonusTime.setY(-mTileSize * 6);
        bonusTime.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 8, mTileSize * 6));
        mRoot.addView(bonusTime);

        // Set animation
        bonusTime.animate().setDuration(FALL_TIME_LONG)
                .y((float) (mRoot.getHeight() / 2 - mTileSize * 3))
                .setInterpolator(mOvershootInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        bonusTime.animate().setDuration(PAUSE_TIME_SHORT).alpha(1)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        mActivity.getSoundManager().playSoundForSoundEvent(SoundEvent.SWEEP2);
                                        //Set retry
                                        bonusTime.animate().setDuration(RETRY_TIME).y(-mTileSize * 6)
                                                .setInterpolator(mAnticipateInterpolator)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        mRoot.removeView(bonusTime);
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    public void gameOver(GameEvent gameEvent) {
        // Add black screen
        createBlackScreen();
        // Add board
        RelativeLayout board = new RelativeLayout(mActivity);
        board.setGravity(Gravity.CENTER);
        board.setBackgroundResource(R.drawable.guide_board);
        board.setX(0);
        board.setY(-mTileSize * 4);
        board.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTileSize * 4));
        mRoot.addView(board);
        // Add guide
        ImageView guide = new ImageView(mActivity);

        switch (gameEvent) {
            case PLAYER_REACH_TARGET:
                guide.setImageResource(R.drawable.guide_win);
                break;
            case PLAYER_OUT_OF_MOVE:
                guide.setImageResource(R.drawable.guide_loss);
                break;
        }

        guide.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 7, mTileSize * 2));
        board.addView(guide);

        // Set animation
        board.animate().setDuration(FALL_TIME_LONG).y((float) (mRoot.getHeight() / 2 - mTileSize * 2))
                .setInterpolator(mOvershootInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        board.animate().setDuration(PAUSE_TIME_LONG).alpha(1)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        mActivity.getSoundManager().playSoundForSoundEvent(SoundEvent.SWEEP2);
                                        //Set retry
                                        board.animate().setDuration(RETRY_TIME).y(mRoot.getHeight())
                                                .setInterpolator(mAnticipateInterpolator)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        mRoot.removeAllViews();
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private void createBlackScreen() {
        // Add black screen
        ImageView black_screen = new ImageView(mActivity);
        black_screen.setImageResource(R.color.black);
        black_screen.setImageAlpha(150);
        black_screen.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRoot.addView(black_screen);
    }

}
