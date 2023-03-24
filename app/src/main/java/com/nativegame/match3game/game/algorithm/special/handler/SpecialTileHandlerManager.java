package com.nativegame.match3game.game.algorithm.special.handler;

import com.nativegame.match3game.game.layer.tile.SpecialType;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.nattyengine.engine.Engine;

import java.util.HashMap;
import java.util.Map;

public class SpecialTileHandlerManager {

    private final Map<SpecialType, SpecialTileHandler> mSpecialTileHandlers = new HashMap<>();

    public SpecialTileHandlerManager(Engine engine) {
        mSpecialTileHandlers.clear();
        // Add all the special tile handler
        mSpecialTileHandlers.put(SpecialType.COLOR_SPECIAL_TILE, new ColorSpecialTileHandler(engine));
        mSpecialTileHandlers.put(SpecialType.EXPLOSION_SPECIAL_TILE, new ExplosionSpecialTileHandler(engine));
        mSpecialTileHandlers.put(SpecialType.ROW_SPECIAL_TILE, new RowSpecialTileHandler(engine));
        mSpecialTileHandlers.put(SpecialType.COLUMN_SPECIAL_TILE, new ColumnSpecialTileHandler(engine));
    }

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void checkSpecialTile(Tile[][] tiles, Tile tile, int row, int col) {
        SpecialType type = tile.getSpecialType();
        SpecialTileHandler handler = mSpecialTileHandlers.get(type);
        handler.handleSpecialTile(tiles, tile, row, col);
    }
    //========================================================

}
