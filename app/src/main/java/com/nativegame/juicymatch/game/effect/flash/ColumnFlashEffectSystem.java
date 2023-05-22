package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ColumnFlashEffectSystem {

    private final Pool<ColumnFlashEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ColumnFlashEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<ColumnFlashEffect>() {
            @Override
            public ColumnFlashEffect createObject() {
                return new ColumnFlashEffect(ColumnFlashEffectSystem.this, engine, Textures.FLASH_COLUMN_ANIMATION);
            }
        }, size * 2);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y, FlashDirection.TOP);
        mEffectPool.obtainObject().activate(x, y, FlashDirection.DOWN);
        Sounds.STRIPED_EXPLODE.play();
    }

    public void returnToPool(ColumnFlashEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
