package com.nativegame.juicymatch.game.algorithm.special.handler;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.effect.flash.ExplosionBeamEffectSystem;
import com.nativegame.juicymatch.game.effect.flash.ExplosionFlashEffectSystem;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.particle.ParticleSystem;
import com.nativegame.natyengine.entity.particle.SpriteParticleSystem;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosiveTileHandler extends BaseSpecialTileHandler {

    private static final int GLITTER_NUM = 8;

    private final ExplosionFlashEffectSystem mFlashEffectSystem;
    private final ExplosionBeamEffectSystem mBeamEffectSystem;
    private final ParticleSystem mGlitterParticleSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosiveTileHandler(Engine engine) {
        super(engine);
        mFlashEffectSystem = new ExplosionFlashEffectSystem(engine, 1);
        mBeamEffectSystem = new ExplosionBeamEffectSystem(engine, 8);
        mGlitterParticleSystem = new SpriteParticleSystem(engine, Textures.GLITTER, GLITTER_NUM)
                .setDuration(600)
                .setSpeedWithAngle(1500, 2500)
                .setInitialRotation(0, 360)
                .setRotationSpeed(-360, 360)
                .setAlpha(255, 0, 200)
                .setScale(1.2f, 0.5f, 200)
                .setLayer(GameLayer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void handleSpecialTile(Tile[][] tiles, Tile tile, int row, int col) {
        int targetRow = tile.getRow();
        int targetCol = tile.getColumn();
        // Pop 3 X 3 tiles around
        for (int i = targetRow - 1; i <= targetRow + 1; i++) {
            for (int j = targetCol - 1; j <= targetCol + 1; j++) {
                // We make sure the index not out of bound
                if (i < 0 || i > row - 1 || j < 0 || j > col - 1) {
                    continue;
                }
                Tile t = tiles[i][j];
                // We make sure not pop the tile multiple time
                if (t.getTileState() == TileState.IDLE) {
                    t.popTile();
                }
            }
        }

        playTileEffect(tile);
    }

    @Override
    protected void playTileEffect(Tile tile) {
        super.playTileEffect(tile);
        mGlitterParticleSystem.oneShot(tile.getCenterX(), tile.getCenterY(), GLITTER_NUM);
        mFlashEffectSystem.activate(tile.getCenterX(), tile.getCenterY());
        mBeamEffectSystem.activate(tile.getCenterX(), tile.getCenterY());
        mEngine.dispatchEvent(GameEvent.PULSE_GAME);
    }
    //========================================================

}
