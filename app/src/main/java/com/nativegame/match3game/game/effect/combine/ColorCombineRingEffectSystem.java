package com.nativegame.match3game.game.effect.combine;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.Entity;
import com.nativegame.nattyengine.entity.modifier.DurationModifier;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ColorCombineRingEffectSystem extends Entity {

    private static final long TIME_TO_LIVE = 1500;
    private static final long TIME_TO_SPAWN = 500;
    private static final int SCALE_INCREMENT = 2;

    private final Pool<ColorCombineRingEffect> mEffectPool;
    private final DurationModifier<ColorCombineRingEffectSystem> mDurationModifier;

    private float mTargetX;
    private float mTargetY;
    private float mCurrentScale;
    private long mTotalTime;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ColorCombineRingEffectSystem(Engine engine, int size) {
        super(engine);
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<ColorCombineRingEffect>() {
            @Override
            public ColorCombineRingEffect createObject() {
                return new ColorCombineRingEffect(ColorCombineRingEffectSystem.this, engine,
                        Textures.RING_LIGHT);
            }
        }, size);
        mDurationModifier = new DurationModifier<>(TIME_TO_LIVE);
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

    public void returnToPool(ColorCombineRingEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
