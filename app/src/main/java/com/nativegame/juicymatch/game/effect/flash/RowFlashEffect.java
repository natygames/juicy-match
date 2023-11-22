package com.nativegame.juicymatch.game.effect.flash;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.modifier.PositionXModifier;
import com.nativegame.natyengine.entity.modifier.ScaleModifier;
import com.nativegame.natyengine.entity.sprite.AnimateSprite;
import com.nativegame.natyengine.texture.texture2d.Texture2DGroup;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class RowFlashEffect extends AnimateSprite {

    private static final long TIME_TO_ANIMATE = 300;
    private static final long TIME_TO_FADE = 300;

    private final RowFlashEffectSystem mParent;
    private final ScaleModifier mScaleModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final PositionXModifier mPositionModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public RowFlashEffect(RowFlashEffectSystem rowFlashEffectSystem, Engine engine, Texture2DGroup textureGroup) {
        super(engine, textureGroup);
        mParent = rowFlashEffectSystem;
        mScaleModifier = new ScaleModifier(1f, 1.5f, 1f, 0.5f, TIME_TO_FADE, TIME_TO_ANIMATE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_ANIMATE);
        mPositionModifier = new PositionXModifier(TIME_TO_FADE, TIME_TO_ANIMATE);
        mPositionModifier.setAutoRemove(true);
        setAnimation(30, false);
        setAnimationAutoStart(true);
        setRotationPivotX(0);
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
        setX(x);
        setY(y - getHeight() / 2f);
        setRotation(direction.getAngle() - 90);
        mScaleModifier.init(this);
        mFadeOutModifier.init(this);
        mPositionModifier.setValue(mX, mX + 1800 * direction.getDirectionX());
        mPositionModifier.init(this);
        addToGame();
    }
    //========================================================

}
