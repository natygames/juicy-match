package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.sprite.AnimateSprite;
import com.nativegame.natyengine.texture.texture2d.Texture2DGroup;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosionFlashEffect extends AnimateSprite {

    private static final long TIME_TO_ANIMATE = 400;
    private static final long TIME_TO_FADE = 500;

    private final ExplosionFlashEffectSystem mParent;
    private final FadeOutModifier mFadeOutModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosionFlashEffect(ExplosionFlashEffectSystem explosionFlashEffectSystem, Engine engine, Texture2DGroup textureGroup) {
        super(engine, textureGroup);
        mParent = explosionFlashEffectSystem;
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_ANIMATE);
        mFadeOutModifier.setAutoRemove(true);
        setAnimation(40, false);
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
        mFadeOutModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        setCenterX(x);
        setCenterY(y);
        mFadeOutModifier.init(this);
        addToGame();
    }
    //========================================================

}
