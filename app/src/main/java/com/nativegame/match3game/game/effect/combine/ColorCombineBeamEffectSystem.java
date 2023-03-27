package com.nativegame.match3game.game.effect.combine;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.effect.EffectDirection;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.Entity;
import com.nativegame.nattyengine.entity.modifier.DurationModifier;
import com.nativegame.nattyengine.util.math.RandomUtils;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ColorCombineBeamEffectSystem extends Entity {

    private static final long TIME_TO_LIVE = 2000;
    private static final long TIME_TO_SPAWN = 200;
    private static final int SPAWN_COUNT = 5;

    private final Pool<ColorCombineBeamEffect> mEffectPool;
    private final DurationModifier<ColorCombineBeamEffectSystem> mDurationModifier;

    private float mTargetX;
    private float mTargetY;
    private long mTotalTime;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ColorCombineBeamEffectSystem(Engine engine, int size) {
        super(engine);
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<ColorCombineBeamEffect>() {
            @Override
            public ColorCombineBeamEffect createObject() {
                return new ColorCombineBeamEffect(ColorCombineBeamEffectSystem.this, engine,
                        Textures.EXPLOSION_BEAM);
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
            // Add beam in random direction
            int size = EffectDirection.values().length;
            for (int i = 0; i < SPAWN_COUNT; i++) {
                int index = RandomUtils.nextInt(size);
                mEffectPool.obtainObject().activate(mTargetX, mTargetY, EffectDirection.values()[index]);
            }
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
        addToGame();
        mTotalTime = 0;
    }

    public void returnToPool(ColorCombineBeamEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
