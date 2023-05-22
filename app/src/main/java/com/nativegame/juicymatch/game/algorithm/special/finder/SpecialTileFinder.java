package com.nativegame.juicymatch.game.algorithm.special.finder;

import com.nativegame.juicymatch.game.layer.tile.Tile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public interface SpecialTileFinder {

    void findSpecialTile(Tile[][] tiles, int row, int col);

}
