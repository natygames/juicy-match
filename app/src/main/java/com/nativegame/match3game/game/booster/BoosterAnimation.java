package com.nativegame.match3game.game.booster;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.effect.animation.AnimationManager;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.effect.sound.SoundManager;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.game.tile.Tile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class BoosterAnimation {
    private final GameEngine mGameEngine;
    private final AnimationManager mAnimationManager;
    private final SoundManager mSoundManager;
    private final RelativeLayout mEffectBoard;
    private final int mTileSize;

    private final OvershootInterpolator mOvershootInterpolator = new OvershootInterpolator();
    private final AnticipateInterpolator mAnticipateInterpolator = new AnticipateInterpolator();

    private static final int SHOW_ITEM_TIME = 500;
    private static final int MOVE_ITEM_TIME = 500;

    public BoosterAnimation(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mAnimationManager = new AnimationManager(gameEngine);
        mSoundManager = ((MainActivity) mGameEngine.mActivity).getSoundManager();
        mEffectBoard = gameEngine.mActivity.findViewById(R.id.effect_board);
        mTileSize = mGameEngine.mImageSize;
    }

    public void playHammerAnimation(int x, int y) {
        // Init hammer image
        ImageView hammer = new ImageView(mGameEngine.mActivity);
        hammer.setImageResource(R.drawable.hammer);

        // We place the item in middle first
        hammer.setX(mEffectBoard.getWidth() * 0.5f - mTileSize);
        hammer.setY(mEffectBoard.getHeight() * 0.5f - mTileSize);

        // Set size
        hammer.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 2, mTileSize * 2));

        // Init start value
        hammer.setScaleX(0);
        hammer.setScaleY(0);
        hammer.setAlpha(0.5f);

        // Add hammer image to screen
        mEffectBoard.addView(hammer);

        // Play animation
        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_APPEAR);
        hammer.animate().setDuration(SHOW_ITEM_TIME).scaleX(3).scaleY(3).alpha(1)
                .setInterpolator(mOvershootInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                        hammer.animate().setDuration(MOVE_ITEM_TIME).x(x + mTileSize * 0.3f).y(y - mTileSize * 2)
                                .scaleX(1).scaleY(1).setInterpolator(mAnticipateInterpolator)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        hammer.setPivotX(mTileSize * 2);
                                        hammer.setPivotY(mTileSize * 2);
                                        hammer.animate().setStartDelay(200).setDuration(300).rotation(-45)
                                                .setInterpolator(mAnticipateInterpolator)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        hammer.animate().setStartDelay(0).setDuration(400).rotation(0)
                                                                .setInterpolator(mOvershootInterpolator)
                                                                .setListener(new AnimatorListenerAdapter() {
                                                                    @Override
                                                                    public void onAnimationEnd(Animator animation) {
                                                                        mEffectBoard.removeView(hammer);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });

    }

    public void playGlovesAnimation(int xStart, int yStart, int xEnd, int yEnd) {
        // Init gloves image and animation
        ImageView gloves = new ImageView(mGameEngine.mActivity);
        gloves.setImageResource(R.drawable.gloves);

        // We place the item in middle first
        gloves.setX(mEffectBoard.getWidth() * 0.5f - mTileSize);
        gloves.setY(mEffectBoard.getHeight() * 0.5f - mTileSize);

        //Set size
        gloves.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 2, mTileSize * 2));

        // Init start value
        gloves.setScaleX(0);
        gloves.setScaleY(0);
        gloves.setAlpha(0.5f);

        // Add gloves image to screen
        mEffectBoard.addView(gloves);

        // Play animation
        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_APPEAR);
        gloves.animate().setDuration(SHOW_ITEM_TIME).scaleX(3).scaleY(3).alpha(1)
                .setInterpolator(mOvershootInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                        gloves.animate().setDuration(MOVE_ITEM_TIME).x(xStart - mTileSize * 0.25f).y(yStart - mTileSize * 0.5f)
                                .scaleX(1).scaleY(1).setInterpolator(mAnticipateInterpolator)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        gloves.animate().setDuration(200).rotation(-45)
                                                .setInterpolator(mAnticipateInterpolator)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        gloves.animate().setDuration(400)
                                                                .x(xEnd - mTileSize * 0.25f).y(yEnd - mTileSize * 0.5f)
                                                                .setListener(new AnimatorListenerAdapter() {
                                                                    @Override
                                                                    public void onAnimationEnd(Animator animation) {
                                                                        mEffectBoard.removeView(gloves);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    public void playBombAnimation(int x, int y) {
        // Init hammer image
        ImageView bomb = new ImageView(mGameEngine.mActivity);
        bomb.setImageResource(R.drawable.bomb);

        // We place the item in middle first
        bomb.setX(mEffectBoard.getWidth() * 0.5f - mTileSize);
        bomb.setY(mEffectBoard.getHeight() * 0.5f - mTileSize);

        // Set size
        bomb.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 2, mTileSize * 2));

        // Init start value
        bomb.setScaleX(0);
        bomb.setScaleY(0);
        bomb.setAlpha(0.5f);

        // Add bomb image to screen
        mEffectBoard.addView(bomb);

        // Play animation
        mSoundManager.playSoundForSoundEvent(SoundEvent.FRUIT_APPEAR);
        bomb.animate().setDuration(SHOW_ITEM_TIME).scaleX(3).scaleY(3).alpha(1)
                .setInterpolator(mOvershootInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP1);
                        bomb.animate().setDuration(MOVE_ITEM_TIME).x(x - mTileSize * 0.5f).y(y - mTileSize * 3)
                                .scaleX(1).scaleY(1).setInterpolator(mAnticipateInterpolator)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        bomb.animate().setStartDelay(200).setDuration(300).alpha(0).scaleX(0.7f).scaleY(0.7f)
                                                .x(x - mTileSize * 0.5f).y(y - mTileSize * 0.5f)
                                                .setInterpolator(mAnticipateInterpolator)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        playExplodeAnimation(x, y);
                                                        mEffectBoard.removeView(bomb);
                                                    }
                                                });
                                    }
                                });
                    }
                });

    }

    private void playExplodeAnimation(int x, int y) {
        Tile tile = new Tile(mGameEngine);
        tile.x = x;
        tile.y = y;
        mAnimationManager.createScaleAnim();
        mAnimationManager.createSquareFlash(tile);
        mSoundManager.playSoundForSoundEvent(SoundEvent.SQUARE_EXPLODE);
    }

}
