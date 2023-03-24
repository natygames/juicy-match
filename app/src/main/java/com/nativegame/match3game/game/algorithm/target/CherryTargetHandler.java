package com.nativegame.match3game.game.algorithm.target;

import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.Tile;

public class CherryTargetHandler implements TargetHandler {

    @Override
    public boolean checkTarget(Tile tile) {
        return tile.getTileType() == FruitType.CHERRY;
    }

}
