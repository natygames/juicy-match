package com.nativegame.juicymatch.game.effect.piece;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.PositionYModifier;
import com.nativegame.natyengine.entity.modifier.ScaleOutModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class StarfishPieceEffect extends Sprite {

    private static final long TIME_TO_LIVE = 500;

    private final ScaleOutModifier mScaleOutModifier;
    private final PositionYModifier mPositionModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public StarfishPieceEffect(Engine engine, Texture texture) {
        super(engine, texture);
        mScaleOutModifier = new ScaleOutModifier(TIME_TO_LIVE);
        mPositionModifier = new PositionYModifier(TIME_TO_LIVE);
        mPositionModifier.setAutoRemove(true);
        setLayer(GameLayer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mScaleOutModifier.update(this, elapsedMillis);
        mPositionModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        setCenterX(x);
        setCenterY(y);
        mScaleOutModifier.init(this);
        mPositionModifier.setValue(mY, mY + 300);
        mPositionModifier.init(this);
        addToGame();
    }
    //========================================================

}
