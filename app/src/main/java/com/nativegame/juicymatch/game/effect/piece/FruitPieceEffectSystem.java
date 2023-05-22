package com.nativegame.juicymatch.game.effect.piece;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class FruitPieceEffectSystem {

    private final Pool<FruitPieceEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public FruitPieceEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<FruitPieceEffect>() {
            @Override
            public FruitPieceEffect createObject() {
                return new FruitPieceEffect(FruitPieceEffectSystem.this, engine, Textures.CHERRY);
            }
        }, size);
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
