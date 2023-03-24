package com.nativegame.match3game.game.effect.lightning;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;

public class LightningGlitterEffectSystem {

    private final ObjectPool<LightningGlitterEffect> mEffectPool;

    public LightningGlitterEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new ObjectPool.PoolObjectFactory<LightningGlitterEffect>() {
            @Override
            public LightningGlitterEffect createObject() {
                return new LightningGlitterEffect(LightningGlitterEffectSystem.this, engine, Textures.LIGHTNING_GLITTER);
            }
        }, size);
    }

    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y);
    }

    public void returnToPool(LightningGlitterEffect effect) {
        mEffectPool.returnObject(effect);
    }

}
