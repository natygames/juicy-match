package com.nativegame.juicymatch.game.effect.combine;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.ScaleModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamCombineEffect extends Sprite {

    private static final long TIME_TO_LIVE = 2000;
    private static final long TIME_TO_MOVE = 200;
    private static final long MOVE_INCREMENT = 10;

    private final IceCreamCombineEffectSystem mParent;
    private final ScaleModifier mScaleModifier;

    private float mMinX;
    private float mMaxX;
    private float mSpeedX;
    private long mTotalTime;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamCombineEffect(IceCreamCombineEffectSystem iceCreamCombineEffectSystem, Engine engine, Texture texture) {
        super(engine, texture);
        mParent = iceCreamCombineEffectSystem;
        mScaleModifier = new ScaleModifier(1.2f, 0.9f, TIME_TO_LIVE);
        mScaleModifier.setAutoRemove(true);
        mSpeedX = 1500f / 1000;
        setLayer(GameLayer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onRemove() {
        mParent.returnToPool(this);
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        mX += mSpeedX * elapsedMillis;
        if (mX <= mMinX) {
            // Move to the right
            mSpeedX *= -1;
            mX = mMinX;
            mLayer = ColorCombineDirection.RIGHT.getLayer();
        }
        if (mX >= mMaxX) {
            // Move to the left
            mSpeedX *= -1;
            mX = mMaxX;
            mLayer = ColorCombineDirection.LEFT.getLayer();
        }

        // Update bound
        mTotalTime += elapsedMillis;
        if (mTotalTime >= TIME_TO_MOVE) {
            mMinX += MOVE_INCREMENT;
            mMaxX -= MOVE_INCREMENT;
            mTotalTime = 0;
        }
        mScaleModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, ColorCombineDirection direction) {
        setCenterX(x);
        setCenterY(y);
        mMinX = x - getWidth();
        mMaxX = x;
        mSpeedX = Math.abs(mSpeedX) * direction.getSpeedFactor();
        mLayer = direction.getLayer();
        mScaleModifier.init(this);
        addToGame();
        mTotalTime = 0;
    }
    //========================================================

    public enum ColorCombineDirection {
        LEFT,
        RIGHT;

        //--------------------------------------------------------
        // Getter and Setter
        //--------------------------------------------------------
        public int getSpeedFactor() {
            switch (this) {
                case LEFT:
                    return -1;
                case RIGHT:
                    return 1;
            }

            return 0;
        }

        public int getLayer() {
            switch (this) {
                case LEFT:
                    return GameLayer.EFFECT_LAYER;
                case RIGHT:
                    return GameLayer.EFFECT_LAYER + 1;
            }

            return 0;
        }
        //========================================================

    }

}
