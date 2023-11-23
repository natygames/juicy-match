package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TransformFlashEffectSystem {

    private final Pool<TransformFlashEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TransformFlashEffectSystem(Engine engine, int count) {
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<TransformFlashEffect>() {
            @Override
            public TransformFlashEffect createObject() {
                return new TransformFlashEffect(TransformFlashEffectSystem.this, engine,
                        Textures.FLASH_TRANSFORM_ANIMATION);
            }
        }, count);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y);
    }

    public void returnToPool(TransformFlashEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
