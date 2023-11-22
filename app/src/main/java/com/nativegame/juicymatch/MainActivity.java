package com.nativegame.juicymatch;

import android.os.Bundle;

import com.nativegame.juicymatch.ad.AdManager;
import com.nativegame.juicymatch.timer.LivesTimer;
import com.nativegame.juicymatch.ui.fragment.LoadingFragment;
import com.nativegame.natyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/*
 *    MIT License
 *
 *    Copyright (c) 2022 NativeGame
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *    copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all
 *    copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *    SOFTWARE.
 */

public class MainActivity extends GameActivity {

    private AdManager mAdManager;
    private LivesTimer mLivesTimer;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public AdManager getAdManager() {
        return mAdManager;
    }

    public LivesTimer getLivesTimer() {
        return mLivesTimer;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_JuicyMatch);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_main);
        setFragmentContainer(R.id.layout_container);

        mAdManager = new AdManager(this);
        mLivesTimer = new LivesTimer(this);

        // Show the menu fragment
        if (savedInstanceState == null) {
            navigateToFragment(new LoadingFragment());
        }
    }
    //========================================================

}
