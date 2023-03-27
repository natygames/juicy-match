package com.nativegame.match3game.game.algorithm.layer;

import com.nativegame.match3game.game.algorithm.target.TargetHandlerManager;
import com.nativegame.match3game.game.layer.entry.EntryPoint;
import com.nativegame.match3game.game.layer.entry.EntryPointSystem;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.type.StarfishTile;
import com.nativegame.match3game.level.TargetType;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class EntryPointLayerHandler extends BaseLayerHandler {

    private final EntryPointSystem mEntryPointSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public EntryPointLayerHandler(EntryPointSystem entryPointSystem) {
        mEntryPointSystem = entryPointSystem;
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
        // Check is tile starfish
        if (tile instanceof StarfishTile) {
            StarfishTile starfish = ((StarfishTile) tile);
            if (!starfish.isStarfish()) {
                return;
            }
            // Check is starfish at entry point
            EntryPoint entryPoint = mEntryPointSystem.getChildAt(tile.getRow(), tile.getColumn());
            if (entryPoint != null && entryPoint.isRunning()) {
                starfish.popStarfishTile();
                targetHandlerManager.updateTarget(TargetType.STARFISH);
            }
        }
    }

    @Override
    protected void onRemoveLayer(Tile tile) {
    }
    //========================================================

}
