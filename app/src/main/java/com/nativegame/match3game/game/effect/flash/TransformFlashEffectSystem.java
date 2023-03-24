package com.nativegame.match3game.game.effect.flash;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;

public class TransformFlashEffectSystem {

    private final ObjectPool<TransformFlashEffect> mEffectPool;

    public TransformFlashEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new ObjectPool.PoolObjectFactory<TransformFlashEffect>() {
            @Override
            public TransformFlashEffect createObject() {
                return new TransformFlashEffect(TransformFlashEffectSystem.this, engine,
                        Textures.TRANSFORM_FLASH_ANIMATION);
            }
        }, size);
    }

    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y);
    }

    public void returnToPool(TransformFlashEffect effect) {
        mEffectPool.returnObject(effect);
    }

}
