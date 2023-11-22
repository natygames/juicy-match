package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.modifier.PositionYModifier;
import com.nativegame.natyengine.entity.modifier.ScaleModifier;
import com.nativegame.natyengine.entity.sprite.AnimateSprite;
import com.nativegame.natyengine.texture.texture2d.Texture2DGroup;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ColumnFlashEffect extends AnimateSprite {

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
        mScaleModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
        mPositionModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, FlashDirection direction) {
        setX(x - getWidth() / 2f);
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
