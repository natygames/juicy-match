package com.nativegame.match3game.game.effect.flash;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.effect.EffectDirection;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;

public class ExplosionBeamEffectSystem {

    private final ObjectPool<ExplosionBeamEffect> mEffectPool;

    public ExplosionBeamEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new ObjectPool.PoolObjectFactory<ExplosionBeamEffect>() {
            @Override
            public ExplosionBeamEffect createObject() {
                return new ExplosionBeamEffect(ExplosionBeamEffectSystem.this, engine, Textures.EXPLOSION_BEAM);
            }
        }, size);
    }

    public void activate(float x, float y) {
        int size = EffectDirection.values().length;
        for (int i = 0; i < size; i++) {
            mEffectPool.obtainObject().activate(x, y, EffectDirection.values()[i]);
        }
    }

    public void returnToPool(ExplosionBeamEffect effect) {
        mEffectPool.returnObject(effect);
    }

}
