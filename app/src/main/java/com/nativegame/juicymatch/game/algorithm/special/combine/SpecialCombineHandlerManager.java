package com.nativegame.juicymatch.game.algorithm.special.combine;

import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SpecialCombineHandlerManager {

    private final List<SpecialCombineHandler> mSpecialCombineHandlers = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public SpecialCombineHandlerManager(Engine engine) {
        // Add all the special combine handler
        mSpecialCombineHandlers.add(new IceCreamTileCombineHandler(engine));
        mSpecialCombineHandlers.add(new IceCreamStripedTileCombineHandler(engine));
        mSpecialCombineHandlers.add(new IceCreamExplosiveTileCombineHandler(engine));
        mSpecialCombineHandlers.add(new DoubleIceCreamCombineHandler(engine));
        mSpecialCombineHandlers.add(new DoubleExplosiveTileCombineHandler(engine));
        mSpecialCombineHandlers.add(new DoubleStripedTileCombineHandler(engine));
        mSpecialCombineHandlers.add(new ExplosiveStripedTileCombineHandler(engine));
    }
    //========================================================

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
