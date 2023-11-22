package com.nativegame.juicymatch.game.effect.lightning;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.sprite.AnimateSprite;
import com.nativegame.natyengine.texture.texture2d.Texture2DGroup;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LightningEffect extends AnimateSprite implements AnimateSprite.AnimationListener {

    private static final long TIME_TO_ANIMATE = 200;
    private static final long TIME_TO_FADE = 400;

    private final LightningEffectSystem mParent;
    private final FadeOutModifier mFadeOutModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LightningEffect(LightningEffectSystem lightningEffectSystem, Engine engine, Texture2DGroup textureGroup) {
        super(engine, textureGroup);
        mParent = lightningEffectSystem;
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_ANIMATE);
        mFadeOutModifier.setAutoRemove(true);
        setAnimation(40, true);
        setAnimationListener(this);
        setAnimationAutoStart(true);
        setScalePivotY(0);
        setRotationPivotY(0);
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
        setStartFrameIndex(5);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, int distance, int angle) {
        setX(x - getWidth() / 2f);
        setY(y);
        setScaleY(distance * 1f / getHeight());
        setRotation(angle);
        mFadeOutModifier.init(this);
        addToGame();
    }
    //========================================================

}
