package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.animation.AnimatedSprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.RotationModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleOutModifier;
import com.nativegame.nattyengine.texture.texture2d.Texture2DGroup;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TransformFlashEffect extends AnimatedSprite {

    private static final long TIME_TO_LIVE = 500;
    private static final long TIME_TO_FADE = 200;

    private final TransformFlashEffectSystem mParent;
    private final RotationModifier mRotationModifier;
    private final ScaleOutModifier mScaleOutModifier;
    private final FadeOutModifier mFadeOutModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TransformFlashEffect(TransformFlashEffectSystem transformFlashEffectSystem, Engine engine, Texture2DGroup textureGroup) {
        super(engine, textureGroup);
        mParent = transformFlashEffectSystem;
        mRotationModifier = new RotationModifier(0, 90, TIME_TO_LIVE);
        mScaleOutModifier = new ScaleOutModifier(TIME_TO_FADE, TIME_TO_LIVE - TIME_TO_FADE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_LIVE - TIME_TO_FADE);
        mFadeOutModifier.setAutoRemove(true);
        setAnimation(20, false);
        setAnimationAutoStart(true);
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
        mRotationModifier.update(this, elapsedMillis);
        mScaleOutModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        setCenterX(x);
        setCenterY(y);
        mRotationModifier.init(this);
        mScaleOutModifier.init(this);
        mFadeOutModifier.init(this);
        addToGame();
    }
    //========================================================

}
