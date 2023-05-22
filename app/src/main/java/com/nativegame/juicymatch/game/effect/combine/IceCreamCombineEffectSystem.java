package com.nativegame.juicymatch.game.effect.combine;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamCombineEffectSystem {

    private final Pool<IceCreamCombineEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamCombineEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<IceCreamCombineEffect>() {
            @Override
            public IceCreamCombineEffect createObject() {
                return new IceCreamCombineEffect(IceCreamCombineEffectSystem.this, engine, Textures.ICE_CREAM);
            }
        }, size * 2);
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
