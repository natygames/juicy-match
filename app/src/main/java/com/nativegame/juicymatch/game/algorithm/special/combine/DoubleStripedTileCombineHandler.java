package com.nativegame.juicymatch.game.algorithm.special.combine;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.game.effect.flash.ColumnFlashEffectSystem;
import com.nativegame.juicymatch.game.effect.flash.RowFlashEffectSystem;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class DoubleStripedTileCombineHandler extends BaseSpecialCombineHandler {

    private final RowFlashEffectSystem mRowFlashEffectSystem;
    private final ColumnFlashEffectSystem mColumnFlashEffectSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public DoubleStripedTileCombineHandler(Engine engine) {
        super(engine);
        mRowFlashEffectSystem = new RowFlashEffectSystem(engine, 1);
        mColumnFlashEffectSystem = new ColumnFlashEffectSystem(engine, 1);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public long getStartDelay() {
        return 0;
    }

    @Override
    public boolean checkSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        // Check are both tiles row or column special tile
        if ((tileA.getSpecialType() == SpecialType.ROW_STRIPED
                || tileA.getSpecialType() == SpecialType.COLUMN_STRIPED)
                && (tileB.getSpecialType() == SpecialType.ROW_STRIPED
                || tileB.getSpecialType() == SpecialType.COLUMN_STRIPED)) {
            // We make sure the origin special tiles not being detected
            tileA.setTileState(TileState.MATCH);
            tileB.setTileState(TileState.MATCH);
            handleSpecialCombine(tiles, tileA, tileB, row, col);
            return true;
        }

        return false;
    }

    @Override
    protected void playTileEffect(Tile tileA, Tile tileB) {
        super.playTileEffect(tileA, tileB);
        mRowFlashEffectSystem.activate(tileA.getCenterX(), tileA.getCenterY());
        mColumnFlashEffectSystem.activate(tileA.getCenterX(), tileA.getCenterY());
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void handleSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        int targetRow = tileA.getRow();
        int targetCol = tileA.getColumn();

        // Pop row tile
        for (int j = 0; j < col; j++) {
            Tile t = tiles[targetRow][j];
            // We make sure not pop multiple times
            if (t.getTileState() == TileState.IDLE) {
                t.popTile();
            }
        }

        // Pop column tile
        for (int i = 0; i < row; i++) {
            Tile t = tiles[i][targetCol];
            // We make sure not pop multiple times
            if (t.getTileState() == TileState.IDLE) {
                t.popTile();
            }
        }

        playTileEffect(tileA, tileB);
    }
    //========================================================

}
