package com.nativegame.juicymatch.game.effect.piece;

import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosionPieceEffectSystem {

    private final Pool<ExplosionPieceEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosionPieceEffectSystem(Engine engine, Texture texture, int count) {
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<ExplosionPieceEffect>() {
            @Override
            public ExplosionPieceEffect createObject() {
                return new ExplosionPieceEffect(ExplosionPieceEffectSystem.this, engine, texture);
            }
        }, count);
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
