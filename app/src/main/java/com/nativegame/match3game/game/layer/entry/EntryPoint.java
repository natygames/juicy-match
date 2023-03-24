package com.nativegame.match3game.game.layer.entry;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.LayerSprite;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

public class EntryPoint extends LayerSprite {

    private static final long START_DELAY = 300;

    private final EntryPointType mEntryPointType;

    private float mSpeedY;
    private float mMinY;
    private float mMaxY;
    private long mTotalTime;

    public EntryPoint(Engine engine, Texture texture, EntryPointType entryPointType) {
        super(engine, texture);
        mEntryPointType = entryPointType;
        mSpeedY = 100f / 1000;
        setLayer(Layer.EFFECT_LAYER);
    }

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
