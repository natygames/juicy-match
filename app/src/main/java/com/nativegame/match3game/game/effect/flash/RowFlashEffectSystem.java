package com.nativegame.match3game.game.effect.flash;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.effect.EffectDirection;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;

public class RowFlashEffectSystem {

    private final ObjectPool<RowFlashEffect> mEffectPool;

    public RowFlashEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new ObjectPool.PoolObjectFactory<RowFlashEffect>() {
            @Override
            public RowFlashEffect createObject() {
                return new RowFlashEffect(RowFlashEffectSystem.this, engine, Textures.ROW_FLASH_ANIMATION);
            }
        }, size * 2);
    }

    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y, EffectDirection.LEFT);
        mEffectPool.obtainObject().activate(x, y, EffectDirection.RIGHT);
        Sounds.STRIPED_SPECIAL_TILE_EXPLODE.play();
    }

    public void returnToPool(RowFlashEffect effect) {
        mEffectPool.returnObject(effect);
    }

}
