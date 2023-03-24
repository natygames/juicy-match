package com.nativegame.match3game.game.algorithm.special.handler;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.particles.ParticleSystem;

public abstract class BaseSpecialTileHandler implements SpecialTileHandler {

    protected final Engine mEngine;
    private final ParticleSystem mLightBgParticleSystem;

    protected BaseSpecialTileHandler(Engine engine) {
        mEngine = engine;
        mLightBgParticleSystem = new ParticleSystem(engine, Textures.LIGHT_BG, 1)
                .setDuration(750)
                .setAlpha(255, 0)
                .setScale(4, 4)
                .setLayer(Layer.GRID_LAYER);
    }

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    protected void playTileEffect(Tile tile) {
        // Add light bg
        mLightBgParticleSystem.oneShot(tile.getCenterX(), tile.getCenterY(), 1);
    }
    //========================================================

}
