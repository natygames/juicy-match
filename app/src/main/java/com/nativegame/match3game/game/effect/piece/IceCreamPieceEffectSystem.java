package com.nativegame.match3game.game.effect.piece;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;

public class IceCreamPieceEffectSystem {

    private final ObjectPool<IceCreamPieceEffect> mEffectPool;

    public IceCreamPieceEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new ObjectPool.PoolObjectFactory<IceCreamPieceEffect>() {
            @Override
            public IceCreamPieceEffect createObject() {
                return new IceCreamPieceEffect(IceCreamPieceEffectSystem.this, engine, Textures.ICE_CREAM);
            }
        }, size);
    }

    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y);
        Sounds.COLOR_SPECIAL_TILE_TRANSFORM.play();
    }

    public void returnToPool(IceCreamPieceEffect effect) {
        mEffectPool.returnObject(effect);
    }

}
