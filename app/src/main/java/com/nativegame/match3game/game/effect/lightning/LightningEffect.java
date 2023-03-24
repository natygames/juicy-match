package com.nativegame.match3game.game.effect.lightning;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.animation.AnimatedSprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.texture.TextureGroup;

public class LightningEffect extends AnimatedSprite implements AnimatedSprite.AnimationListener {

    private static final long TIME_TO_ANIMATE = 200;
    private static final long TIME_TO_FADE = 400;

    private final LightningEffectSystem mParent;
    private final FadeOutModifier mFadeOutModifier;

    public LightningEffect(LightningEffectSystem lightningEffectSystem, Engine engine, TextureGroup textureGroup) {
        super(engine, textureGroup);
        mParent = lightningEffectSystem;
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_ANIMATE);
        mFadeOutModifier.setAutoRemove(true);
        setAnimation(40, true);
        setAnimationListener(this);
        setAnimationAutoStart(true);
        setScalePivotY(0);
        setRotationPivotY(0);
        setLayer(Layer.EFFECT_LAYER);
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onRemove() {
        mParent.returnToPool(this);
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        mFadeOutModifier.update(this, elapsedMillis);
    }

    @Override
    public void onAnimationStart() {
    }

    @Override
    public void onAnimationStop() {
    }

    @Override
    public void onAnimationPause() {
    }

    @Override
    public void onAnimationResume() {
    }

    @Override
    public void onAnimationRepeat() {
        // We only repeat the full lightning animation
        setStartIndex(5);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, int distance, int angle) {
        setX(x - mWidth / 2f);
        setY(y);
        setScaleY(distance * 1f / mHeight);
        setRotation(angle);
        mFadeOutModifier.init(this);
        addToGame();
    }
    //========================================================

}
