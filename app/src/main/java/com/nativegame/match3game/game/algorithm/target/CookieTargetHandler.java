package com.nativegame.match3game.game.algorithm.target;

import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.type.CookieTile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class CookieTargetHandler implements TargetHandler {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean checkTarget(Tile tile) {
        if (tile instanceof CookieTile) {
            return ((CookieTile) tile).isObstacle();
        } else {
            return false;
        }
    }
    //========================================================

}
