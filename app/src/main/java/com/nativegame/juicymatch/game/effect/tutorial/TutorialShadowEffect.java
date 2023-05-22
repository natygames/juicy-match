package com.nativegame.juicymatch.game.effect.tutorial;

import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.primitive.Color;
import com.nativegame.nattyengine.input.touch.TouchEventListener;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TutorialShadowEffect extends Color implements TouchEventListener {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TutorialShadowEffect(Engine engine, int color) {
        super(engine, color);
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

}
