package com.nativegame.match3game.game.layer.grid;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.LayerSprite;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

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
        setLayer(Layer.GRID_LAYER);
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
