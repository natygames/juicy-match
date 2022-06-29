package com.nativegame.match3game.game.tile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.Sprite;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * Tile contain tile's attribute and helper method
 */

public class Tile extends Sprite {
    public int row = 0, col = 0;   // Tile position
    public int kind = 0, match = 0, ice = 0, layer = 0;   // Tile attribute
    public int bounce = 0, wait = 0, diagonal = 0;   // Tile moving
    public boolean invalid = false, empty = false, tube = false;   // Tile movement
    public boolean special = false, lock = false, breakable = false;   // Tile attribute
    public boolean isExplode = false, isUpgrade = false, isAnimate = false, isChosen = false;   //Tile state
    public char direct = 'N', specialCombine = 'N';
    public int iceCreamTarget = 0;
    public boolean entryPoint = false;

    public static int mSpeed;
    public static int mFruitNum;

    public Tile(GameEngine gameEngine) {
        super(gameEngine);
        mSpeed = mWidth / 4;
        mFruitNum = gameEngine.mLevel.mFruitNum;
    }

    public void startGame() {
        if (!invalid
                && kind != TileUtils.STAR_FISH) {
            mImage.setAlpha(0f);
            mImage.setScaleY(0);
            mImage.setScaleX(0);
            mImage.animate().scaleX(1).scaleY(1).alpha(1).setStartDelay(650).setDuration((long) (500 * Math.random() + 200))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mImage.animate().setStartDelay(0);
                        }
                    });
        }
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        int diff_x = 0, diff_y = 0;
        for (int i = 0; i < mSpeed; i++) {
            diff_x = x - col * mWidth;
            diff_y = y - row * mWidth;

            if (diff_x != 0) {
                // Check diagonal swapping position
                if (y >= diagonal * mWidth)
                    //Go left or right
                    x -= diff_x / Math.abs(diff_x);
            } else {
                diagonal = 0;
            }

            if (diff_y != 0) {

                if (bounce == 0) {
                    if (diff_y <= -mWidth * 4) {
                        bounce = 2;
                    } else if (diff_y <= -mWidth) {
                        bounce = 1;
                    }
                }

                y -= diff_y / Math.abs(diff_y);
            }
        }

    }

    @Override
    public void onDraw() {
        mImage.setX(x);
        mImage.setY(y);

        // Check state
        if (!empty) {
            if (wait != 0 || isAnimate) {
                // Show nothing if tile is waiting or already exploded
                mImage.setImageResource(0);
            } else {
                if (isChosen && isMovable()) {
                    mImage.setImageResource(TileUtils.getChosenFruit(this));
                } else if (special) {
                    mImage.setImageResource(TileUtils.getSpecialFruit(this));
                } else {
                    mImage.setImageResource(TileUtils.getFruit(this));
                }
            }
        }

    }

    public boolean isMoving() {
        return (x - col * mWidth != 0) || (y - row * mHeight != 0);
    }

    public boolean isMovable() {
        return !invalid && kind != 0;
    }

    public boolean isFruit() {
        int size = TileUtils.FRUITS.length;
        for (int i = 0; i < size; i++) {
            if (kind == TileUtils.FRUITS[i])
                return true;
        }
        return false;
    }

    // This method is for score calculation and ice detect
    public boolean isUnblockFruitOrIceCream() {
        if (!invalid && (isFruit() || kind == TileUtils.ICE_CREAM)) {
            return true;
        }
        return false;
    }

    public void reset() {
        match = 0;
        isUpgrade = false;
        breakable = false;
        isExplode = false;
        if (special) {
            special = false;
            direct = 'N';
            specialCombine = 'N';
            iceCreamTarget = 0;
        }
        setRandomFruit();
    }

    public void setRandomFruit() {
        // Assign fruit kind
        kind = TileUtils.FRUITS[(int) (Math.random() * mFruitNum)];
    }
}
