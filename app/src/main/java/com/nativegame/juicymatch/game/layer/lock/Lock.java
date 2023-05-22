package com.nativegame.juicymatch.game.layer.lock;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.effect.piece.ExplosionPieceEffectSystem;
import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.juicymatch.game.layer.LayerSprite;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Lock extends LayerSprite {

    private static final int LOCK_PIECE = 4;

    private final LockType mLockType;

    private final ExplosionPieceEffectSystem mLockPieceEffect;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public Lock(Engine engine, Texture texture, LockType lockType) {
        super(engine, texture);
        mLockType = lockType;
        mLockPieceEffect = new ExplosionPieceEffectSystem(engine, Textures.LOCK_PIECE, LOCK_PIECE);
        setLayer(Layer.LOCK_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public LockType getLockType() {
        return mLockType;
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void playLockEffect() {
        mLockPieceEffect.activate(getCenterX(), getCenterY(), LOCK_PIECE);
        Sounds.LOCK_EXPLODE.play();
        // Remove the lock
        removeFromGame();
    }
    //========================================================

}
