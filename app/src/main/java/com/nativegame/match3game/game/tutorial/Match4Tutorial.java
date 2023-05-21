package com.nativegame.match3game.game.tutorial;

import com.nativegame.match3game.asset.Colors;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.effect.tutorial.TutorialFingerEffect;
import com.nativegame.match3game.game.effect.tutorial.TutorialHintEffectSystem;
import com.nativegame.match3game.game.effect.tutorial.TutorialShadowEffect;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Match4Tutorial implements Tutorial {

    private final TutorialShadowEffect mShadowBg;
    private final TutorialHintEffectSystem mHintEffect;
    private final TutorialFingerEffect mFingerEffect;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public Match4Tutorial(Engine engine) {
        mShadowBg = new TutorialShadowEffect(engine, Colors.BLACK_80);
        mHintEffect = new TutorialHintEffectSystem(engine);
        mFingerEffect = new TutorialFingerEffect(engine, Textures.TUTORIAL_FINGER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void show(GameActivity activity) {
        mShadowBg.addToGame();
        mHintEffect.activate(Level.LEVEL_DATA.getTutorialHint().toCharArray());
        mFingerEffect.activate(900, 600, 550, 550);
    }
    //========================================================

}
