package com.nativegame.juicymatch.game.effect.piece;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamPieceEffectSystem {

    private final Pool<IceCreamPieceEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamPieceEffectSystem(Engine engine, int count) {
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<IceCreamPieceEffect>() {
            @Override
            public IceCreamPieceEffect createObject() {
                return new IceCreamPieceEffect(IceCreamPieceEffectSystem.this, engine, Textures.ICE_CREAM);
            }
        }, count);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y);
        Sounds.ICE_CREAM_TRANSFORM.play();
    }

    public void returnToPool(IceCreamPieceEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
