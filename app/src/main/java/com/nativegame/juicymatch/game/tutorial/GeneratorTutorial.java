package com.nativegame.juicymatch.game.tutorial;

import com.nativegame.juicymatch.asset.Colors;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.effect.tutorial.TutorialFingerEffect;
import com.nativegame.juicymatch.game.effect.tutorial.TutorialHintEffectSystem;
import com.nativegame.juicymatch.game.effect.tutorial.TutorialShadowEffect;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class GeneratorTutorial implements Tutorial {

    private final TutorialShadowEffect mShadowBg;
    private final TutorialHintEffectSystem mHintEffect;
    private final TutorialFingerEffect mFingerEffect;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public GeneratorTutorial(Engine engine) {
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
        mFingerEffect.activate(750, 1050, 400, 400);
    }
    //========================================================

}
