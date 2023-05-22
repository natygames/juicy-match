package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosionFlashEffectSystem {

    private final Pool<ExplosionFlashEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosionFlashEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<ExplosionFlashEffect>() {
            @Override
            public ExplosionFlashEffect createObject() {
                return new ExplosionFlashEffect(ExplosionFlashEffectSystem.this, engine, Textures.FLASH_EXPLOSION_ANIMATION);
            }
        }, size);
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
