package com.nativegame.juicymatch.game.effect;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.PositionModifier;
import com.nativegame.natyengine.entity.modifier.ScaleModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.modifier.tween.AccelerateTweener;

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
