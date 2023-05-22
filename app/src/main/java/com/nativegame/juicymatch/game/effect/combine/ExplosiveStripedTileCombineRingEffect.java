package com.nativegame.juicymatch.game.effect.combine;

import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleModifier;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosiveStripedTileCombineRingEffect extends Sprite {

    private static final long TIME_TO_LIVE = 300;

    private final ExplosiveStripedTileCombineRingEffectSystem mParent;
    private final ScaleModifier mScaleModifier;
    private final FadeOutModifier mFadeOutModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosiveStripedTileCombineRingEffect(ExplosiveStripedTileCombineRingEffectSystem explosiveStripedTileCombineRingEffectSystem,
                                                 Engine engine, Texture texture) {
        super(engine, texture);
        mParent = explosiveStripedTileCombineRingEffectSystem;
        mScaleModifier = new ScaleModifier(1, 2.75f, TIME_TO_LIVE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_LIVE / 2, TIME_TO_LIVE / 2);
        mFadeOutModifier.setAutoRemove(true);
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
