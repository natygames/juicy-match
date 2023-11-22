package com.nativegame.juicymatch.game.layer.tile.type;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class PipeTile extends EmptyTile {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public PipeTile(Engine engine, Texture texture) {
        super(engine, texture);
        // We let the pipe on top of tile, so they will pass underneath
        mLayer = GameLayer.TILE_LAYER + 1;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean isNegligible() {
        // We neglect pipe when swap to top
        return true;
    }
    //========================================================

}
