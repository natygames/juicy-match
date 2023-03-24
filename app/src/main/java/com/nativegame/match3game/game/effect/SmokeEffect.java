package com.nativegame.match3game.game.effect;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.animation.AnimatedSprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.texture.TextureGroup;

public class SmokeEffect extends AnimatedSprite {

    private final FadeOutModifier mFadeOutModifier = new FadeOutModifier(400);

    public SmokeEffect(Engine engine, TextureGroup textureGroup) {
        super(engine, textureGroup);
        setAnimation(50, false);
        setAnimationAutoStart(true);
        setAnimationAutoRemove(true);
        setLayer(Layer.EFFECT_LAYER);
    }

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
