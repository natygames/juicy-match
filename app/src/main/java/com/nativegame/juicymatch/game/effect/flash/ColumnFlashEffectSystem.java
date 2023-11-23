package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ColumnFlashEffectSystem {

    private final Pool<ColumnFlashEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ColumnFlashEffectSystem(Engine engine, int count) {
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<ColumnFlashEffect>() {
            @Override
            public ColumnFlashEffect createObject() {
                return new ColumnFlashEffect(ColumnFlashEffectSystem.this, engine, Textures.FLASH_COLUMN_ANIMATION);
            }
        }, count * 2);
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
