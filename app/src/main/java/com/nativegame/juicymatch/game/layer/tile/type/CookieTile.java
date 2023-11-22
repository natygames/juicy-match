package com.nativegame.juicymatch.game.layer.tile.type;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.effect.piece.CookiePiece;
import com.nativegame.juicymatch.game.effect.piece.CookiePieceEffect;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.particle.ParticleSystem;
import com.nativegame.natyengine.entity.particle.SpriteParticleSystem;
import com.nativegame.natyengine.texture.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class CookieTile extends ObstacleTile {

    private static final int GLITTER_NUM = 4;
    private static final int COOKIE_PIECE = 6;

    private final ParticleSystem mExplosionParticleSystem;
    private final ParticleSystem mGlitterParticleSystem;
    private final List<CookiePieceEffect> mCookiePieceEffects = new ArrayList<>(COOKIE_PIECE);

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public CookieTile(TileSystem tileSystem, Engine engine, Texture texture) {
        super(tileSystem, engine, texture);
        mExplosionParticleSystem = new SpriteParticleSystem(engine, Textures.LIGHT_BG, 1)
                .setDuration(750)
                .setAlpha(255, 0)
                .setLayer(GameLayer.EFFECT_LAYER);
        mGlitterParticleSystem = new SpriteParticleSystem(engine, Textures.GLITTER, GLITTER_NUM)
                .setDuration(600)
                .setSpeedX(-300, 300)
                .setSpeedY(-300, 300)
                .setInitialRotation(0, 360)
                .setRotationSpeed(-360, 360)
                .setAlpha(255, 0, 400)
                .setScale(1, 0, 400)
                .setLayer(GameLayer.EFFECT_LAYER);
        // Init cookie pieces
        for (int i = 0; i < COOKIE_PIECE; i++) {
            CookiePiece cookiePiece = CookiePiece.values()[i];
            mCookiePieceEffects.add(new CookiePieceEffect(engine, cookiePiece.getTexture(), cookiePiece));
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void playTileEffect() {
        if (mIsObstacle) {
            playCookieEffect();
            mIsObstacle = false;
            return;
        }
        super.playTileEffect();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void playCookieEffect() {
        mExplosionParticleSystem.oneShot(getCenterX(), getCenterY(), 1);
        mGlitterParticleSystem.oneShot(getCenterX(), getCenterY(), GLITTER_NUM);
        for (int i = 0; i < COOKIE_PIECE; i++) {
            mCookiePieceEffects.get(i).activate(getCenterX(), getCenterY());
        }
        mCookiePieceEffects.clear();
        Sounds.COOKIE_EXPLODE.play();
    }
    //========================================================

}
