package com.nativegame.match3game.game.effect.booster;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.modifier.tween.AnticipateTweener;
import com.nativegame.nattyengine.entity.modifier.tween.OvershootTweener;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeInModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.PositionModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.PositionYModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleModifier;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class BombEffect extends Sprite {

    private static final long TIME_TO_SCALE = 500;
    private static final long TIME_TO_MOVE = 500;
    private static final long TIME_TO_FALL = 500;
    private static final long TIME_TO_FADE = 200;

    private final ScaleModifier mScaleInModifier;
    private final ScaleModifier mScaleOutModifier;
    private final FadeInModifier mFadeInModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final PositionModifier mPositionModifier;
    private final PositionYModifier mFallPositionModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public BombEffect(Engine engine, Texture texture) {
        super(engine, texture);
        mScaleInModifier = new ScaleModifier(0, 2, TIME_TO_SCALE, OvershootTweener.getInstance());
        mScaleOutModifier = new ScaleModifier(2, 1, TIME_TO_MOVE, TIME_TO_SCALE);
        mScaleOutModifier.setModifyBefore(false);
        mFadeInModifier = new FadeInModifier(TIME_TO_SCALE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_SCALE + TIME_TO_MOVE + TIME_TO_FALL);
        mFadeOutModifier.setModifyBefore(false);
        mFadeOutModifier.setAutoRemove(true);
        mPositionModifier = new PositionModifier(TIME_TO_MOVE, TIME_TO_SCALE);
        mFallPositionModifier = new PositionYModifier(TIME_TO_FALL,
                TIME_TO_SCALE + TIME_TO_MOVE, AnticipateTweener.getInstance());
        mFallPositionModifier.setModifyBefore(false);
        setLayer(Layer.EFFECT_LAYER);
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
        mFallPositionModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, float targetX, float targetY) {
        setCenterX(x);
        setCenterY(y);
        mPositionModifier.setValue(mX, targetX - 300, mY, targetY - 900);
        mFallPositionModifier.setValue(targetY - 900, targetY - 300);
        mScaleInModifier.init(this);
        mScaleOutModifier.init(this);
        mFadeInModifier.init(this);
        mFadeOutModifier.init(this);
        mPositionModifier.init(this);
        mFallPositionModifier.init(this);
        addToGame();
    }
    //========================================================

}