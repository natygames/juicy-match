package com.nativegame.juicymatch.game.effect;

import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.modifier.tween.AccelerateTweener;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.PositionModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleModifier;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class UpgradeFruitEffect extends Sprite {

    private static final int TIME_TO_LIVE = 300;

    private final UpgradeFruitEffectSystem mParent;
    private final ScaleModifier mScaleModifier;
    private final PositionModifier mPositionModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public UpgradeFruitEffect(UpgradeFruitEffectSystem upgradeFruitEffectSystem, Engine engine, Texture texture) {
        super(engine, texture);
        mParent = upgradeFruitEffectSystem;
        mScaleModifier = new ScaleModifier(1.5f, 0.75f, TIME_TO_LIVE, AccelerateTweener.getInstance());
        mPositionModifier = new PositionModifier(TIME_TO_LIVE, AccelerateTweener.getInstance());
        mPositionModifier.setAutoRemove(true);
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
        mPositionModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float startX, float startY, float endX, float endY, FruitType fruitType) {
        setTexture(fruitType.getTexture());
        mScaleModifier.init(this);
        mPositionModifier.setValue(startX, endX, startY, endY);
        mPositionModifier.init(this);
        addToGame();
    }
    //========================================================

}
