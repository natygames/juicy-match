package com.nativegame.match3game.game.algorithm.target;

import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.type.CandyTile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class CandyTargetHandler implements TargetHandler {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean checkTarget(Tile tile) {
        if (tile instanceof CandyTile) {
            CandyTile candy = ((CandyTile) tile);
            return candy.isObstacle() && candy.getObstacleLayer() == 1;
        }
        return false;
    }
    //========================================================

}
