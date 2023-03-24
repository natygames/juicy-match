package com.nativegame.match3game.game.effect.combine;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;

public class ColorCombineEffectSystem {

    private final ObjectPool<ColorCombineEffect> mEffectPool;

    public ColorCombineEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new ObjectPool.PoolObjectFactory<ColorCombineEffect>() {
            @Override
            public ColorCombineEffect createObject() {
                return new ColorCombineEffect(ColorCombineEffectSystem.this, engine, Textures.ICE_CREAM);
            }
        }, size * 2);
    }

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mEffectPool.obtainObject().activate(x, y, ColorCombineDirection.LEFT);
        mEffectPool.obtainObject().activate(x, y, ColorCombineDirection.RIGHT);
        Sounds.COLOR_SPECIAL_TILE_COMBINE.play();
    }

    public void returnToPool(ColorCombineEffect effect) {
        mEffectPool.returnObject(effect);
    }
    //========================================================

}
