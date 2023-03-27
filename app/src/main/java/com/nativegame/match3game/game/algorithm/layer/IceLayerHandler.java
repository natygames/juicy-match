package com.nativegame.match3game.game.algorithm.layer;

import com.nativegame.match3game.algorithm.TileState;
import com.nativegame.match3game.game.algorithm.target.TargetHandlerManager;
import com.nativegame.match3game.game.layer.ice.Ice;
import com.nativegame.match3game.game.layer.ice.IceSystem;
import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.SpecialType;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.level.TargetType;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceLayerHandler extends BaseLayerHandler {

    private final IceSystem mIceSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceLayerHandler(IceSystem iceSystem) {
        mIceSystem = iceSystem;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onInitLayer(Tile tile) {
    }

    @Override
    protected void onUpdateLayer(TargetHandlerManager targetHandlerManager, Tile tile) {
        if (tile.getTileState() != TileState.MATCH) {
            return;
        }
        // Check is tile has ice
        Ice ice = mIceSystem.getChildAt(tile.getRow(), tile.getColumn());
        if (ice != null && ice.isRunning()) {
            // Remove ice if tile is fruit or special tile
            if (tile.getTileType() != FruitType.NONE || tile.getSpecialType() != SpecialType.NONE) {
                ice.playIceEffect();
                // Update target if all the ice layers being removed
                if (ice.getIceLayer() == 0) {
                    targetHandlerManager.updateTarget(TargetType.ICE);
                }
            }
        }
    }

    @Override
    protected void onRemoveLayer(Tile tile) {
    }
    //========================================================

}
