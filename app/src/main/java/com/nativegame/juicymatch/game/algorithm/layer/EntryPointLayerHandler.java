package com.nativegame.juicymatch.game.algorithm.layer;

import com.nativegame.juicymatch.game.algorithm.target.TargetHandlerManager;
import com.nativegame.juicymatch.game.layer.entrypoint.EntryPoint;
import com.nativegame.juicymatch.game.layer.entrypoint.EntryPointSystem;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.type.StarfishTile;
import com.nativegame.juicymatch.level.TargetType;

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
    protected void onUpdateLayer(Tile tile, TargetHandlerManager targetHandlerManager, Tile[][] tiles, int row, int col) {
        if (tile instanceof StarfishTile) {
            StarfishTile starfish = ((StarfishTile) tile);
            if (starfish.isStarfish()) {
                updateStarfish(targetHandlerManager, starfish);
            }
        }
    }

    @Override
    protected void onRemoveLayer(Tile tile) {
        // Remove starfish when layer removed
        if (tile instanceof StarfishTile) {
            StarfishTile starfish = ((StarfishTile) tile);
            if (!starfish.isStarfish()) {
                return;
            }
            starfish.popStarfishTile();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void updateStarfish(TargetHandlerManager targetHandlerManager, StarfishTile starfish) {
        // Check is starfish at entry point
        EntryPoint entryPoint = mEntryPointSystem.getChildAt(starfish.getRow(), starfish.getColumn());
        if (entryPoint != null && entryPoint.isRunning()) {
            // Remove starfish and update target
            starfish.popStarfishTile();
            targetHandlerManager.updateTarget(TargetType.STARFISH);
        }
    }
    //========================================================

}
