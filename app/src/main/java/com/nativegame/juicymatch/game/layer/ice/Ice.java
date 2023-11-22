package com.nativegame.juicymatch.game.layer.ice;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.effect.piece.ExplosionPieceEffectSystem;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.LayerSprite;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Ice extends LayerSprite {

    private static final int ICE_PIECE = 20;

    private final IceType mIceType;
    private final ExplosionPieceEffectSystem mWhiteIcePieceEffect;
    private final ExplosionPieceEffectSystem mBlueIcePieceEffect;

    private int mIceLayer;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public Ice(Engine engine, Texture texture, IceType iceType, int iceLayer) {
        super(engine, texture);
        mIceType = iceType;
        mIceLayer = iceLayer;
        mWhiteIcePieceEffect = new ExplosionPieceEffectSystem(engine, Textures.WHITE_ICE_PIECE, ICE_PIECE);
        mBlueIcePieceEffect = new ExplosionPieceEffectSystem(engine, Textures.BLUE_ICE_PIECE, ICE_PIECE);
        setRotation(iceType.getAngle());
        setLayer(GameLayer.ICE_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public IceType getIceType() {
        return mIceType;
    }

    public int getIceLayer() {
        return mIceLayer;
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void playIceEffect() {
        // Update ice layer
        mIceLayer--;

        if (mIceLayer == 0) {
            // Remove the ice
            removeFromGame();
            mBlueIcePieceEffect.activate(getCenterX(), getCenterY(), ICE_PIECE);
        } else {
            // Update ice texture
            setTexture(mIceType.getTexture(mIceLayer));
            mWhiteIcePieceEffect.activate(getCenterX(), getCenterY(), ICE_PIECE);
        }
        Sounds.ICE_EXPLODE.play();
    }
    //========================================================

}
