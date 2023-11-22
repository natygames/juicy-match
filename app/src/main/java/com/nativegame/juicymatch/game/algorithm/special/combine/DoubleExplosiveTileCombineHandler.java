package com.nativegame.juicymatch.game.algorithm.special.combine;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.effect.flash.ExplosionBeamEffectSystem;
import com.nativegame.juicymatch.game.effect.flash.ExplosionFlashEffectSystem;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.particle.ParticleSystem;
import com.nativegame.natyengine.entity.particle.SpriteParticleSystem;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class DoubleExplosiveTileCombineHandler extends BaseSpecialCombineHandler {

    private static final int GLITTER_NUM = 12;

    private final ExplosionFlashEffectSystem mFlashEffectSystem;
    private final ExplosionBeamEffectSystem mBeamEffectSystem;
    private final ParticleSystem mGlitterParticleSystem;
    private final ParticleSystem mRingLightParticleSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public DoubleExplosiveTileCombineHandler(Engine engine) {
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
        mRingLightParticleSystem = new SpriteParticleSystem(engine, Textures.FLASH_RING, 1)
                .setDuration(500)
                .setScale(0, 10)
                .setAlpha(255, 55)
                .setLayer(GameLayer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public long getStartDelay() {
        return 0;
    }

    @Override
    public boolean checkSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        // Check are both tiles explosion special tile
        if (tileA.getSpecialType() == SpecialType.EXPLOSIVE
                && tileB.getSpecialType() == SpecialType.EXPLOSIVE) {
            // We make sure the origin special tiles not being detected
            tileA.setTileState(TileState.MATCH);
            tileB.setTileState(TileState.MATCH);
            handleSpecialCombine(tiles, tileA, tileB, row, col);
            return true;
        }

        return false;
    }

    @Override
    protected void playTileEffect(Tile tileA, Tile tileB) {
        super.playTileEffect(tileA, tileB);
        mGlitterParticleSystem.oneShot(tileA.getCenterX(), tileA.getCenterY(), GLITTER_NUM);
        mFlashEffectSystem.activate(tileA.getCenterX(), tileA.getCenterY());
        mBeamEffectSystem.activate(tileA.getCenterX(), tileA.getCenterY());
        mRingLightParticleSystem.oneShot(tileA.getCenterX(), tileA.getCenterY(), 1);
        mEngine.dispatchEvent(GameEvent.PULSE_GAME);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void handleSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        int targetRow = tileA.getRow();
        int targetCol = tileA.getColumn();

        // Pop 5 X 5 tiles around
        for (int i = targetRow - 2; i <= targetRow + 2; i++) {
            for (int j = targetCol - 2; j <= targetCol + 2; j++) {
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

        playTileEffect(tileA, tileB);
    }
    //========================================================

}
