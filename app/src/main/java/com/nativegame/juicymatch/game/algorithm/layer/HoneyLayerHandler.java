package com.nativegame.juicymatch.game.algorithm.layer;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.game.algorithm.target.TargetHandlerManager;
import com.nativegame.juicymatch.game.layer.honey.Honey;
import com.nativegame.juicymatch.game.layer.honey.HoneySystem;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.level.TargetType;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HoneyLayerHandler extends BaseLayerHandler {

    private final HoneySystem mHoneySystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public HoneyLayerHandler(HoneySystem honeySystem) {
        mHoneySystem = honeySystem;
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
        if (tile.getTileState() != TileState.MATCH) {
            return;
        }
        updateHoney(targetHandlerManager, tiles, row, col, tile);
    }

    @Override
    protected void onRemoveLayer(Tile tile) {
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void updateHoney(TargetHandlerManager targetHandlerManager, Tile[][] tiles, int row, int col, Tile tile) {
        Honey honey = mHoneySystem.getChildAt(tile.getRow(), tile.getColumn());
        if (honey != null && honey.isRunning()) {
            // Remove ice if tile is fruit or special tile
            if (tile.getTileType() != FruitType.NONE || tile.getSpecialType() != SpecialType.NONE) {
                dfs(targetHandlerManager, tiles, row, col, tile);
            }
        }
    }

    private void dfs(TargetHandlerManager targetHandlerManager, Tile[][] tiles, int row, int col, Tile tile) {
        int tileRow = tile.getRow();
        int tileCol = tile.getColumn();
        // Check upper tile
        if (tileRow > 0) {
            Tile upperTile = tiles[tileRow - 1][tileCol];
            if (upperTile.getTileState() == TileState.MATCH) {
                if (upperTile.getTileType() != FruitType.NONE || upperTile.getSpecialType() != SpecialType.NONE) {
                    Honey honey = mHoneySystem.getChildAt(tileRow - 1, tileCol);
                    if (honey != null && !honey.isRunning()) {
                        honey.playHoneyEffect();
                        targetHandlerManager.updateTarget(TargetType.HONEY);
                        dfs(targetHandlerManager, tiles, row, col, upperTile);
                    }
                }
            }
        }
        // Check down tile
        if (tileRow < row - 1) {
            Tile downTile = tiles[tileRow + 1][tileCol];
            if (downTile.getTileState() == TileState.MATCH) {
                if (downTile.getTileType() != FruitType.NONE || downTile.getSpecialType() != SpecialType.NONE) {
                    Honey honey = mHoneySystem.getChildAt(tileRow + 1, tileCol);
                    if (honey != null && !honey.isRunning()) {
                        honey.playHoneyEffect();
                        targetHandlerManager.updateTarget(TargetType.HONEY);
                        dfs(targetHandlerManager, tiles, row, col, downTile);
                    }
                }
            }
        }
        // Check left tile
        if (tileCol > 0) {
            Tile leftTile = tiles[tileRow][tileCol - 1];
            if (leftTile.getTileState() == TileState.MATCH) {
                if (leftTile.getTileType() != FruitType.NONE || leftTile.getSpecialType() != SpecialType.NONE) {
                    Honey honey = mHoneySystem.getChildAt(tileRow, tileCol - 1);
                    if (honey != null && !honey.isRunning()) {
                        honey.playHoneyEffect();
                        targetHandlerManager.updateTarget(TargetType.HONEY);
                        dfs(targetHandlerManager, tiles, row, col, leftTile);
                    }
                }
            }
        }
        // Check right tile
        if (tileCol < col - 1) {
            Tile rightTile = tiles[tileRow][tileCol + 1];
            if (rightTile.getTileState() == TileState.MATCH) {
                if (rightTile.getTileType() != FruitType.NONE || rightTile.getSpecialType() != SpecialType.NONE) {
                    Honey honey = mHoneySystem.getChildAt(tileRow, tileCol + 1);
                    if (honey != null && !honey.isRunning()) {
                        honey.playHoneyEffect();
                        targetHandlerManager.updateTarget(TargetType.HONEY);
                        dfs(targetHandlerManager, tiles, row, col, rightTile);
                    }
                }
            }
        }
    }
    //========================================================

}
