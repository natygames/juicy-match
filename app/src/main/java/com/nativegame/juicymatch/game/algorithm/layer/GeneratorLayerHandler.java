package com.nativegame.juicymatch.game.algorithm.layer;

import com.nativegame.juicymatch.game.algorithm.target.TargetHandlerManager;
import com.nativegame.juicymatch.game.layer.generator.GeneratorSystem;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.TileResetter;

import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class GeneratorLayerHandler extends BaseLayerHandler {

    private final GeneratorSystem mGeneratorSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public GeneratorLayerHandler(GeneratorSystem generatorSystem) {
        mGeneratorSystem = generatorSystem;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onInitLayer(Tile tile) {
        List<TileResetter> resetters = mGeneratorSystem.getResetters();
        int size = resetters.size();
        for (int i = 0; i < size; i++) {
            TileResetter resetter = resetters.get(i);
            tile.addResetter(resetter);
        }
    }

    @Override
    protected void onUpdateLayer(Tile tile, TargetHandlerManager targetHandlerManager, Tile[][] tiles, int row, int col) {
    }

    @Override
    protected void onRemoveLayer(Tile tile) {
        List<TileResetter> resetters = mGeneratorSystem.getResetters();
        int size = resetters.size();
        for (int i = 0; i < size; i++) {
            TileResetter resetter = resetters.get(i);
            tile.removeResetter(resetter);
        }
    }
    //========================================================

}
