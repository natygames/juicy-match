package com.nativegame.juicymatch.game.algorithm.special.finder;

import com.nativegame.juicymatch.algorithm.TileType;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosiveTileXFinder extends QuadSpecialTileFinder {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosiveTileXFinder(Engine engine) {
        super(engine);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void findSpecialTile(Tile[][] tiles, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 2; j++) {
                // We skip unmatchable tile
                if (!tiles[i][j].isMatchable()) {
                    continue;
                }

                // Check is tile type match the next two column
                TileType type = tiles[i][j].getTileType();
                if (type == tiles[i][j + 1].getTileType()
                        && type == tiles[i][j + 2].getTileType()) {
                    // We make sure the index not out of bound
                    if (i > 0 && i < row - 1) {
                        checkCenter(tiles, i, j + 1);
                    }
                }
            }
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void checkCenter(Tile[][] tiles, int row, int col) {
        // Center shape:
        //   0
        // 0 X 0
        //   0
        TileType type = tiles[row][col].getTileType();
        if (type == tiles[row - 1][col].getTileType()
                && type == tiles[row + 1][col].getTileType()) {
            // Set target tile special type
            Tile targetTile = tiles[row][col];
            // We make sure it is not already special tile
            if (targetTile.getSpecialType() != SpecialType.NONE) {
                return;
            }
            targetTile.setSpecialType(SpecialType.EXPLOSIVE);

            // Init position factors
            setPositionXFactors(0, 0, -1, 1);
            setPositionYFactors(-1, 1, 0, 0);
            // Set upgrade to others
            setUpgradeTiles(tiles, row, col);

            playUpgradeEffect(targetTile);
        }
    }
    //========================================================

}
