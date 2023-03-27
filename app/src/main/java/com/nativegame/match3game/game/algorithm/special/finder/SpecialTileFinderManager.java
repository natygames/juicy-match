package com.nativegame.match3game.game.algorithm.special.finder;

import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.nattyengine.engine.Engine;

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
        mSpecialTileFinders.add(new ColorSpecialTileFinder(engine));
        mSpecialTileFinders.add(new ExplosionXSpecialTileFinder(engine));
        mSpecialTileFinders.add(new ExplosionTSpecialTileFinder(engine));
        mSpecialTileFinders.add(new ExplosionLSpecialTileFinder(engine));
        mSpecialTileFinders.add(new RowSpecialTileFinder(engine));
        mSpecialTileFinders.add(new ColumnSpecialTileFinder(engine));
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
