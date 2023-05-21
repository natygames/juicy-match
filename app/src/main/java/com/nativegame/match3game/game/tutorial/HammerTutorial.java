package com.nativegame.match3game.game.tutorial;

import com.nativegame.match3game.R;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.effect.tutorial.TutorialFingerEffect;
import com.nativegame.match3game.game.effect.tutorial.TutorialHintEffectSystem;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.ui.GameButton;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HammerTutorial implements Tutorial {

    private final TutorialHintEffectSystem mHintEffect;
    private final TutorialFingerEffect mFingerEffect;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public HammerTutorial(Engine engine) {
        mHintEffect = new TutorialHintEffectSystem(engine);
        mFingerEffect = new TutorialFingerEffect(engine, Textures.TUTORIAL_FINGER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void show(GameActivity activity) {
        mHintEffect.activate(Level.LEVEL_DATA.getTutorialHint().toCharArray());
        mFingerEffect.activate(1300, 1200, 1200, 1300);

        // Click the booster button
        GameButton btnHammer = (GameButton) activity.findViewById(R.id.btn_hammer);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnHammer.performClick();
            }
        });
    }
    //========================================================

}
