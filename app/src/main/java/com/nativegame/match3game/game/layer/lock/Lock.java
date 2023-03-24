package com.nativegame.match3game.game.layer.lock;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.game.effect.piece.LockPiece;
import com.nativegame.match3game.game.effect.piece.LockPieceEffect;
import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.LayerSprite;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class Lock extends LayerSprite {

    private static final int LOCK_PIECE_NUM = 5;

    private final LockType mLockType;

    private final List<LockPieceEffect> mLockPieceEffects = new ArrayList<>(LOCK_PIECE_NUM);

    public Lock(Engine engine, Texture texture, LockType lockType) {
        super(engine, texture);
        mLockType = lockType;
        // Init lock pieces
        for (int i = 0; i < LOCK_PIECE_NUM; i++) {
            LockPiece lockPiece = LockPiece.values()[i];
            mLockPieceEffects.add(new LockPieceEffect(engine, lockPiece.getTexture(), lockPiece));
        }
        setLayer(Layer.EFFECT_LAYER);
    }

    public LockType getLockType() {
        return mLockType;
    }

    public void playLockEffect() {
        // play explosion effect
        for (int i = 0; i < LOCK_PIECE_NUM; i++) {
            mLockPieceEffects.get(i).activate(getCenterX(), getCenterY());
        }
        mLockPieceEffects.clear();
        Sounds.LOCK_EXPLODE.play();

        // Remove the lock
        removeFromGame();
    }

}
