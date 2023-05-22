package com.nativegame.juicymatch.game.effect.combine;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.Entity;
import com.nativegame.nattyengine.entity.modifier.DurationModifier;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

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
    public IceCreamCombineRingEffectSystem(Engine engine, int size) {
        super(engine);
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<IceCreamCombineRingEffect>() {
            @Override
            public IceCreamCombineRingEffect createObject() {
                return new IceCreamCombineRingEffect(IceCreamCombineRingEffectSystem.this, engine,
                        Textures.LIGHT_RING);
            }
        }, size);
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
