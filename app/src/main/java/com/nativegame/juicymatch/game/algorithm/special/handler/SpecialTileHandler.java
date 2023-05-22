package com.nativegame.juicymatch.game.algorithm.special.handler;

import com.nativegame.juicymatch.game.layer.tile.Tile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public interface SpecialTileHandler {

    void handleSpecialTile(Tile[][] tiles, Tile tile, int row, int col);

}
