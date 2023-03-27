package com.nativegame.match3game.game.effect.piece;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.RotationModifier;
import com.nativegame.nattyengine.texture.Texture;
import com.nativegame.nattyengine.util.math.RandomUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LockPieceEffect extends Sprite {

    private static final long TIME_TO_LIVE = 600;
    private static final long TIME_TO_FLOAT = 200;

    private final LockPiece mLockPiece;
    private final RotationModifier mRotationModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final float mGravity;

    private float mSpeedX;
    private float mSpeedY;
    private long mTotalTime;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LockPieceEffect(Engine engine, Texture texture, LockPiece lockPiece) {
        super(engine, texture);
        mLockPiece = lockPiece;
        mRotationModifier = new RotationModifier(0, RandomUtils.nextSign() * 30f, TIME_TO_LIVE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_LIVE - TIME_TO_FLOAT, TIME_TO_FLOAT);
        mFadeOutModifier.setAutoRemove(true);
        mGravity = 20f / 1000;
        setLayer(Layer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public LockPiece getLockPiece() {
        return mLockPiece;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mTotalTime += elapsedMillis;
        if (mTotalTime < TIME_TO_FLOAT) {
            // Decelerate when go up
            mY -= mSpeedY * elapsedMillis;
            mSpeedY -= mGravity * elapsedMillis;
        } else {
            // Accelerate when go down
            mY += mSpeedY * elapsedMillis;
            mSpeedY += mGravity * elapsedMillis;
        }
        mX += mSpeedX * elapsedMillis;
        mRotationModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        setCenterX(x);
        setCenterY(y);
        mSpeedX = mLockPiece.getDirection() * 100f / 1000;
        mSpeedY = RandomUtils.nextFloat(3200f, 4200f) / 1000;
        mRotationModifier.init(this);
        mFadeOutModifier.init(this);
        addToGame();
        mTotalTime = 0;
    }
    //========================================================

}
