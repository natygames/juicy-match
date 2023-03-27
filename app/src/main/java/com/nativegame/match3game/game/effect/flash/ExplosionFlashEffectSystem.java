package com.nativegame.match3game.game.effect.flash;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
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
                return new ExplosionFlashEffect(ExplosionFlashEffectSystem.this, engine, Textures.EXPLOSION_FLASH_ANIMATION);
            }
        }, size);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y);
        Sounds.EXPLOSION_SPECIAL_TILE_EXPLODE.play();
    }

    public void returnToPool(ExplosionFlashEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
