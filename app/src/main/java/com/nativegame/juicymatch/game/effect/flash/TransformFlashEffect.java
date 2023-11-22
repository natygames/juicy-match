package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.modifier.RotationModifier;
import com.nativegame.natyengine.entity.modifier.ScaleOutModifier;
import com.nativegame.natyengine.entity.sprite.AnimateSprite;
import com.nativegame.natyengine.texture.texture2d.Texture2DGroup;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TransformFlashEffect extends AnimateSprite {

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
