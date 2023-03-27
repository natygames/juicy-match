package com.nativegame.match3game.game.layer.tile;

import com.nativegame.match3game.game.layer.LayerSystem;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TileSystem extends LayerSystem<Tile> {

    private final Tile[][] mTiles;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TileSystem(Engine engine) {
        super(engine);
        mTiles = new Tile[mTotalRow][mTotalCol];
        initTile(Level.LEVEL_DATA.getTile().toCharArray());
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public Tile[][] getChild() {
        return mTiles;
    }

    @Override
    public Tile getChildAt(int row, int col) {
        return mTiles[row][col];
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void initTile(char[] chars) {
        for (int i = 0; i < mTotalRow; i++) {
            for (int j = 0; j < mTotalCol; j++) {
                char c = chars[i * mTotalCol + j];
                Tile tile = TileInitializer.createTile(this, mEngine, c);
                tile.setPosition(i, j);
                if (c != 'e') {
                    // We do not add empty tile
                    tile.addToGame();
                }
                mTiles[i][j] = tile;
            }
        }
    }
    //========================================================

}
