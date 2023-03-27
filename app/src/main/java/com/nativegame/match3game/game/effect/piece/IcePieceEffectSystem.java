package com.nativegame.match3game.game.effect.piece;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import com.nativegame.match3game.asset.Colors;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IcePieceEffectSystem {

    private static final int ICE_PIECE_NUM = 10;

    private final Pool<IcePieceEffect> mEffectPool;
    private final ColorFilter mWhiteFilter;
    private final ColorFilter mBlueFilter;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IcePieceEffectSystem(Engine engine, int size) {
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<IcePieceEffect>() {
            @Override
            public IcePieceEffect createObject() {
                return new IcePieceEffect(IcePieceEffectSystem.this, engine, Textures.ICE_PIECE);
            }
        }, size);
        mWhiteFilter = new PorterDuffColorFilter(Colors.WHITE, PorterDuff.Mode.SRC_IN);
        mBlueFilter = new PorterDuffColorFilter(Colors.BLUE, PorterDuff.Mode.SRC_IN);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, int layer) {
        for (int i = 0; i < ICE_PIECE_NUM; i++) {
            IcePieceEffect effect = mEffectPool.obtainObject();
            effect.setColorFilter(getIcePieceFilter(layer));
            effect.activate(x, y);
        }
    }

    public void returnToPool(IcePieceEffect effect) {
        mEffectPool.returnObject(effect);
    }

    private ColorFilter getIcePieceFilter(int layer) {
        switch (layer) {
            case 1:
                return mBlueFilter;
            case 2:
                return mWhiteFilter;
            default:
                throw new IllegalArgumentException("Filter not found");
        }
    }
    //========================================================

}
