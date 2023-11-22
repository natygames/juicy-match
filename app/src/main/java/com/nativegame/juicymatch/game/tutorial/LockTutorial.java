package com.nativegame.juicymatch.game.tutorial;

import com.nativegame.juicymatch.asset.Colors;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameWorld;
import com.nativegame.juicymatch.game.effect.tutorial.TutorialFingerEffect;
import com.nativegame.juicymatch.game.effect.tutorial.TutorialHintEffectSystem;
import com.nativegame.juicymatch.game.effect.tutorial.TutorialShadowEffect;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LockTutorial implements Tutorial {

    private final TutorialShadowEffect mShadowBg;
    private final TutorialHintEffectSystem mHintEffect;
    private final TutorialFingerEffect mFingerEffect;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LockTutorial(Engine engine) {
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
        int marginX = (GameWorld.WORLD_WIDTH - Level.LEVEL_DATA.getColumn() * 300) / 2;
        int marginY = (GameWorld.WORLD_HEIGHT - Level.LEVEL_DATA.getRow() * 300) / 2;
        mFingerEffect.activate(marginX + 300, marginX + 600, marginY + 400, marginY + 400);
    }
    //========================================================

}
