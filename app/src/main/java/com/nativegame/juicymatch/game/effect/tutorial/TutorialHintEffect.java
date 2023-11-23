package com.nativegame.juicymatch.game.effect.tutorial;

import android.graphics.Color;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.GameWorld;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.shape.primitive.Rectangle;
import com.nativegame.natyengine.input.touch.TouchEventListener;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TutorialHintEffect extends Rectangle implements TouchEventListener {

    protected final int mMarginX;
    protected final int mMarginY;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TutorialHintEffect(Engine engine, int width, int height) {
        super(engine, width, height);
        mMarginX = (GameWorld.WORLD_WIDTH - Level.LEVEL_DATA.getColumn() * width) / 2;
        mMarginY = (GameWorld.WORLD_HEIGHT - Level.LEVEL_DATA.getRow() * height) / 2;
        mPaint.setColor(Color.BLACK);
        setAlpha(200);
        setLayer(GameLayer.EFFECT_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onTouchEvent(int type, float touchX, float touchY) {
        // Remove when player touch screen
        removeFromGame();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y) {
        mX = x + mMarginX;
        mY = y + mMarginY;
        addToGame();
    }
    //========================================================

}
