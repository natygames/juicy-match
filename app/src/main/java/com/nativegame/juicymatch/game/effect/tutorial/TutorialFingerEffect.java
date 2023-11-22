package com.nativegame.juicymatch.game.effect.tutorial;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.DurationModifier;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.modifier.PositionModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.input.touch.TouchEventListener;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.modifier.Modifier;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TutorialFingerEffect extends Sprite implements TouchEventListener, Modifier.ModifierListener {

    private static final long TIME_TO_LIVE = 800;
    private static final long TIME_TO_PAUSE = 400;
    private static final long TIME_TO_FADE = 200;

    private final PositionModifier mPositionModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final DurationModifier mDurationModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TutorialFingerEffect(Engine engine, Texture texture) {
        super(engine, texture);
        mPositionModifier = new PositionModifier(TIME_TO_LIVE, TIME_TO_PAUSE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_LIVE + TIME_TO_PAUSE);
        mDurationModifier = new DurationModifier(TIME_TO_LIVE, TIME_TO_LIVE + TIME_TO_PAUSE + TIME_TO_FADE);
        mDurationModifier.setListener(this);
        mDurationModifier.setLooping(true);
        setLayer(GameLayer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mPositionModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
        mDurationModifier.update(this, elapsedMillis);
    }

    @Override
    public void onTouchEvent(int type, float touchX, float touchY) {
        // Remove when player touch screen
        removeFromGame();
    }

    @Override
    public void onModifierComplete() {
        mPositionModifier.init(this);
        mFadeOutModifier.init(this);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float startX, float endX, float startY, float endY) {
        mPositionModifier.setValue(startX, endX, startY, endY);
        mPositionModifier.init(this);
        mFadeOutModifier.init(this);
        mDurationModifier.init(this);
        addToGame();
    }
    //========================================================

}
