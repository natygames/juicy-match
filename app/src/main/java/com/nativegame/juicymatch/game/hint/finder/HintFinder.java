package com.nativegame.juicymatch.game.hint.finder;

import com.nativegame.juicymatch.game.layer.tile.Tile;

import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public interface HintFinder {

    List<Tile> findHint(Tile[][] tiles, int row, int col);

}
