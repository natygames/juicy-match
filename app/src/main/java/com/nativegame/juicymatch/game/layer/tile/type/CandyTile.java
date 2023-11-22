package com.nativegame.juicymatch.game.layer.tile.type;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.effect.piece.ExplosionPieceEffectSystem;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.particle.ParticleSystem;
import com.nativegame.natyengine.entity.particle.SpriteParticleSystem;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class CandyTile extends LayerObstacleTile {

    private static final int CANDY_PIECE = 10;
    private static final int WRAPPER_PIECE = 8;

    private final ParticleSystem mRingLightParticleSystem;
    private final ExplosionPieceEffectSystem mCandyPieceEffect;
    private final ExplosionPieceEffectSystem mWrapperPieceEffect;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public CandyTile(TileSystem tileSystem, Engine engine, Texture texture, int obstacleLayer) {
        super(tileSystem, engine, texture, obstacleLayer);
        mRingLightParticleSystem = new SpriteParticleSystem(engine, Textures.LIGHT_RING, 1)
                .setDuration(500)
                .setAlpha(255, 0, 350)
                .setScale(0, 3)
                .setLayer(GameLayer.EFFECT_LAYER);
        mCandyPieceEffect = new ExplosionPieceEffectSystem(engine, Textures.CANDY_PIECE, CANDY_PIECE);
        mWrapperPieceEffect = new ExplosionPieceEffectSystem(engine, Textures.CANDY_WRAPPER_PIECE, WRAPPER_PIECE);
    }

    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onUpdateLayer(int obstacleLayer) {
        switch (obstacleLayer) {
            case 0:
                playCandyEffect();
                break;
            case 1:
                setTexture(Textures.CANDY_01);
                playLayerEffect();
                break;
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void playCandyEffect() {
        mRingLightParticleSystem.oneShot(getCenterX(), getCenterY(), 1);
        mCandyPieceEffect.activate(getCenterX(), getCenterY(), CANDY_PIECE);
        Sounds.CANDY_EXPLODE.play();
    }

    private void playLayerEffect() {
        mWrapperPieceEffect.activate(getCenterX(), getCenterY(), WRAPPER_PIECE);
        Sounds.CANDY_WRAPPER_EXPLODE.play();
    }
    //========================================================

}
