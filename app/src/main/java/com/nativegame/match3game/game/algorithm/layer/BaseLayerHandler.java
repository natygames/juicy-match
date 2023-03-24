package com.nativegame.match3game.game.algorithm.layer;

import com.nativegame.match3game.game.algorithm.target.TargetHandlerManager;
import com.nativegame.match3game.game.layer.tile.Tile;

public abstract class BaseLayerHandler implements LayerHandler {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void initLayer(Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Tile t = tiles[i][j];
                onInitLayer(t);
            }
        }
    }

    @Override
    public void updateLayer(TargetHandlerManager targetHandlerManager, Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Tile t = tiles[i][j];
                onUpdateLayer(targetHandlerManager, t);
            }
        }
    }

    @Override
    public void removeLayer(Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Tile t = tiles[i][j];
                onRemoveLayer(t);
            }
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    protected abstract void onInitLayer(Tile tile);

    protected abstract void onUpdateLayer(TargetHandlerManager targetHandlerManager, Tile tile);

    protected abstract void onRemoveLayer(Tile tile);
    //========================================================

}
