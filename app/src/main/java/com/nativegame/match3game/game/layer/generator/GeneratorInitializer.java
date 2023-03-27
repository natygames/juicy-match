package com.nativegame.match3game.game.layer.generator;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.layer.generator.type.ExplosionSpecialTileGenerator;
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
            case 'e':
                return new ExplosionSpecialTileGenerator(engine, Textures.GENERATOR_EXPLOSION);
            default:
                throw new IllegalArgumentException("Generator not found!");
        }
    }
    //========================================================

}
