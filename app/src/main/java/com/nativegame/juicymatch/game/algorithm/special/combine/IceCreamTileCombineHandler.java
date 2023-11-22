package com.nativegame.juicymatch.game.algorithm.special.combine;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.algorithm.TileType;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamTileCombineHandler extends IceCreamCombineHandler {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamTileCombineHandler(Engine engine) {
        super(engine);
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
        if (tileA.getSpecialType() == SpecialType.ICE_CREAM
                && tileB.getSpecialType() == SpecialType.NONE
                && tileB.getTileType() != FruitType.NONE) {
            handleSpecialCombine(tiles, tileA, tileB, row, col);
            return true;
        }
        if (tileB.getSpecialType() == SpecialType.ICE_CREAM
                && tileA.getSpecialType() == SpecialType.NONE
                && tileA.getTileType() != FruitType.NONE) {
            handleSpecialCombine(tiles, tileB, tileA, row, col);
            return true;
        }

        return false;
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void handleSpecialCombine(Tile[][] tiles, Tile colorTile, Tile fruitTile, int row, int col) {
        colorTile.setTileState(TileState.MATCH);
        fruitTile.setTileState(TileState.MATCH);

        // Pop the same type tile
        TileType targetType = fruitTile.getTileType();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Tile t = tiles[i][j];
                // We make sure not pop multiple times
                if (t.getTileType() == targetType && t.getTileState() == TileState.IDLE) {
                    // We pop this tile and obstacle around
                    t.matchTile();
                    // Add lightning effect from color tile to target tile
                    playLightningEffect(colorTile, t);
                }
            }
        }

        playTileEffect(colorTile, fruitTile);
    }
    //========================================================

}
