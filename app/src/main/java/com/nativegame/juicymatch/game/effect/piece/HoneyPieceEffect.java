package com.nativegame.juicymatch.game.effect.piece;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.modifier.ScaleModifier;
import com.nativegame.natyengine.entity.modifier.ScaleOutModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HoneyPieceEffect extends Sprite {

    private static final long TIME_TO_SCALE_IN = 200;
    private static final long TIME_TO_SCALE_OUT = 400;

    private final HoneyPiece mHoneyPiece;
    private final ScaleModifier mScaleInModifier;
    private final ScaleOutModifier mScaleOutModifier;
    private final FadeOutModifier mFadeOutModifier;

    private float mSpeedX;
    private float mSpeedY;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public HoneyPieceEffect(Engine engine, Texture texture, HoneyPiece honeyPiece) {
        super(engine, texture);
        mHoneyPiece = honeyPiece;
        mScaleInModifier = new ScaleModifier(0.5f, 1, TIME_TO_SCALE_IN);
        mScaleOutModifier = new ScaleOutModifier(TIME_TO_SCALE_OUT, TIME_TO_SCALE_IN);
        mScaleOutModifier.setModifyBefore(false);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_SCALE_OUT, TIME_TO_SCALE_IN);
        mFadeOutModifier.setAutoRemove(true);
        setLayer(GameLayer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public HoneyPiece getHoneyPiece() {
        return mHoneyPiece;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mX += mSpeedX * elapsedMillis;
        mY += mSpeedY * elapsedMillis;
        mScaleInModifier.update(this, elapsedMillis);
        mScaleOutModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        setCenterX(x);
        setCenterY(y);
        setRotation(mHoneyPiece.getAngle());
        mSpeedX = mHoneyPiece.getDirectionX() * 500f / 1000;
        mSpeedY = mHoneyPiece.getDirectionY() * 500f / 1000;
        mScaleInModifier.init(this);
        mScaleOutModifier.init(this);
        mFadeOutModifier.init(this);
        addToGame();
    }
    //========================================================

}
