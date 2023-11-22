package com.nativegame.juicymatch.game.layer.tile;

import com.nativegame.juicymatch.game.layer.LayerSpriteSystem;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TileSystem extends LayerSpriteSystem<Tile> {

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
                Tile tile = TileInitializer.createTile(this, mEngine, i, j, c);
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
