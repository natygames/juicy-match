package com.nativegame.match3game.game.layer.generator;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.layer.generator.type.ColumnStripedGenerator;
import com.nativegame.match3game.game.layer.generator.type.ExplosiveGenerator;
import com.nativegame.match3game.game.layer.generator.type.RowColumnStripedGenerator;
import com.nativegame.match3game.game.layer.generator.type.RowStripedGenerator;
import com.nativegame.nattyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class GeneratorInitializer {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static Generator getGenerator(Engine engine, char c) {
        switch (c) {
            case 'R':
                return new RowStripedGenerator(engine, Textures.GENERATOR_ROW_STRIPED);
            case 'C':
                return new ColumnStripedGenerator(engine, Textures.GENERATOR_COLUMN_STRIPED);
            case 'M':
                return new RowColumnStripedGenerator(engine, Textures.GENERATOR_ROW_COLUMN_STRIPED);
            case 'E':
                return new ExplosiveGenerator(engine, Textures.GENERATOR_EXPLOSIVE);
            default:
                throw new IllegalArgumentException("Generator not found!");
        }
    }
    //========================================================

}
