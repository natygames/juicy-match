package com.nativegame.match3game.game.algorithm.special.combine;

import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.nattyengine.engine.Engine;

import java.util.ArrayList;
import java.util.List;

public class SpecialCombineHandlerManager {

    private final List<SpecialCombineHandler> mSpecialCombineHandlers = new ArrayList<>();

    public SpecialCombineHandlerManager(Engine engine) {
        mSpecialCombineHandlers.clear();
        // Add all the special combine handler
        mSpecialCombineHandlers.add(new ColorFruitCombineHandler(engine));
        mSpecialCombineHandlers.add(new ColorRowColumnCombineHandler(engine));
        mSpecialCombineHandlers.add(new ColorExplosionCombineHandler(engine));
        mSpecialCombineHandlers.add(new DoubleColorCombineHandler(engine));
        mSpecialCombineHandlers.add(new DoubleExplosionCombineHandler(engine));
        mSpecialCombineHandlers.add(new DoubleRowColumnCombineHandler(engine));
        mSpecialCombineHandlers.add(new ExplosionRowColumnCombineHandler(engine));
    }

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public SpecialCombineHandler checkSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        int size = mSpecialCombineHandlers.size();
        for (int i = 0; i < size; i++) {
            SpecialCombineHandler handler = mSpecialCombineHandlers.get(i);
            // We make sure detected one special combine
            if (handler.checkSpecialCombine(tiles, tileA, tileB, row, col)) {
                return handler;
            }
        }

        return null;
    }
    //========================================================

}
