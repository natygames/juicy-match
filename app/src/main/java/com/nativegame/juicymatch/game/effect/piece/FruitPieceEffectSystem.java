package com.nativegame.juicymatch.game.effect.piece;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class FruitPieceEffectSystem {

    private final Pool<FruitPieceEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public FruitPieceEffectSystem(Engine engine, int count) {
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<FruitPieceEffect>() {
            @Override
            public FruitPieceEffect createObject() {
                return new FruitPieceEffect(FruitPieceEffectSystem.this, engine, Textures.CHERRY);
            }
        }, count);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, FruitType fruitType, SpecialType specialType) {
        mEffectPool.obtainObject().activate(x, y, fruitType, specialType, FruitPiece.LEFT);
        mEffectPool.obtainObject().activate(x, y, fruitType, specialType, FruitPiece.RIGHT);
    }

    public void returnToPool(FruitPieceEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
