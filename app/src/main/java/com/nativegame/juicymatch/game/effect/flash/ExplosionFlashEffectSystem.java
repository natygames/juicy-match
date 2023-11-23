package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosionFlashEffectSystem {

    private final Pool<ExplosionFlashEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosionFlashEffectSystem(Engine engine, int count) {
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<ExplosionFlashEffect>() {
            @Override
            public ExplosionFlashEffect createObject() {
                return new ExplosionFlashEffect(ExplosionFlashEffectSystem.this, engine, Textures.FLASH_EXPLOSION_ANIMATION);
            }
        }, count);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y);
        Sounds.EXPLOSIVE_EXPLODE.play();
    }

    public void returnToPool(ExplosionFlashEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
