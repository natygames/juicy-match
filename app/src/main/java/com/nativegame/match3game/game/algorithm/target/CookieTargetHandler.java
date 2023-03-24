package com.nativegame.match3game.game.algorithm.target;

import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.type.CookieTile;

public class CookieTargetHandler implements TargetHandler {

    @Override
    public boolean checkTarget(Tile tile) {
        if (tile instanceof CookieTile) {
            return ((CookieTile) tile).isObstacle();
        } else {
            return false;
        }
    }

}
