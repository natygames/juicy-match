package com.nativegame.match3game.game.algorithm.target;

import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.Tile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class StrawberryTargetHandler implements TargetHandler {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean checkTarget(Tile tile) {
        return tile.getTileType() == FruitType.STRAWBERRY;
    }
    //========================================================

}
