package com.nativegame.match3game;

import android.os.Bundle;

import com.nativegame.match3game.ui.fragment.LoadingFragment;
import com.nativegame.nattyengine.ui.GameActivity;

public class MainActivity extends GameActivity {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_JuicyMatchTest);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_main);
        setContainerView(R.id.layout_container);

        // Show the menu fragment
        if (savedInstanceState == null) {
            navigateToFragment(new LoadingFragment());
        }
    }
    //========================================================

}
