package com.nativegame.match3game.game.algorithm.target;

import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.type.PieTile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class PieTargetHandler implements TargetHandler {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean checkTarget(Tile tile) {
        if (tile instanceof PieTile) {
            PieTile pie = ((PieTile) tile);
            return pie.isObstacle() && pie.getObstacleLayer() == 1;
        }
        return false;
    }
    //========================================================

}
