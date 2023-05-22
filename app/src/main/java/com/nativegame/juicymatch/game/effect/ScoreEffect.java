package com.nativegame.juicymatch.game.effect;

import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.modifier.tween.OvershootTweener;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.PositionYModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleInModifier;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ScoreEffect extends Sprite {

    private static final long TIME_TO_SCALE = 500;
    private static final long TIME_TO_FADE = 400;

    private final ScoreEffectSystem mParent;
    private final ScaleInModifier mScaleInModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final PositionYModifier mPositionModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ScoreEffect(ScoreEffectSystem scoreEffectSystem, Engine engine, Texture texture) {
        super(engine, texture);
        mParent = scoreEffectSystem;
        mScaleInModifier = new ScaleInModifier(TIME_TO_SCALE, OvershootTweener.getInstance());
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_SCALE);
        mPositionModifier = new PositionYModifier(TIME_TO_FADE, TIME_TO_SCALE);
        mPositionModifier.setAutoRemove(true);
        setLayer(Layer.TEXT_LAYER);
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
        mScaleInModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
        mPositionModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, FruitType fruitType) {
        setCenterX(x);
        setCenterY(y);
        setTexture(fruitType.getScoreTexture());
        mScaleInModifier.init(this);
        mFadeOutModifier.init(this);
        mPositionModifier.setValue(mY, mY - 150);
        mPositionModifier.init(this);
        addToGame();
    }
    //========================================================

}
