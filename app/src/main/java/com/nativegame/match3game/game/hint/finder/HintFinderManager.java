package com.nativegame.match3game.game.hint.finder;

import com.nativegame.match3game.game.layer.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class HintFinderManager {

    private final List<HintFinder> mHintFinders = new ArrayList<>();

    public HintFinderManager() {
        mHintFinders.add(new SpecialCombineHint());
        mHintFinders.add(new MatchColorSpecialTileHint());
        mHintFinders.add(new MatchExplosionSpecialTileHint());
        mHintFinders.add(new MatchTileHint());
        mHintFinders.add(new ColorSpecialTileHint());
    }

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
