package com.nativegame.juicymatch.game.effect;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.modifier.PositionYModifier;
import com.nativegame.natyengine.entity.modifier.ScaleInModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.modifier.tween.OvershootTweener;

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
        setLayer(GameLayer.TEXT_LAYER);
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
