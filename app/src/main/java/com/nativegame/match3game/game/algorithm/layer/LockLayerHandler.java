package com.nativegame.match3game.game.algorithm.layer;

import com.nativegame.match3game.algorithm.TileState;
import com.nativegame.match3game.game.algorithm.target.TargetHandlerManager;
import com.nativegame.match3game.game.layer.lock.Lock;
import com.nativegame.match3game.game.layer.lock.LockSystem;
import com.nativegame.match3game.game.layer.tile.Tile;

public class LockLayerHandler extends BaseLayerHandler {

    private final LockSystem mLockSystem;

    public LockLayerHandler(LockSystem lockSystem) {
        super();
        mLockSystem = lockSystem;
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onInitLayer(Tile tile) {
        Lock lock = mLockSystem.getChildAt(tile.getRow(), tile.getColumn());
        if (lock != null && lock.isRunning()) {
            tile.setPoppable(false);
            tile.setSwappable(false);
            tile.setShufflable(false);
            tile.removeShuffleEffect();
        }
    }

    @Override
    protected void onUpdateLayer(TargetHandlerManager targetHandlerManager, Tile tile) {
        if (tile.getTileState() != TileState.MATCH) {
            return;
        }
        removeLock(tile);
    }

    @Override
    protected void onRemoveLayer(Tile tile) {
        removeLock(tile);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void removeLock(Tile tile) {
        Lock lock = mLockSystem.getChildAt(tile.getRow(), tile.getColumn());
        if (lock != null && lock.isRunning()) {
            lock.playLockEffect();
            tile.setPoppable(true);
            tile.setSwappable(true);
            tile.setShufflable(true);
            tile.setTileState(TileState.IDLE);
        }
    }
    //========================================================

}
