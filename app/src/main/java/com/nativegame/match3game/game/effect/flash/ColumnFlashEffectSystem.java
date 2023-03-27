package com.nativegame.match3game.game.effect.flash;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.effect.EffectDirection;
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
                return new ColumnFlashEffect(ColumnFlashEffectSystem.this, engine, Textures.COLUMN_FLASH_ANIMATION);
            }
        }, size * 2);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y, EffectDirection.TOP);
        mEffectPool.obtainObject().activate(x, y, EffectDirection.DOWN);
        Sounds.STRIPED_SPECIAL_TILE_EXPLODE.play();
    }

    public void returnToPool(ColumnFlashEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
