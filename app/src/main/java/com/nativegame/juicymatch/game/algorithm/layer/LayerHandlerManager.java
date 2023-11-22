package com.nativegame.juicymatch.game.algorithm.layer;

import com.nativegame.juicymatch.game.algorithm.target.TargetHandlerManager;
import com.nativegame.juicymatch.game.layer.honey.HoneySystem;
import com.nativegame.juicymatch.game.layer.entrypoint.EntryPointSystem;
import com.nativegame.juicymatch.game.layer.generator.GeneratorSystem;
import com.nativegame.juicymatch.game.layer.ice.IceSystem;
import com.nativegame.juicymatch.game.layer.lock.LockSystem;
import com.nativegame.juicymatch.game.layer.sand.SandSystem;
import com.nativegame.juicymatch.game.layer.shell.ShellSystem;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;

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
        if (Level.LEVEL_DATA.getLock() != null) {
            mLayerHandlers.add(new LockLayerHandler(new LockSystem(engine)));
        }
        if (Level.LEVEL_DATA.getIce() != null) {
            mLayerHandlers.add(new IceLayerHandler(new IceSystem(engine)));
        }
        if (Level.LEVEL_DATA.getHoney() != null) {
            mLayerHandlers.add(new HoneyLayerHandler(new HoneySystem(engine)));
        }
        if (Level.LEVEL_DATA.getEntry() != null) {
            mLayerHandlers.add(new EntryPointLayerHandler(new EntryPointSystem(engine)));
        }
        if (Level.LEVEL_DATA.getSand() != null) {
            mLayerHandlers.add(new SandLayerHandler(new SandSystem(engine), new ShellSystem(engine)));
        }
        if (Level.LEVEL_DATA.getGenerator() != null) {
            mLayerHandlers.add(new GeneratorLayerHandler(new GeneratorSystem(engine)));
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
