package com.nativegame.juicymatch.game.algorithm.special.handler;

import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SpecialTileHandlerManager {

    private final Map<SpecialType, SpecialTileHandler> mSpecialTileHandlers = new HashMap<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public SpecialTileHandlerManager(Engine engine) {
        // Add all the special tile handler
        mSpecialTileHandlers.put(SpecialType.ICE_CREAM, new IceCreamHandler(engine));
        mSpecialTileHandlers.put(SpecialType.EXPLOSIVE, new ExplosiveTileHandler(engine));
        mSpecialTileHandlers.put(SpecialType.ROW_STRIPED, new RowStripedTileHandler(engine));
        mSpecialTileHandlers.put(SpecialType.COLUMN_STRIPED, new ColumnStripedTileHandler(engine));
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void checkSpecialTile(Tile[][] tiles, Tile tile, int row, int col) {
        SpecialType type = tile.getSpecialType();
        SpecialTileHandler handler = mSpecialTileHandlers.get(type);
        if (handler != null) {
            handler.handleSpecialTile(tiles, tile, row, col);
        }
    }
    //========================================================

}
