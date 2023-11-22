package com.nativegame.juicymatch.game.effect.booster;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeInModifier;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.modifier.PositionModifier;
import com.nativegame.natyengine.entity.modifier.ScaleModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.modifier.tween.OvershootTweener;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class GloveEffect extends Sprite {

    private static final long TIME_TO_SCALE = 500;
    private static final long TIME_TO_MOVE = 500;
    private static final long TIME_TO_PAUSE = 300;
    private static final long TIME_TO_SWAP = 300;
    private static final long TIME_TO_FADE = 200;

    private final ScaleModifier mScaleInModifier;
    private final ScaleModifier mScaleOutModifier;
    private final FadeInModifier mFadeInModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final PositionModifier mPositionModifier;
    private final PositionModifier mSwapPositionModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public GloveEffect(Engine engine, Texture texture) {
        super(engine, texture);
        mScaleInModifier = new ScaleModifier(0, 2, TIME_TO_SCALE, OvershootTweener.getInstance());
        mScaleOutModifier = new ScaleModifier(2, 1, TIME_TO_MOVE, TIME_TO_SCALE);
        mScaleOutModifier.setModifyBefore(false);
        mFadeInModifier = new FadeInModifier(TIME_TO_SCALE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_SCALE + TIME_TO_MOVE + TIME_TO_PAUSE + TIME_TO_SWAP);
        mFadeOutModifier.setModifyBefore(false);
        mFadeOutModifier.setAutoRemove(true);
        mPositionModifier = new PositionModifier(TIME_TO_MOVE, TIME_TO_SCALE);
        mSwapPositionModifier = new PositionModifier(TIME_TO_SWAP, TIME_TO_SCALE + TIME_TO_MOVE + TIME_TO_PAUSE);
        mSwapPositionModifier.setModifyBefore(false);
        setLayer(GameLayer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mScaleInModifier.update(this, elapsedMillis);
        mScaleOutModifier.update(this, elapsedMillis);
        mFadeInModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
        mPositionModifier.update(this, elapsedMillis);
        mSwapPositionModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, float targetX, float targetY, float swapX, float swapY) {
        setCenterX(x);
        setCenterY(y);
        mPositionModifier.setValue(mX, targetX, mY, targetY);
        mSwapPositionModifier.setValue(targetX, swapX, targetY, swapY);
        mScaleInModifier.init(this);
        mScaleOutModifier.init(this);
        mFadeInModifier.init(this);
        mFadeOutModifier.init(this);
        mPositionModifier.init(this);
        mSwapPositionModifier.init(this);
        addToGame();
    }
    //========================================================

}
