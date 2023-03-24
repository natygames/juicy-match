package com.nativegame.match3game.game.layer.tile.type;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.effect.piece.CookiePiece;
import com.nativegame.match3game.game.effect.piece.CookiePieceEffect;
import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.TileSystem;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.particles.ParticleSystem;
import com.nativegame.nattyengine.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class CookieTile extends ObstacleTile {

    private static final int GLITTER_NUM = 4;
    private static final int COOKIE_PIECE_NUM = 6;

    private final ParticleSystem mExplosionParticleSystem;
    private final ParticleSystem mGlitterParticleSystem;
    private final List<CookiePieceEffect> mCookiePieceEffects = new ArrayList<>(COOKIE_PIECE_NUM);

    public CookieTile(TileSystem tileSystem, Engine engine, Texture texture) {
        super(tileSystem, engine, texture, FruitType.NONE, 1);
        mExplosionParticleSystem = new ParticleSystem(engine, Textures.LIGHT_BG, 1)
                .setDuration(750)
                .setAlpha(255, 0)
                .setLayer(Layer.EFFECT_LAYER);
        mGlitterParticleSystem = new ParticleSystem(engine, Textures.GLITTER, GLITTER_NUM)
                .setDuration(600)
                .setSpeedX(-300, 300)
                .setSpeedY(-300, 300)
                .setInitialRotation(0, 360)
                .setRotationSpeed(-360, 360)
                .setAlpha(255, 0, 400)
                .setScale(1, 0, 400)
                .setLayer(Layer.EFFECT_LAYER);
        // Init cookie pieces
        for (int i = 0; i < COOKIE_PIECE_NUM; i++) {
            CookiePiece cookiePiece = CookiePiece.values()[i];
            mCookiePieceEffects.add(new CookiePieceEffect(engine, cookiePiece.getTexture(), cookiePiece));
        }
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void playObstacleEffect(int obstacleLayer) {
        mExplosionParticleSystem.oneShot(getCenterX(), getCenterY(), 1);
        mGlitterParticleSystem.oneShot(getCenterX(), getCenterY(), GLITTER_NUM);
        playCookieEffect();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void playCookieEffect() {
        for (int i = 0; i < COOKIE_PIECE_NUM; i++) {
            mCookiePieceEffects.get(i).activate(getCenterX(), getCenterY());
        }
        mCookiePieceEffects.clear();
        Sounds.COOKIE_EXPLODE.play();
    }
    //========================================================

}
