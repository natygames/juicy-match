package com.nativegame.match3game.game.effect.lightning;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;

public class LightningEffectSystem {

    private final ObjectPool<LightningEffect> mEffectPool;

    public LightningEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new ObjectPool.PoolObjectFactory<LightningEffect>() {
            @Override
            public LightningEffect createObject() {
                return new LightningEffect(LightningEffectSystem.this, engine, Textures.LIGHTNING_ANIMATION);
            }
        }, size);
    }

    public void activate(float x, float y, int height, int rotation) {
        mEffectPool.obtainObject().activate(x, y, height, rotation);
    }

    public void returnToPool(LightningEffect effect) {
        mEffectPool.returnObject(effect);
    }

}
