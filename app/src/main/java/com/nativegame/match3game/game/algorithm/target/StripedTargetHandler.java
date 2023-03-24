package com.nativegame.match3game.game.algorithm.target;

import com.nativegame.match3game.game.layer.tile.SpecialType;
import com.nativegame.match3game.game.layer.tile.Tile;

public class StripedTargetHandler implements TargetHandler {

    @Override
    public boolean checkTarget(Tile tile) {
        return tile.getSpecialType() == SpecialType.ROW_SPECIAL_TILE
                || tile.getSpecialType() == SpecialType.COLUMN_SPECIAL_TILE;
    }

}
