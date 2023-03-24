package com.nativegame.match3game.game.algorithm.special.handler;

import com.nativegame.match3game.game.layer.tile.Tile;

public interface SpecialTileHandler {

    void handleSpecialTile(Tile[][] tiles, Tile tile, int row, int col);

}
