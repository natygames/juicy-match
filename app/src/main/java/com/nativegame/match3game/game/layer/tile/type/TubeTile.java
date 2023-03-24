package com.nativegame.match3game.game.layer.tile.type;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

public class TubeTile extends EmptyTile {

    public TubeTile(Engine engine, Texture texture) {
        super(engine, texture);
        // We let the tube on top of tile, so they will pass underneath
        mLayer = Layer.TILE_LAYER + 1;
    }

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
