package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.animation.AnimatedSprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.PositionYModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleModifier;
import com.nativegame.nattyengine.texture.texture2d.Texture2DGroup;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ColumnFlashEffect extends AnimatedSprite {

    private static final long TIME_TO_ANIMATE = 300;
    private static final long TIME_TO_FADE = 300;

    private final ColumnFlashEffectSystem mParent;
    private final ScaleModifier mScaleModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final PositionYModifier mPositionModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ColumnFlashEffect(ColumnFlashEffectSystem columnFlashEffectSystem, Engine engine, Texture2DGroup textureGroup) {
        super(engine, textureGroup);
        mParent = columnFlashEffectSystem;
        mScaleModifier = new ScaleModifier(1f, 0.5f, 1f, 1.5f, TIME_TO_FADE, TIME_TO_ANIMATE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_ANIMATE);
        mPositionModifier = new PositionYModifier(TIME_TO_FADE, TIME_TO_ANIMATE);
        mPositionModifier.setAutoRemove(true);
        setAnimation(30, false);
        setAnimationAutoStart(true);
        setRotationPivotY(0);
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
        mScaleModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
        mPositionModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, FlashDirection direction) {
        setX(x - mWidth / 2f);
        setY(y);
        setRotation(direction.getAngle() - 180);
        mScaleModifier.init(this);
        mFadeOutModifier.init(this);
        mPositionModifier.setValue(mY, mY + 1800 * direction.getDirectionY());
        mPositionModifier.init(this);
        addToGame();
    }
    //========================================================

}
