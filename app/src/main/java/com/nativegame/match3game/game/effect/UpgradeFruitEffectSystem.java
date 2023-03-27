package com.nativegame.match3game.game.effect;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class UpgradeFruitEffectSystem {

    private final Pool<UpgradeFruitEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public UpgradeFruitEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<UpgradeFruitEffect>() {
            @Override
            public UpgradeFruitEffect createObject() {
                return new UpgradeFruitEffect(UpgradeFruitEffectSystem.this, engine, Textures.CHERRY);
            }
        }, size);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float startX, float startY, float endX, float endY, FruitType fruitType) {
        mEffectPool.obtainObject().activate(startX, startY, endX, endY, fruitType);
        Sounds.TILE_UPGRADE.play();
    }

    public void returnToPool(UpgradeFruitEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
