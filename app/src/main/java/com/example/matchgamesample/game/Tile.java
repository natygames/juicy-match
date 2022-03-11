package com.example.matchgamesample.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.Sprite;

public class Tile extends Sprite {
    public int row = 0, col = 0;                // Tile position
    public int kind = 0, match = 0, ice = 0, layer = 0;       // Tile attribute
    public int bounce = 0, wait = 0, diagonal = 0;            // Tile moving
    public boolean invalid = false, empty = false, tube = false;  // Tile movement
    public boolean special = false, lock = false, breakable = false, honey = false;     // Tile attribute
    public boolean isExplode = false, isUpgrade = false, isAnimate = false, isChosen = false;       //Tile state
    public char direct = 'N', machine = 'N', specialCombine = 'N';
    public int iceCreamTarget = 0;
    public boolean entryPoint = false;

    private final int mFruitNum;

    public Tile(GameEngine gameEngine) {
        super(gameEngine);
        mFruitNum = gameEngine.mLevel.mFruitNum;
    }

    public void startGame() {
        if (!invalid
                && kind != TileUtils.STAR_FISH) {
            mImage.setAlpha(0f);
            mImage.setScaleY(0);
            mImage.setScaleX(0);
            mImage.animate().scaleX(1).scaleY(1).alpha(1).setStartDelay(800).setDuration((long) (500 * Math.random() + 200))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mImage.animate().setStartDelay(0);
                        }
                    });
        }
    }

    @Override
    public void onUpdate() {
        // We do this in GameController
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
        if (empty || lock || kind == 0 || (breakable && kind != TileUtils.CRACKER))
            return false;

        return true;
    }

    public boolean isFruit() {
        for (int i = 0; i < 5; i++) {
            if (kind == TileUtils.FRUITS[i])
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
        //Assign fruit kind
        kind = TileUtils.FRUITS[(int) (Math.random() * mFruitNum)];
    }
}
