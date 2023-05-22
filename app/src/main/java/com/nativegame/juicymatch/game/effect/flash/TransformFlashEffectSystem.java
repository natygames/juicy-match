package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TransformFlashEffectSystem {

    private final Pool<TransformFlashEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TransformFlashEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<TransformFlashEffect>() {
            @Override
            public TransformFlashEffect createObject() {
                return new TransformFlashEffect(TransformFlashEffectSystem.this, engine,
                        Textures.FLASH_TRANSFORM_ANIMATION);
            }
        }, size);
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
