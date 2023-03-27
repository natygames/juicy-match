package com.nativegame.match3game.game.layer.ice;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.game.effect.piece.IcePieceEffectSystem;
import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.LayerSprite;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Ice extends LayerSprite {

    private final IcePieceEffectSystem mIcePieceEffect;
    private final IceType mIceType;

    private int mIceLayer;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public Ice(Engine engine, Texture texture, IceType iceType, int iceLayer) {
        super(engine, texture);
        mIcePieceEffect = new IcePieceEffectSystem(engine, 20);
        mIceType = iceType;
        mIceLayer = iceLayer;
        setRotation(iceType.getAngle());
        setLayer(Layer.GRID_LAYER);
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
        // play explosion effect
        mIcePieceEffect.activate(getCenterX(), getCenterY(), mIceLayer);
        Sounds.ICE_EXPLODE.play();

        // Update ice layer
        if (mIceLayer == 1) {
            // Remove the ice
            removeFromGame();
        } else {
            // Update ice texture
            setTexture(mIceType.getTexture(mIceLayer - 1));
        }
        mIceLayer--;
    }
    //========================================================

}
