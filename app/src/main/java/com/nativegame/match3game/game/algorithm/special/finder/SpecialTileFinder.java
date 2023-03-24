package com.nativegame.match3game.game.algorithm.special.finder;

import com.nativegame.match3game.game.layer.tile.Tile;

public interface SpecialTileFinder {

    void findSpecialTile(Tile[][] tiles, int row, int col);

}
