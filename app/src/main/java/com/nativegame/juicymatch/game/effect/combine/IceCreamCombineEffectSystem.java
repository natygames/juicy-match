package com.nativegame.juicymatch.game.effect.combine;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamCombineEffectSystem {

    private final Pool<IceCreamCombineEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamCombineEffectSystem(Engine engine, int count) {
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<IceCreamCombineEffect>() {
            @Override
            public IceCreamCombineEffect createObject() {
                return new IceCreamCombineEffect(IceCreamCombineEffectSystem.this, engine, Textures.ICE_CREAM);
            }
        }, count * 2);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y, IceCreamCombineEffect.ColorCombineDirection.LEFT);
        mEffectPool.obtainObject().activate(x, y, IceCreamCombineEffect.ColorCombineDirection.RIGHT);
        Sounds.ICE_CREAM_COMBINE.play();
    }

    public void returnToPool(IceCreamCombineEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
