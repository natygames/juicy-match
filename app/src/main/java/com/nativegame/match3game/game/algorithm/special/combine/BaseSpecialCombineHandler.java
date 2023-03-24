package com.nativegame.match3game.game.algorithm.special.combine;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.particles.ParticleSystem;

public abstract class BaseSpecialCombineHandler implements SpecialCombineHandler {

    protected final Engine mEngine;
    private final ParticleSystem mLightBgParticleSystem;

    protected BaseSpecialCombineHandler(Engine engine) {
        mEngine = engine;
        mLightBgParticleSystem = new ParticleSystem(engine, Textures.LIGHT_BG, 1)
                .setDuration(750)
                .setAlpha(255, 0)
                .setScale(5, 5)
                .setLayer(Layer.GRID_LAYER);
    }

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    protected void playTileEffect(Tile tileA, Tile tileB) {
        // Add light bg
        mLightBgParticleSystem.oneShot(tileA.getCenterX(), tileA.getCenterY(), 1);
    }
    //========================================================

}
