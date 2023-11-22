package com.nativegame.juicymatch.game.algorithm.special.combine;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.particle.ParticleSystem;
import com.nativegame.natyengine.entity.particle.SpriteParticleSystem;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class BaseSpecialCombineHandler implements SpecialCombineHandler {

    protected final Engine mEngine;
    private final ParticleSystem mLightBgParticleSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected BaseSpecialCombineHandler(Engine engine) {
        mEngine = engine;
        mLightBgParticleSystem = new SpriteParticleSystem(engine, Textures.LIGHT_BG, 1)
                .setDuration(750)
                .setAlpha(255, 0)
                .setScale(5, 5)
                .setLayer(GameLayer.EFFECT_BG_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    protected void playTileEffect(Tile tileA, Tile tileB) {
        // Add light bg
        mLightBgParticleSystem.oneShot(tileA.getCenterX(), tileA.getCenterY(), 1);
    }
    //========================================================

}
