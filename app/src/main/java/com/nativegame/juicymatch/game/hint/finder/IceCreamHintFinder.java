package com.nativegame.juicymatch.game.hint.finder;

import com.nativegame.juicymatch.algorithm.TileType;
import com.nativegame.juicymatch.game.layer.tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamHintFinder implements HintFinder {

    private final List<Tile> mHintTiles = new ArrayList<>();

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public List<Tile> findHint(Tile[][] tiles, int row, int col) {
        // Clear the previous hint
        mHintTiles.clear();

        // Find five consecutive same tiles in column
        for (int i = 0; i < row - 4; i++) {
            for (int j = 0; j < col; j++) {

                // Find consecutive same type
                TileType type = tiles[i][j].getTileType();
                if (tiles[i][j].isMatchable()
                        && type == tiles[i + 1][j].getTileType()
                        && type == tiles[i + 3][j].getTileType()
                        && type == tiles[i + 4][j].getTileType()
                        && tiles[i + 2][j].isSwappable()) {

                    // Check potential match
                    if (j > 0 && type == tiles[i + 2][j - 1].getTileType()
                            && tiles[i + 2][j - 1].isSwappable()) {
                        //  O
                        //  O
                        // O
                        //  O
                        //  O
                        mHintTiles.add(tiles[i][j]);
                        mHintTiles.add(tiles[i + 1][j]);
                        mHintTiles.add(tiles[i + 2][j - 1]);
                        mHintTiles.add(tiles[i + 3][j]);
                        mHintTiles.add(tiles[i + 4][j]);
                        return mHintTiles;
                    }
                    if (j < col - 1 && type == tiles[i + 2][j + 1].getTileType()
                            && tiles[i + 2][j + 1].isSwappable()) {
                        // O
                        // O
                        //  O
                        // O
                        // O
                        mHintTiles.add(tiles[i][j]);
                        mHintTiles.add(tiles[i + 1][j]);
                        mHintTiles.add(tiles[i + 2][j + 1]);
                        mHintTiles.add(tiles[i + 3][j]);
                        mHintTiles.add(tiles[i + 4][j]);
                        return mHintTiles;
                    }
                }
            }
        }

        // Find five consecutive same tiles in row
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 4; j++) {

                // Find consecutive same type
                TileType type = tiles[i][j].getTileType();
                if (tiles[i][j].isMatchable()
                        && type == tiles[i][j + 1].getTileType()
                        && type == tiles[i][j + 3].getTileType()
                        && type == tiles[i][j + 4].getTileType()
                        && tiles[i][j + 2].isSwappable()) {

                    // Check potential match
                    if (i > 0 && type == tiles[i - 1][j + 2].getTileType()
                            && tiles[i - 1][j + 2].isSwappable()) {
                        //   O
                        // OO OO
                        mHintTiles.add(tiles[i][j]);
                        mHintTiles.add(tiles[i][j + 1]);
                        mHintTiles.add(tiles[i - 1][j + 2]);
                        mHintTiles.add(tiles[i][j + 3]);
                        mHintTiles.add(tiles[i][j + 4]);
                        return mHintTiles;
                    }
                    if (i < row - 1 && type == tiles[i + 1][j + 2].getTileType()
                            && tiles[i + 1][j + 2].isSwappable()) {
                        // OO OO
                        //   O
                        mHintTiles.add(tiles[i][j]);
                        mHintTiles.add(tiles[i][j + 1]);
                        mHintTiles.add(tiles[i + 1][j + 2]);
                        mHintTiles.add(tiles[i][j + 3]);
                        mHintTiles.add(tiles[i][j + 4]);
                        return mHintTiles;
                    }
                }
            }
        }

        return mHintTiles;
    }
    //========================================================

}
