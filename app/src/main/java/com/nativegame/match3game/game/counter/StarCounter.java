package com.nativegame.match3game.game.counter;

import android.graphics.drawable.ClipDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.engine.event.Event;
import com.nativegame.nattyengine.engine.event.EventListener;
import com.nativegame.nattyengine.entity.runnable.RunnableEntity;
import com.nativegame.nattyengine.ui.GameActivity;

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
        ImageView imageView = activity.findViewById(R.id.image_star_progress);
        mProgress = (ClipDrawable) imageView.getDrawable();
        mStarAnimation = AnimationUtils.loadAnimation(activity, R.anim.star_pulse);
        mProgressIncrement = 10000 / (Level.LEVEL_DATA.getMove() * 4);   // Progress max is 10000
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
            ImageView imageView = (ImageView) mActivity.findViewById(R.id.image_progress_star_01);
            imageView.setImageResource(R.drawable.star);
            imageView.startAnimation(mStarAnimation);
            mObtainedStar = 1;
        }
        if (mCurrentStar >= 2 && mObtainedStar == 1) {
            ImageView imageView = (ImageView) mActivity.findViewById(R.id.image_progress_star_02);
            imageView.setImageResource(R.drawable.star);
            imageView.startAnimation(mStarAnimation);
            mObtainedStar = 2;
        }
        if (mCurrentStar >= 3 && mObtainedStar == 2) {
            ImageView imageView = (ImageView) mActivity.findViewById(R.id.image_progress_star_03);
            imageView.setImageResource(R.drawable.star);
            imageView.startAnimation(mStarAnimation);
            mObtainedStar = 3;
        }
    }
    //========================================================

}
