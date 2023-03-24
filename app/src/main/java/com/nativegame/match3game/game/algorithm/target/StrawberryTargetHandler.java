package com.nativegame.match3game.game.algorithm.target;

import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.Tile;

public class StrawberryTargetHandler implements TargetHandler {

    @Override
    public boolean checkTarget(Tile tile) {
        return tile.getTileType() == FruitType.STRAWBERRY;
    }

}
