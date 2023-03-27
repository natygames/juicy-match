package com.nativegame.match3game.game.algorithm.layer;

import com.nativegame.match3game.game.algorithm.target.TargetHandlerManager;
import com.nativegame.match3game.game.layer.entry.EntryPointSystem;
import com.nativegame.match3game.game.layer.generator.GeneratorSystem;
import com.nativegame.match3game.game.layer.ice.IceSystem;
import com.nativegame.match3game.game.layer.lock.LockSystem;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.level.Level;
import com.nativegame.match3game.level.LevelData;
import com.nativegame.nattyengine.engine.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LayerHandlerManager {

    private final List<LayerHandler> mLayerHandlers = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LayerHandlerManager(Engine engine) {
        LevelData levelData = Level.LEVEL_DATA;
        // We add the layer from top to bottom
        if (levelData.getEntry() != null) {
            mLayerHandlers.add(new EntryPointLayerHandler(new EntryPointSystem(engine)));
        }
        if (levelData.getGenerator() != null) {
            mLayerHandlers.add(new GeneratorLayerHandler(new GeneratorSystem(engine)));
        }
        if (levelData.getLock() != null) {
            mLayerHandlers.add(new LockLayerHandler(new LockSystem(engine)));
        }
        if (levelData.getIce() != null) {
            mLayerHandlers.add(new IceLayerHandler(new IceSystem(engine)));
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public List<LayerHandler> getLayerHandlers() {
        return mLayerHandlers;
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void initLayers(Tile[][] tiles, int row, int col) {
        int size = mLayerHandlers.size();
        for (int i = 0; i < size; i++) {
            LayerHandler handler = mLayerHandlers.get(i);
            handler.initLayer(tiles, row, col);
        }
    }

    public void updateLayers(TargetHandlerManager targetHandlerManager, Tile[][] tiles, int row, int col) {
        int size = mLayerHandlers.size();
        for (int i = 0; i < size; i++) {
            LayerHandler handler = mLayerHandlers.get(i);
            handler.updateLayer(targetHandlerManager, tiles, row, col);
        }
    }

    public void removeLayers(Tile[][] tiles, int row, int col) {
        int size = mLayerHandlers.size();
        for (int i = 0; i < size; i++) {
            LayerHandler handler = mLayerHandlers.get(i);
            handler.removeLayer(tiles, row, col);
        }
    }
    //========================================================

}
