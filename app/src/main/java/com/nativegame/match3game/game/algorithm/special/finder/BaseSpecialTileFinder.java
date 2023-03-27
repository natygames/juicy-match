package com.nativegame.match3game.game.algorithm.special.finder;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.particles.ParticleSystem;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class BaseSpecialTileFinder implements SpecialTileFinder {

    protected static final int MAX_FIND_NUM = 3;

    private final ParticleSystem mLightBgParticleSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected BaseSpecialTileFinder(Engine engine) {
        mLightBgParticleSystem = new ParticleSystem(engine, Textures.LIGHT_BG, MAX_FIND_NUM)
                .setDuration(750)
                .setAlpha(255, 0)
                .setScale(3, 3)
                .setLayer(Layer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    protected abstract void setUpgradeTiles(Tile[][] tiles, int row, int col);

    protected void playUpgradeEffect(Tile tile) {
        // Add light bg
        mLightBgParticleSystem.oneShot(tile.getCenterX(), tile.getCenterY(), 1);
    }
    //========================================================

}
