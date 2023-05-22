package com.nativegame.juicymatch.game.hint.finder;

import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SpecialCombineHintFinder implements HintFinder {

    private final List<Tile> mHintTiles = new ArrayList<>();

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public List<Tile> findHint(Tile[][] tiles, int row, int col) {
        // Clear the previous hint
        mHintTiles.clear();

        // Find two consecutive special tiles in row
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 1; j++) {
                Tile tileA = tiles[i][j];
                Tile tileB = tiles[i][j + 1];
                if (tileA.isSwappable() && tileB.isSwappable()
                        && tileA.getSpecialType() != SpecialType.NONE
                        && tileB.getSpecialType() != SpecialType.NONE) {
                    mHintTiles.add(tileA);
                    mHintTiles.add(tileB);
                    return mHintTiles;
                }
            }
        }

        // Find two consecutive special tiles in column
        for (int i = 0; i < row - 1; i++) {
            for (int j = 0; j < col; j++) {
                Tile tileA = tiles[i][j];
                Tile tileB = tiles[i + 1][j];
                if (tileA.isSwappable() && tileB.isSwappable()
                        && tileA.getSpecialType() != SpecialType.NONE
                        && tileB.getSpecialType() != SpecialType.NONE) {
                    mHintTiles.add(tileA);
                    mHintTiles.add(tileB);
                    return mHintTiles;
                }
            }
        }

        return mHintTiles;
    }
    //========================================================

}
