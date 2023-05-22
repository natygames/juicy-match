package com.nativegame.juicymatch.game.algorithm.target;

import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.type.CakeTile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class CakeTargetHandler implements TargetHandler {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean checkTarget(Tile tile) {
        if (tile instanceof CakeTile) {
            CakeTile cake = ((CakeTile) tile);
            return cake.isObstacle() && cake.getObstacleLayer() == 1;
        }
        return false;
    }
    //========================================================

}
