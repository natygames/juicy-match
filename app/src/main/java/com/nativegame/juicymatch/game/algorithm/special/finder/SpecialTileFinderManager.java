package com.nativegame.juicymatch.game.algorithm.special.finder;

import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SpecialTileFinderManager {

    private final List<SpecialTileFinder> mSpecialTileFinders = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public SpecialTileFinderManager(Engine engine) {
        // Add all the special tile finder
        mSpecialTileFinders.add(new IceCreamFinder(engine));
        mSpecialTileFinders.add(new ExplosiveTileXFinder(engine));
        mSpecialTileFinders.add(new ExplosiveTileTFinder(engine));
        mSpecialTileFinders.add(new ExplosiveTileLFinder(engine));
        mSpecialTileFinders.add(new RowStripedTileFinder(engine));
        mSpecialTileFinders.add(new ColumnStripedTileFinder(engine));
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void findSpecialTile(Tile[][] tiles, int row, int col) {
        int size = mSpecialTileFinders.size();
        for (int i = 0; i < size; i++) {
            SpecialTileFinder finder = mSpecialTileFinders.get(i);
            finder.findSpecialTile(tiles, row, col);
        }
    }
    //========================================================

}
