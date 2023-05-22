package com.nativegame.juicymatch.game.effect;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ScoreEffectSystem {

    private final Pool<ScoreEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ScoreEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<ScoreEffect>() {
            @Override
            public ScoreEffect createObject() {
                return new ScoreEffect(ScoreEffectSystem.this, engine, Textures.SCORE_PINK);
            }
        }, size);
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
