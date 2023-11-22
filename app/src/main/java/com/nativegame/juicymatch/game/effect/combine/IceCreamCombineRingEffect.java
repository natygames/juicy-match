package com.nativegame.juicymatch.game.effect.combine;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeInModifier;
import com.nativegame.natyengine.entity.modifier.ScaleModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamCombineRingEffect extends Sprite {

    private static final long TIME_TO_LIVE = 600;

    private final IceCreamCombineRingEffectSystem mParent;
    private final ScaleModifier mScaleModifier;
    private final FadeInModifier mFadeInModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamCombineRingEffect(IceCreamCombineRingEffectSystem iceCreamCombineRingEffectSystem, Engine engine, Texture texture) {
        super(engine, texture);
        mParent = iceCreamCombineRingEffectSystem;
        mScaleModifier = new ScaleModifier(TIME_TO_LIVE);
        mFadeInModifier = new FadeInModifier(TIME_TO_LIVE);
        mFadeInModifier.setAutoRemove(true);
        setLayer(GameLayer.EFFECT_LAYER);
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
        mScaleModifier.update(this, elapsedMillis);
        mFadeInModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, float scale) {
        setCenterX(x);
        setCenterY(y);
        mScaleModifier.setValue(scale, 0);
        mScaleModifier.init(this);
        mFadeInModifier.init(this);
        addToGame();
    }
    //========================================================

}
