package com.nativegame.juicymatch.game.layer.entrypoint;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.LayerSprite;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class EntryPoint extends LayerSprite {

    private static final long START_DELAY = 300;

    private final EntryPointType mEntryPointType;

    private float mSpeedY;
    private float mMinY;
    private float mMaxY;
    private long mTotalTime;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public EntryPoint(Engine engine, Texture texture, EntryPointType entryPointType) {
        super(engine, texture);
        mEntryPointType = entryPointType;
        mSpeedY = 100f / 1000;
        setLayer(GameLayer.ENTRY_POINT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public EntryPointType getEntryPointType() {
        return mEntryPointType;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        // Init the arrow position and bound
        mY += 150;
        mMinY = mY;
        mMaxY = mY + 50;
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        mTotalTime += elapsedMillis;
        if (mTotalTime >= START_DELAY) {
            mY += mSpeedY * elapsedMillis;
            // Reverse speed direction when reach bound
            if (mY >= mMaxY) {
                mY = mMaxY;
                mSpeedY *= -1;
            }
            if (mY <= mMinY) {
                mY = mMinY;
                mSpeedY *= -1;
                mTotalTime = 0;
            }
        }
    }
    //========================================================

}
