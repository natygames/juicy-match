package com.nativegame.match3game.game.effect;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;

public class ScoreEffectSystem {

    private final ObjectPool<ScoreEffect> mEffectPool;

    public ScoreEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new ObjectPool.PoolObjectFactory<ScoreEffect>() {
            @Override
            public ScoreEffect createObject() {
                return new ScoreEffect(ScoreEffectSystem.this, engine, Textures.SCORE_PINK);
            }
        }, size);
    }

    public void activate(float x, float y, FruitType fruitType) {
        mEffectPool.obtainObject().activate(x, y, fruitType);
    }

    public void returnToPool(ScoreEffect effect) {
        mEffectPool.returnObject(effect);
    }

}
