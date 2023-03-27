package com.nativegame.match3game.game.layer.tile.type;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TubeTile extends EmptyTile {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TubeTile(Engine engine, Texture texture) {
        super(engine, texture);
        // We let the tube on top of tile, so they will pass underneath
        mLayer = Layer.TILE_LAYER + 1;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean isNegligible() {
        // We neglect tube when swap to top
        return true;
    }
    //========================================================

}
