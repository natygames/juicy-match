package com.nativegame.match3game.game.counter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.ClipDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.GameEvent;
import com.nativegame.match3game.engine.GameObject;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ScoreBarCounter extends GameObject {

    private final GameEngine mGameEngine;
    private final MainActivity mActivity;

    private int mPoints;
    private int mScoreBarLevel;
    private final int mFactor;
    private final ClipDrawable mScoreBar;
    private static final int POINTS_GAINED_PER_FRUIT = 10;

    private int mCurrentStar;
    private final RelativeLayout mStar1, mStar2, mStar3;
    private static final int STAR1_THRESHOLD = 2800;
    private static final int STAR2_THRESHOLD = 7200;
    private static final int STAR3_THRESHOLD = 10000;

    public ScoreBarCounter(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mActivity = (MainActivity) gameEngine.mActivity;
        mFactor = (10000 / (gameEngine.mLevel.mMove * 100));   // Progress bar max is 10000

        // Score bar
        mScoreBar = (ClipDrawable) gameEngine.mActivity.findViewById(R.id.score_bar).getBackground();

        // Star
        mStar1 = (RelativeLayout) gameEngine.mActivity.findViewById(R.id.layout_star1);
        mStar2 = (RelativeLayout) gameEngine.mActivity.findViewById(R.id.layout_star2);
        mStar3 = (RelativeLayout) gameEngine.mActivity.findViewById(R.id.layout_star3);

    }

    @Override
    public void startGame() {
        mPoints = 0;
        mScoreBarLevel = 0;
        mScoreBar.setLevel(0);
        mCurrentStar = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis) {

    }

    @Override
    public void onDraw() {
        if (mScoreBarLevel < mFactor * mPoints) {
            mScoreBarLevel += mFactor * POINTS_GAINED_PER_FRUIT;
            mScoreBar.setLevel(mScoreBarLevel);

            if (mCurrentStar < 1 && mScoreBarLevel >= STAR1_THRESHOLD) {
                mStar1.setBackgroundResource(R.drawable.star);
                createStarAnim(mStar1);
                mActivity.getSoundManager().playSoundForSoundEvent(SoundEvent.SCORE_GET_STAR);
                mGameEngine.mLevel.mStar = 1;
                mCurrentStar = 1;
            } else if (mCurrentStar < 2 && mScoreBarLevel >= STAR2_THRESHOLD) {
                mStar2.setBackgroundResource(R.drawable.star);
                createStarAnim(mStar2);
                mActivity.getSoundManager().playSoundForSoundEvent(SoundEvent.SCORE_GET_STAR);
                mGameEngine.mLevel.mStar = 2;
                mCurrentStar = 2;
            } else if (mCurrentStar < 3 && mScoreBarLevel >= STAR3_THRESHOLD) {
                mStar3.setBackgroundResource(R.drawable.star);
                createStarAnim(mStar3);
                mActivity.getSoundManager().playSoundForSoundEvent(SoundEvent.SCORE_GET_STAR);
                mGameEngine.mLevel.mStar = 3;
                mCurrentStar = 3;

                mGameEngine.removeGameObject(this);
            }

        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        if (gameEvents == GameEvent.PLAYER_SCORE) {
            mPoints += POINTS_GAINED_PER_FRUIT;
        }
    }

    private void createStarAnim(RelativeLayout star) {
        int view_width = star.getWidth();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ImageView sparkler = new ImageView(mGameEngine.mActivity);
                sparkler.setImageResource(R.drawable.flash_s_small);
                sparkler.setX((int) (view_width * 4 / 9));
                sparkler.setY((int) (view_width * 4 / 9));
                sparkler.setLayoutParams(new ViewGroup.LayoutParams((int) (view_width / 9), (int) (view_width / 9)));
                star.addView(sparkler);
                //Set animation
                sparkler.animate().setDuration((long) (250 * Math.random() + 250))
                        .alpha(0).rotation(Math.random() > 0.5 ? 180 : -180).scaleX(10).scaleY(10)
                        .x(j < 2 ? (float) ((int) (view_width * 4 / 9) - view_width * 3 * Math.random())
                                : (float) ((int) (view_width * 4 / 9) + view_width * 3 * Math.random()))
                        .y(i < 2 ? (float) ((int) (view_width * 4 / 9) - view_width * 3 * Math.random())
                                : (float) ((int) (view_width * 4 / 9) + view_width * 3 * Math.random()))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                star.removeView(sparkler);
                            }
                        });
            }
        }
    }

}
