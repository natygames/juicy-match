package com.nativegame.juicymatch.game.effect.piece;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import com.nativegame.juicymatch.asset.Colors;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.camera.Camera;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.FadeOutModifier;
import com.nativegame.natyengine.entity.modifier.ScaleOutModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.RandomUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosionPieceEffect extends Sprite {

    private static final long TIME_TO_LIVE = 1000;
    private static final long TIME_TO_FADE = 300;

    private final ExplosionPieceEffectSystem mParent;
    private final ScaleOutModifier mScaleOutModifier;
    private final FadeOutModifier mFadeOutModifier;
    private final EffectShadow mEffectShadow;
    private final float mGravity;

    private float mSpeedX;
    private float mSpeedY;
    private float mRotationSpeed;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosionPieceEffect(ExplosionPieceEffectSystem explosionPieceEffectSystem, Engine engine, Texture texture) {
        super(engine, texture);
        mParent = explosionPieceEffectSystem;
        mScaleOutModifier = new ScaleOutModifier(TIME_TO_FADE, TIME_TO_LIVE);
        mFadeOutModifier = new FadeOutModifier(TIME_TO_FADE, TIME_TO_LIVE);
        mFadeOutModifier.setAutoRemove(true);
        mEffectShadow = new EffectShadow(engine, texture);
        mGravity = 10f / 1000;
        setLayer(GameLayer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mEffectShadow.addToGame();
    }

    @Override
    public void onRemove() {
        mEffectShadow.removeFromGame();
        mParent.returnToPool(this);
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        mX += mSpeedX * elapsedMillis;
        mY += mSpeedY * elapsedMillis;
        mSpeedY += mGravity * elapsedMillis;
        mRotation += mRotationSpeed * elapsedMillis;
        mScaleOutModifier.update(this, elapsedMillis);
        mFadeOutModifier.update(this, elapsedMillis);
        mEffectShadow.updatePosition(this);
    }

    @Override
    public void onPreDraw(Canvas canvas, Camera camera) {

    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        setCenterX(x);
        setCenterY(y);
        setRotation(RandomUtils.nextInt(360));
        mSpeedX = RandomUtils.nextFloat(-1200f, 1200f) / 1000;
        mSpeedY = RandomUtils.nextFloat(-2500f, -1500f) / 1000;
        mRotationSpeed = RandomUtils.nextFloat(-720f, 720f) / 1000;
        mScaleOutModifier.init(this);
        mFadeOutModifier.init(this);
        addToGame();
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    private static class EffectShadow extends Sprite {

        private static final ColorFilter SHADOW_FILTER = new PorterDuffColorFilter(Colors.BLACK_10, PorterDuff.Mode.SRC_IN);

        //--------------------------------------------------------
        // Constructors
        //--------------------------------------------------------
        public EffectShadow(Engine engine, Texture texture) {
            super(engine, texture);
            mPaint.setColorFilter(SHADOW_FILTER);
            setLayer(GameLayer.EFFECT_LAYER - 1);
        }
        //========================================================

        //--------------------------------------------------------
        // Methods
        //--------------------------------------------------------
        public void updatePosition(Sprite sprite) {
            // Update shadow position along parent sprite
            setCenterX(sprite.getCenterX() - 100);
            setCenterY(sprite.getCenterY() + 100);
            setScaleX(sprite.getScaleX());
            setScaleY(sprite.getScaleY());
            setRotation(sprite.getRotation());
            setAlpha(sprite.getAlpha());
        }
        //========================================================

    }
    //========================================================

}
