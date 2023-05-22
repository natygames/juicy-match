package com.nativegame.juicymatch.game.hint.finder;

import com.nativegame.juicymatch.game.layer.tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HintFinderManager {

    private final List<HintFinder> mHintFinders = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public HintFinderManager() {
        mHintFinders.add(new SpecialCombineHintFinder());
        mHintFinders.add(new IceCreamHintFinder());
        mHintFinders.add(new ExplosiveStripedTileHintFinder());
        mHintFinders.add(new TileHintFinder());
        mHintFinders.add(new IceCreamTileHintFind());
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public List<Tile> findHint(Tile[][] tiles, int row, int col) {
        int size = mHintFinders.size();
        for (int i = 0; i < size; i++) {
            HintFinder finder = mHintFinders.get(i);
            List<Tile> hintTiles = finder.findHint(tiles, row, col);
            // Check is any hint detected
            if (!hintTiles.isEmpty()) {
                return hintTiles;
            }
        }

        return null;
    }
    //========================================================

}
