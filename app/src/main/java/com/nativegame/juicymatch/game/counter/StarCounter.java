package com.nativegame.juicymatch.game.counter;

import android.graphics.drawable.ClipDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.runnable.RunnableEntity;
import com.nativegame.natyengine.event.Event;
import com.nativegame.natyengine.event.EventListener;
import com.nativegame.natyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class StarCounter extends RunnableEntity implements EventListener {

    private static final int STAR_PROGRESS_01 = 2800;
    private static final int STAR_PROGRESS_02 = 7200;
    private static final int STAR_PROGRESS_03 = 10000;

    private final ClipDrawable mProgress;
    private final Animation mStarAnimation;
    private final int mProgressIncrement;

    private int mCurrentProgress;
    private int mCurrentStar;
    private int mObtainedStar;
    private boolean mIsUpdateStar = false;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public StarCounter(GameActivity activity, Engine engine) {
        super(activity, engine);
        ImageView imageProgress = activity.findViewById(R.id.image_star_progress);
        mProgress = (ClipDrawable) imageProgress.getDrawable();
        mStarAnimation = AnimationUtils.loadAnimation(activity, R.anim.star_pulse);
        mProgressIncrement = 10000 / (Level.LEVEL_DATA.getMove() * 10);   // Progress max is 10000
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mCurrentProgress = 0;
        mCurrentStar = 0;
        mObtainedStar = 0;
        setPostRunnable(true);
    }

    @Override
    protected void onUpdateRunnable() {
        mProgress.setLevel(mCurrentProgress);
        if (mIsUpdateStar) {
            updateView();
            mIsUpdateStar = false;
        }
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case PLAYER_SCORE:
            case ADD_BONUS:
                mCurrentProgress += mProgressIncrement;
                updateStar();
                setPostRunnable(true);
                break;
            case GAME_OVER:
            case BONUS_TIME_END:
                removeFromGame();
                break;
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void updateStar() {
        if (mCurrentStar < 1 && mCurrentProgress >= STAR_PROGRESS_01) {
            mCurrentStar = 1;
            Level.LEVEL_DATA.setStar(mCurrentStar);
            Sounds.ADD_STAR.play();
            mIsUpdateStar = true;
        } else if (mCurrentStar < 2 && mCurrentProgress >= STAR_PROGRESS_02) {
            mCurrentStar = 2;
            Level.LEVEL_DATA.setStar(mCurrentStar);
            Sounds.ADD_STAR.play();
            mIsUpdateStar = true;
        } else if (mCurrentStar < 3 && mCurrentProgress >= STAR_PROGRESS_03) {
            mCurrentStar = 3;
            Level.LEVEL_DATA.setStar(mCurrentStar);
            Sounds.ADD_STAR.play();
            mIsUpdateStar = true;
        }
    }

    private void updateView() {
        if (mCurrentStar >= 1 && mObtainedStar == 0) {
            ImageView imageStar = (ImageView) mActivity.findViewById(R.id.image_progress_star_01);
            imageStar.setImageResource(R.drawable.ui_star);
            imageStar.startAnimation(mStarAnimation);
            mObtainedStar = 1;
        }
        if (mCurrentStar >= 2 && mObtainedStar == 1) {
            ImageView imageStar = (ImageView) mActivity.findViewById(R.id.image_progress_star_02);
            imageStar.setImageResource(R.drawable.ui_star);
            imageStar.startAnimation(mStarAnimation);
            mObtainedStar = 2;
        }
        if (mCurrentStar >= 3 && mObtainedStar == 2) {
            ImageView imageStar = (ImageView) mActivity.findViewById(R.id.image_progress_star_03);
            imageStar.setImageResource(R.drawable.ui_star);
            imageStar.startAnimation(mStarAnimation);
            mObtainedStar = 3;
        }
    }
    //========================================================

}
