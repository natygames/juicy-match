package com.nativegame.match3game.game.layer.generator;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.layer.generator.type.ExplosionSpecialTileGenerator;
import com.nativegame.nattyengine.engine.Engine;

public class GeneratorInitializer {

    public static Generator getGenerator(Engine engine, char c) {
        switch (c) {
            case 'e':
                return new ExplosionSpecialTileGenerator(engine, Textures.GENERATOR_EXPLOSION);
            default:
                throw new IllegalArgumentException("Generator not found!");
        }
    }

}
