package com.nativegame.juicymatch.game.effect.combine;

import com.nativegame.juicymatch.game.effect.flash.FlashDirection;
import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleModifier;
import com.nativegame.nattyengine.texture.Texture;
import com.nativegame.nattyengine.util.math.RandomUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamCombineBeamEffect extends Sprite {

    private static final long TIME_TO_LIVE = 200;

    private final IceCreamCombineBeamEffectSystem mParent;
    private final ScaleModifier mScaleModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final float mSpeed;

    private float mSpeedX;
    private float mSpeedY;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamCombineBeamEffect(IceCreamCombineBeamEffectSystem iceCreamCombineBeamEffectSystem, Engine engine, Texture texture) {
        super(engine, texture);
        mParent = iceCreamCombineBeamEffectSystem;
        mScaleModifier = new ScaleModifier(TIME_TO_LIVE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_LIVE);
        mFadeOutModifier.setAutoRemove(true);
        mSpeed = 2000f / 1000;
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
        mX += mSpeedX * elapsedMillis;
        mY += mSpeedY * elapsedMillis;
        mScaleModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, FlashDirection direction) {
        setCenterX(x - direction.getDirectionX() * 600);
        setCenterY(y - direction.getDirectionY() * 600);
        setRotation(direction.getAngle());
        mSpeedX = direction.getDirectionX() * mSpeed;
        mSpeedY = direction.getDirectionY() * mSpeed;
        mScaleModifier.setValue(RandomUtils.nextFloat(1.5f, 2.5f), 0.5f);
        mScaleModifier.init(this);
        mFadeOutModifier.init(this);
        addToGame();
    }
    //========================================================

}
