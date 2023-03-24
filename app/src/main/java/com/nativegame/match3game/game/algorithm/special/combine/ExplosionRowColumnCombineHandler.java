package com.nativegame.match3game.game.algorithm.special.combine;

import com.nativegame.match3game.algorithm.TileState;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.effect.flash.ColumnFlashEffectSystem;
import com.nativegame.match3game.game.effect.flash.RowFlashEffectSystem;
import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.tile.SpecialType;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.particles.ParticleSystem;

public class ExplosionRowColumnCombineHandler extends BaseSpecialCombineHandler {

    private final RowFlashEffectSystem mRowFlashEffectSystem;
    private final ColumnFlashEffectSystem mColumnFlashEffectSystem;
    private final ParticleSystem mRingLightParticleSystem;

    public ExplosionRowColumnCombineHandler(Engine engine) {
        super(engine);
        mRowFlashEffectSystem = new RowFlashEffectSystem(engine, 3);
        mColumnFlashEffectSystem = new ColumnFlashEffectSystem(engine, 3);
        mRingLightParticleSystem = new ParticleSystem(engine, Textures.RING_FLASH, 1)
                .setDuration(500)
                .setScale(0, 10)
                .setAlpha(255, 55)
                .setLayer(Layer.EFFECT_LAYER);
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public long getStartDelay() {
        return 0;
    }

    @Override
    public boolean checkSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        // First tile is row or column special tile
        if ((tileA.getSpecialType() == SpecialType.ROW_SPECIAL_TILE
                || tileA.getSpecialType() == SpecialType.COLUMN_SPECIAL_TILE)
                && tileB.getSpecialType() == SpecialType.EXPLOSION_SPECIAL_TILE) {
            // We make sure the origin special tiles not being detected
            tileA.setTileState(TileState.MATCH);
            tileB.setTileState(TileState.MATCH);
            handleSpecialCombine(tiles, tileA, tileB, row, col);
            return true;
        }

        // First tile is explosion special tile
        if ((tileB.getSpecialType() == SpecialType.ROW_SPECIAL_TILE
                || tileB.getSpecialType() == SpecialType.COLUMN_SPECIAL_TILE)
                && tileA.getSpecialType() == SpecialType.EXPLOSION_SPECIAL_TILE) {
            // We make sure the origin special tiles not being detected
            tileA.setTileState(TileState.MATCH);
            tileB.setTileState(TileState.MATCH);
            handleSpecialCombine(tiles, tileB, tileA, row, col);
            return true;
        }

        return false;
    }

    @Override
    protected void playTileEffect(Tile tileA, Tile tileB) {
        super.playTileEffect(tileA, tileB);
        mRingLightParticleSystem.oneShot(tileA.getCenterX(), tileA.getCenterY(), 1);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void handleSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        int targetRow = tileA.getRow();
        int targetCol = tileA.getColumn();

        // Pop 3 row tile
        for (int i = targetRow - 1; i <= targetRow + 1; i++) {
            // We make sure the index not out of bound
            if (i < 0 || i > row - 1) {
                continue;
            }
            for (int j = 0; j < col; j++) {
                Tile t = tiles[i][j];
                // We make sure not pop multiple times
                if (t.getTileState() == TileState.IDLE) {
                    t.popTile();
                }
            }
        }

        // Pop 3 column tile
        for (int j = targetCol - 1; j <= targetCol + 1; j++) {
            // We make sure the index not out of bound
            if (j < 0 || j > col - 1) {
                continue;
            }
            for (int i = 0; i < row; i++) {
                Tile t = tiles[i][j];
                // We make sure not pop multiple times
                if (t.getTileState() == TileState.IDLE) {
                    t.popTile();
                }
            }
        }

        // Add 3 row flash
        for (int i = -1; i <= 1; i++) {
            // We make sure the flash not out of bound
            int flashRow = targetRow + i;
            if (flashRow < 0 || flashRow > row - 1) {
                continue;
            }
            mRowFlashEffectSystem.activate(tileA.getCenterX(), tileA.getCenterY() + tileA.getHeight() * i);
        }
        // Add 3 column flash
        for (int i = -1; i <= 1; i++) {
            // We make sure the flash not out of bound
            int flashCol = targetCol + i;
            if (flashCol < 0 || flashCol > col - 1) {
                continue;
            }
            mColumnFlashEffectSystem.activate(tileA.getCenterX() + tileA.getWidth() * i, tileA.getCenterY());
        }

        playTileEffect(tileA, tileB);
    }
    //========================================================

}
