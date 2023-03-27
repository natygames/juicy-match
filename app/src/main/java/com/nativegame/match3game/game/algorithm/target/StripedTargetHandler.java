package com.nativegame.match3game.game.algorithm.target;

import com.nativegame.match3game.game.layer.tile.SpecialType;
import com.nativegame.match3game.game.layer.tile.Tile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class StripedTargetHandler implements TargetHandler {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean checkTarget(Tile tile) {
        return tile.getSpecialType() == SpecialType.ROW_SPECIAL_TILE
                || tile.getSpecialType() == SpecialType.COLUMN_SPECIAL_TILE;
    }
    //========================================================

}
