package com.nativegame.match3game.game.algorithm.layer;

import com.nativegame.match3game.game.algorithm.target.TargetHandlerManager;
import com.nativegame.match3game.game.layer.generator.GeneratorSystem;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.TileResetter;

import java.util.List;

public class GeneratorLayerHandler extends BaseLayerHandler {

    private final GeneratorSystem mGeneratorSystem;

    public GeneratorLayerHandler(GeneratorSystem generatorSystem) {
        super();
        mGeneratorSystem = generatorSystem;
    }

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
    protected void onUpdateLayer(TargetHandlerManager targetHandlerManager, Tile tile) {
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
