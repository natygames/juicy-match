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
    public ExplosiveStripedTileCombineRingEffectSystem(Engine engine, int size) {
        super(engine);
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<ExplosiveStripedTileCombineRingEffect>() {
            @Override
            public ExplosiveStripedTileCombineRingEffect createObject() {
                return new ExplosiveStripedTileCombineRingEffect(ExplosiveStripedTileCombineRingEffectSystem.this,
                        engine, Textures.LIGHT_CIRCLE);
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
