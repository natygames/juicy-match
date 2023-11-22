package com.nativegame.juicymatch.game.algorithm.special.finder;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.particle.ParticleSystem;
import com.nativegame.natyengine.entity.particle.SpriteParticleSystem;

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
        mLightBgParticleSystem = new SpriteParticleSystem(engine, Textures.LIGHT_BG, MAX_FIND_NUM)
                .setDuration(750)
                .setAlpha(255, 0)
                .setScale(3, 3)
                .setLayer(GameLayer.EFFECT_LAYER);
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
