package com.nativegame.match3game.game.algorithm.special.handler;

import com.nativegame.match3game.algorithm.TileState;
import com.nativegame.match3game.game.effect.flash.ColumnFlashEffectSystem;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.nattyengine.engine.Engine;

public class ColumnSpecialTileHandler extends BaseSpecialTileHandler {

    private final ColumnFlashEffectSystem mFlashEffectSystem;

    public ColumnSpecialTileHandler(Engine engine) {
        super(engine);
        mFlashEffectSystem = new ColumnFlashEffectSystem(engine, 1);
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void handleSpecialTile(Tile[][] tiles, Tile tile, int row, int col) {
        int targetCol = tile.getColumn();
        for (int i = 0; i < row; i++) {
            Tile t = tiles[i][targetCol];
            // We make sure not pop the tile multiple time
            if (t.getTileState() == TileState.IDLE) {
                t.popTile();
            }
        }

        playTileEffect(tile);
    }

    @Override
    protected void playTileEffect(Tile tile) {
        super.playTileEffect(tile);
        mFlashEffectSystem.activate(tile.getCenterX(), tile.getCenterY());
    }
    //========================================================

}
