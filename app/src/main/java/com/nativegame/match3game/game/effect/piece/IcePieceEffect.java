package com.nativegame.match3game.game.effect.piece;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleModifier;
import com.nativegame.nattyengine.texture.Texture;
import com.nativegame.nattyengine.util.math.RandomUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IcePieceEffect extends Sprite {

    private static final long TIME_TO_LIVE = 700;
    private static final long TIME_TO_FLOAT = 300;

    private final IcePieceEffectSystem mParent;
    private final ScaleModifier mScaleModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final float mMaxSpeed;
    private final float mGravity;

    private float mSpeedX;
    private float mSpeedY;
    private long mStartDelay;
    private long mTotalTime;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IcePieceEffect(IcePieceEffectSystem icePieceEffectSystem, Engine engine, Texture texture) {
        super(engine, texture);
        mParent = icePieceEffectSystem;
        mScaleModifier = new ScaleModifier(TIME_TO_LIVE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_LIVE - TIME_TO_FLOAT, TIME_TO_FLOAT);
        mFadeOutModifier.setAutoRemove(true);
        mMaxSpeed = 2500f / 1000;
        mGravity = 10f / 1000;
        setLayer(Layer.EFFECT_LAYER);
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
        mTotalTime += elapsedMillis;
        if (mTotalTime >= mStartDelay) {
            if (mTotalTime < TIME_TO_FLOAT) {
                // Decelerate when go up
                mY -= mSpeedY * elapsedMillis;
                // We make sure speed is not negative
                if (mSpeedY > 0) {
                    mSpeedY -= mGravity * elapsedMillis;
                }
            } else {
                // Accelerate when go down
                mY += mSpeedY * elapsedMillis;
                // We make sure speed is not too fast
                if (mSpeedY < mMaxSpeed) {
                    mSpeedY += mGravity * elapsedMillis;
                }
            }
            mX += mSpeedX * elapsedMillis;
            mScaleModifier.update(this, elapsedMillis);
            mFadeOutModifier.update(this, elapsedMillis);
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        setCenterX(x);
        setCenterY(y);
        mSpeedX = RandomUtils.nextFloat(-800f, 800f) / 1000;
        mSpeedY = RandomUtils.nextFloat(1500f, 2500f) / 1000;
        float scale = RandomUtils.nextFloat(0, 1);
        mScaleModifier.setValue(scale, scale + 1.5f);
        mScaleModifier.init(this);
        mFadeOutModifier.init(this);
        mStartDelay = RandomUtils.nextInt(0, 200);
        addToGame();
        mTotalTime = 0;
    }
    //========================================================

}
