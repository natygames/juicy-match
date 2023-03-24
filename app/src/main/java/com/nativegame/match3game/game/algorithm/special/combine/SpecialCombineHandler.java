package com.nativegame.match3game.game.algorithm.special.combine;

import com.nativegame.match3game.game.layer.tile.Tile;

public interface SpecialCombineHandler {

    long getStartDelay();

    boolean checkSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col);

}
