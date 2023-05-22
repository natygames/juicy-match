package com.nativegame.juicymatch.game.effect.piece;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamPieceEffectSystem {

    private final Pool<IceCreamPieceEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamPieceEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<IceCreamPieceEffect>() {
            @Override
            public IceCreamPieceEffect createObject() {
                return new IceCreamPieceEffect(IceCreamPieceEffectSystem.this, engine, Textures.ICE_CREAM);
            }
        }, size);
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
