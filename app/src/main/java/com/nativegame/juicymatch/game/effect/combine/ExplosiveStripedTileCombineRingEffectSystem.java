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

public class ExplosiveStripedTileCombineRingEffectSystem extends Entity {

    private static final long TIME_TO_LIVE = 600;
    private static final long TIME_TO_SPAWN = 200;

    private final Pool<ExplosiveStripedTileCombineRingEffect> mEffectPool;
    private final DurationModifier mDurationModifier;

    private float mTargetX;
    private float mTargetY;
    private long mTotalTime;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosiveStripedTileCombineRingEffectSystem(Engine engine, int count) {
        super(engine);
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<ExplosiveStripedTileCombineRingEffect>() {
            @Override
            public ExplosiveStripedTileCombineRingEffect createObject() {
                return new ExplosiveStripedTileCombineRingEffect(ExplosiveStripedTileCombineRingEffectSystem.this,
                        engine, Textures.LIGHT_CIRCLE);
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
            // Add one ring at target position
            mEffectPool.obtainObject().activate(mTargetX, mTargetY);
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
        mDurationModifier.init(this);
        mEffectPool.obtainObject().activate(mTargetX, mTargetY);
        addToGame();
        mTotalTime = 0;
    }

    public void returnToPool(ExplosiveStripedTileCombineRingEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
