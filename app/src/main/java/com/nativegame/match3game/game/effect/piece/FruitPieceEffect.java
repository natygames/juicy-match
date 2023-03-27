package com.nativegame.match3game.game.effect.piece;

import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.SpecialType;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.modifier.Modifier;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.PositionModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.RotationModifier;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class FruitPieceEffect extends Sprite implements Modifier.ModifierListener {

    private static final long TIME_TO_LIVE = 500;

    private final FruitPieceDirection mFruitPieceDirection;
    private final RotationModifier mRotationModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final PositionModifier mPositionModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public FruitPieceEffect(Engine engine, Texture texture, FruitPieceDirection direction) {
        super(engine, texture);
        mFruitPieceDirection = direction;
        mRotationModifier = new RotationModifier(0, -45, TIME_TO_LIVE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_LIVE);
        mPositionModifier = new PositionModifier(TIME_TO_LIVE);
        mPositionModifier.setListener(this);
        setLayer(Layer.TEXT_LAYER);
        setActive(false);
        setVisible(false);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public FruitPieceDirection getFruitPieceDirection() {
        return mFruitPieceDirection;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onModifierComplete() {
        setActive(false);
        setVisible(false);
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        mRotationModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
        mPositionModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, FruitType fruitType, SpecialType specialType) {
        setCenterX(x);
        setCenterY(y);
        if (specialType != SpecialType.NONE) {
            setTexture(specialType.getPiecesTexture(fruitType)[mFruitPieceDirection.getIndex()]);
        } else {
            setTexture(fruitType.getPiecesTexture()[mFruitPieceDirection.getIndex()]);
        }
        mRotationModifier.init(this);
        mFadeOutModifier.init(this);
        mPositionModifier.setValue(mX, mX + 80 * mFruitPieceDirection.getDirection(), mY, mY + 80);
        mPositionModifier.init(this);
        setActive(true);
        setVisible(true);
    }
    //========================================================

}
