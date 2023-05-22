package com.nativegame.juicymatch.game.effect;

import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.animation.AnimatedSprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.texture.texture2d.Texture2DGroup;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SmokeEffect extends AnimatedSprite {

    private static final long TIME_TO_LIVE = 400;

    private final FadeOutModifier mFadeOutModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public SmokeEffect(Engine engine, Texture2DGroup textureGroup) {
        super(engine, textureGroup);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_LIVE);
        setAnimation(50, false);
        setAnimationAutoStart(true);
        setAnimationAutoRemove(true);
        setLayer(Layer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
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
