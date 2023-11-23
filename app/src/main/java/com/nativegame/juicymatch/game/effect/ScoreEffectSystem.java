package com.nativegame.juicymatch.game.effect;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.util.pool.Pool;
import com.nativegame.natyengine.util.pool.SafeFixedObjectPool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ScoreEffectSystem {

    private final Pool<ScoreEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ScoreEffectSystem(Engine engine, int count) {
        mEffectPool = new SafeFixedObjectPool<>(new Pool.PoolObjectFactory<ScoreEffect>() {
            @Override
            public ScoreEffect createObject() {
                return new ScoreEffect(ScoreEffectSystem.this, engine, Textures.SCORE_PINK);
            }
        }, count);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, FruitType fruitType) {
        mEffectPool.obtainObject().activate(x, y, fruitType);
    }

    public void returnToPool(ScoreEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
