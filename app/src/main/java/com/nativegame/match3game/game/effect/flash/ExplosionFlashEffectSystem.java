package com.nativegame.match3game.game.effect.flash;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;

public class ExplosionFlashEffectSystem {

    private final ObjectPool<ExplosionFlashEffect> mEffectPool;

    public ExplosionFlashEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new ObjectPool.PoolObjectFactory<ExplosionFlashEffect>() {
            @Override
            public ExplosionFlashEffect createObject() {
                return new ExplosionFlashEffect(ExplosionFlashEffectSystem.this, engine, Textures.EXPLOSION_FLASH_ANIMATION);
            }
        }, size);
    }

    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y);
        Sounds.EXPLOSION_SPECIAL_TILE_EXPLODE.play();
    }

    public void returnToPool(ExplosionFlashEffect effect) {
        mEffectPool.returnObject(effect);
    }

}
