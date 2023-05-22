package com.nativegame.juicymatch.game.effect;

import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.modifier.tween.OvershootTweener;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleModifier;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TextEffect extends Sprite {

    private static final long TIME_TO_LIVE = 1000;
    private static final long TIME_TO_FADE = 300;

    private final ScaleModifier mScaleModifier;
    private final FadeOutModifier mFadeOutModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TextEffect(Engine engine, Texture texture) {
        super(engine, texture);
        mScaleModifier = new ScaleModifier(5, 2, TIME_TO_FADE, OvershootTweener.getInstance());
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_LIVE);
        mFadeOutModifier.setAutoRemove(true);
        setLayer(Layer.TEXT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mScaleModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        setCenterX(x);
        setCenterY(y);
        mScaleModifier.init(this);
        mFadeOutModifier.init(this);
        addToGame();
    }
    //========================================================

}
