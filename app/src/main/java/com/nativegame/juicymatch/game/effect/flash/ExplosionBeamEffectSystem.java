package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosionBeamEffectSystem {

    private final Pool<ExplosionBeamEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosionBeamEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<ExplosionBeamEffect>() {
            @Override
            public ExplosionBeamEffect createObject() {
                return new ExplosionBeamEffect(ExplosionBeamEffectSystem.this, engine, Textures.FLASH_BEAM);
            }
        }, size);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        int size = FlashDirection.values().length;
        for (int i = 0; i < size; i++) {
            mEffectPool.obtainObject().activate(x, y, FlashDirection.values()[i]);
        }
    }

    public void returnToPool(ExplosionBeamEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
