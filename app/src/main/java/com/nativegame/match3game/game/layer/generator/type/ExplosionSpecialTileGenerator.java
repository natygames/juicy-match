package com.nativegame.match3game.game.layer.generator.type;

import com.nativegame.match3game.game.layer.generator.Generator;
import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.SpecialType;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.TileResetter;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

public class ExplosionSpecialTileGenerator extends Generator {

    private static int mCount;

    private final ExplosionSpecialTileGeneratorResetter mResetter = new ExplosionSpecialTileGeneratorResetter();

    public ExplosionSpecialTileGenerator(Engine engine, Texture texture) {
        super(engine, texture);
        mCount = 0;
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public TileResetter getResetter() {
        return mResetter;
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    private class ExplosionSpecialTileGeneratorResetter implements TileResetter {

        private static final int MAX_COUNT = 6;

        @Override
        public void resetTile(Tile tile) {
            // Check is generator and tile at the same column
            if (tile.getColumn() == mCol) {
                // Update and check the accumulated count
                mCount++;
                if (mCount >= MAX_COUNT) {
                    tile.setTileType(FruitType.STRAWBERRY);
                    tile.setSpecialType(SpecialType.EXPLOSION_SPECIAL_TILE);
                    playGeneratorEffect();
                    mCount = 0;
                }
            }
        }

    }
    //========================================================

}
