package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class RowFlashEffectSystem {

    private final Pool<RowFlashEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public RowFlashEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<RowFlashEffect>() {
            @Override
            public RowFlashEffect createObject() {
                return new RowFlashEffect(RowFlashEffectSystem.this, engine, Textures.FLASH_ROW_ANIMATION);
            }
        }, size * 2);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y, FlashDirection.LEFT);
        mEffectPool.obtainObject().activate(x, y, FlashDirection.RIGHT);
        Sounds.STRIPED_EXPLODE.play();
    }

    public void returnToPool(RowFlashEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
