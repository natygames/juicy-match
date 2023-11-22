package com.nativegame.juicymatch.game.hint;

import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.Entity;

import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HintModifier extends Entity {

    private static final int MAX_ALPHA = 255;
    private static final int MIN_ALPHA = 100;

    private List<Tile> mHintTiles;
    private float mAlpha;
    private float mAlphaSpeed;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public HintModifier(Engine engine) {
        super(engine);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mAlpha = MAX_ALPHA;
        mAlphaSpeed = -300f / 1000;
    }

    @Override
    public void onRemove() {
        // Stop hint effect
        int size = mHintTiles.size();
        for (int i = 0; i < size; i++) {
            Tile t = mHintTiles.get(i);
            t.setAlpha(MAX_ALPHA);
        }
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        mAlpha += mAlphaSpeed * elapsedMillis;
        // Reverse speed direction when reach bound
        if (mAlpha >= MAX_ALPHA) {
            mAlpha = MAX_ALPHA;
            mAlphaSpeed *= -1;
        }
        if (mAlpha <= MIN_ALPHA) {
            mAlpha = MIN_ALPHA;
            mAlphaSpeed *= -1;
        }

        // Update tile alpha
        int size = mHintTiles.size();
        for (int i = 0; i < size; i++) {
            Tile t = mHintTiles.get(i);
            t.setAlpha((int) mAlpha);
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(List<Tile> tiles) {
        mHintTiles = tiles;
        addToGame();
    }
    //========================================================

}
