package com.nativegame.juicymatch.game.effect.lightning;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LightningGlitterEffectSystem {

    private final Pool<LightningGlitterEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LightningGlitterEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<LightningGlitterEffect>() {
            @Override
            public LightningGlitterEffect createObject() {
                return new LightningGlitterEffect(LightningGlitterEffectSystem.this, engine, Textures.GLITTER_BLUE);
            }
        }, size);
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
