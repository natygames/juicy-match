package com.nativegame.juicymatch.game.algorithm.layer;

import com.nativegame.juicymatch.game.algorithm.target.TargetHandlerManager;
import com.nativegame.juicymatch.game.layer.tile.Tile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public interface LayerHandler {

    void initLayer(Tile[][] tiles, int row, int col);

    void updateLayer(TargetHandlerManager targetHandlerManager, Tile[][] tiles, int row, int col);

    void removeLayer(Tile[][] tiles, int row, int col);

}
