package com.nativegame.juicymatch.game.effect.lightning;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LightningGlitterEffectSystem {

    private final Pool<LightningGlitterEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LightningGlitterEffectSystem(Engine engine, int count) {
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<LightningGlitterEffect>() {
            @Override
            public LightningGlitterEffect createObject() {
                return new LightningGlitterEffect(LightningGlitterEffectSystem.this, engine, Textures.GLITTER_BLUE);
            }
        }, count);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y);
    }

    public void returnToPool(LightningGlitterEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
