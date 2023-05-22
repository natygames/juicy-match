package com.nativegame.juicymatch.game.effect.piece;

import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleOutModifier;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamPieceEffect extends Sprite {

    private static final long TIME_TO_LIVE = 500;

    private final IceCreamPieceEffectSystem mParent;
    private final ScaleOutModifier mScaleOutModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamPieceEffect(IceCreamPieceEffectSystem iceCreamPieceEffectSystem, Engine engine, Texture texture) {
        super(engine, texture);
        mParent = iceCreamPieceEffectSystem;
        mScaleOutModifier = new ScaleOutModifier(TIME_TO_LIVE);
        mScaleOutModifier.setAutoRemove(true);
        setLayer(Layer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onRemove() {
        mParent.returnToPool(this);
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        mScaleOutModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        setCenterX(x);
        setCenterY(y);
        mScaleOutModifier.init(this);
        addToGame();
    }
    //========================================================

}
