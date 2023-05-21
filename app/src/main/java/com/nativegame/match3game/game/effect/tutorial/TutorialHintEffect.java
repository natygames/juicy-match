package com.nativegame.match3game.game.effect.tutorial;

import com.nativegame.match3game.asset.Colors;
import com.nativegame.match3game.game.JuicyMatch;
import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.shape.Rectangle;
import com.nativegame.nattyengine.input.touch.TouchEventListener;

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
        mMarginX = (JuicyMatch.WORLD_WIDTH - Level.LEVEL_DATA.getColumn() * mWidth) / 2;
        mMarginY = (JuicyMatch.WORLD_HEIGHT - Level.LEVEL_DATA.getRow() * mHeight) / 2;
        setColor(Colors.BLACK_60);
        setLayer(Layer.EFFECT_LAYER);
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
