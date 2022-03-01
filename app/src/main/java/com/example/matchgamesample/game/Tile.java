package com.example.matchgamesample.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.Sprite;

public class Tile extends Sprite {
    private GameEngine mGameEngine;
    private int mTileSize;
    private static final int SPEED = 8;

    public int row = 0, col = 0;                // Tile position
    public int kind = 0, match = 0, ice = 0, layer = 0;       // Tile attribute
    public int bounce = 0, wait = 0, diagonal = 0;            // Tile moving
    public boolean invalid = false, empty = false, tube = false, special = false, lock = false, honey = false, breakable = false;  // Tile design
    public boolean isExplode = false, isUpgrade = false, isAnimate = false, isChosen = false;                        //Tile state
    public char direct = 'N', machine = 'N', specialCombine = 'N';
    public int iceCreamTarget = 0;
    public boolean entryPoint = false;

    public Tile(GameEngine gameEngine) {
        super(gameEngine);
        mGameEngine = gameEngine;
        mTileSize = gameEngine.mImageSize;
    }

    public void startGame() {
        if (!breakable
                && !lock
                && !honey
                && kind != TileID.STAR_FISH) {
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
                    mImage.setImageResource(TileID.getChosenFruit(this));
                } else if (special) {
                    mImage.setImageResource(TileID.getSpecialFruit(this));
                } else {
                    mImage.setImageResource(TileID.getFruit(this));
                }
            }
        }

    }

    public boolean isMoving(){
        return (x - col * mTileSize != 0) || (y - row * mTileSize != 0);
    }

    public boolean isMovable() {
        if (empty || lock || honey || kind == 0 || (breakable && kind != TileID.CRACKER))
            return false;

        return true;
    }
}
