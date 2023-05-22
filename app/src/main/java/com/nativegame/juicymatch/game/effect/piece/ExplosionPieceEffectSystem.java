package com.nativegame.juicymatch.game.effect.piece;

import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosionPieceEffectSystem {

    private final Pool<ExplosionPieceEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosionPieceEffectSystem(Engine engine, Texture texture, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<ExplosionPieceEffect>() {
            @Override
            public ExplosionPieceEffect createObject() {
                return new ExplosionPieceEffect(ExplosionPieceEffectSystem.this, engine, texture);
            }
        }, size);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, int count) {
        for (int i = 0; i < count; i++) {
            mEffectPool.obtainObject().activate(x, y);
        }
    }

    public void returnToPool(ExplosionPieceEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
