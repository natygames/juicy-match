package com.nativegame.juicymatch.game.effect.combine;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.Entity;
import com.nativegame.natyengine.entity.modifier.DurationModifier;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamCombineRingEffectSystem extends Entity {

    private static final long TIME_TO_LIVE = 1500;
    private static final long TIME_TO_SPAWN = 500;
    private static final int SCALE_INCREMENT = 2;

    private final Pool<IceCreamCombineRingEffect> mEffectPool;
    private final DurationModifier mDurationModifier;

    private float mTargetX;
    private float mTargetY;
    private float mCurrentScale;
    private long mTotalTime;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamCombineRingEffectSystem(Engine engine, int count) {
        super(engine);
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<IceCreamCombineRingEffect>() {
            @Override
            public IceCreamCombineRingEffect createObject() {
                return new IceCreamCombineRingEffect(IceCreamCombineRingEffectSystem.this, engine,
                        Textures.LIGHT_RING);
            }
        }, count);
        mDurationModifier = new DurationModifier(TIME_TO_LIVE);
        mDurationModifier.setAutoRemove(true);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mTotalTime += elapsedMillis;
        if (mTotalTime >= TIME_TO_SPAWN) {
            // Add ring and update scale
            mEffectPool.obtainObject().activate(mTargetX, mTargetY, mCurrentScale);
            mCurrentScale -= SCALE_INCREMENT;
            mTotalTime = 0;
        }
        mDurationModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mTargetX = x;
        mTargetY = y;
        mCurrentScale = 10;
        mDurationModifier.init(this);
        addToGame();
        mTotalTime = 0;
    }

    public void returnToPool(IceCreamCombineRingEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
