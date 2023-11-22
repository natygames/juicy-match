package com.nativegame.juicymatch.game.algorithm.special.handler;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.game.effect.flash.ColumnFlashEffectSystem;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ColumnStripedTileHandler extends BaseSpecialTileHandler {

    private final ColumnFlashEffectSystem mFlashEffectSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ColumnStripedTileHandler(Engine engine) {
        super(engine);
        mFlashEffectSystem = new ColumnFlashEffectSystem(engine, 1);
    }
    //========================================================

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
