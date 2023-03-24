package com.nativegame.match3game.game.hint.finder;

import com.nativegame.match3game.game.layer.tile.Tile;

import java.util.List;

public interface HintFinder {

    List<Tile> findHint(Tile[][] tiles, int row, int col);

}
