package com.nativegame.juicymatch.game.layer.grid;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.LayerSprite;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Grid extends LayerSprite {

    private final GridType mGridType;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public Grid(Engine engine, Texture texture, GridType gridType) {
        super(engine, texture);
        mGridType = gridType;
        setRotation(gridType.getAngle());
        setLayer(GameLayer.GRID_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public GridType getGridType() {
        return mGridType;
    }
    //========================================================

}
