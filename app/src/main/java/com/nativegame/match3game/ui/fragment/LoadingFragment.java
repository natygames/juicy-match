package com.nativegame.match3game.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nativegame.match3game.R;
import com.nativegame.match3game.asset.Colors;
import com.nativegame.match3game.asset.Fonts;
import com.nativegame.match3game.asset.Musics;
import com.nativegame.match3game.asset.Preferences;
import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.ui.GameFragment;

public class LoadingFragment extends GameFragment {

    public LoadingFragment() {
        // Required empty public constructor
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    protected void onLayoutCreated(View view) {
        // Load assets
        Textures.load(getGameActivity().getTextureManager(), getContext());
        Sounds.load(getGameActivity().getSoundManager());
        Musics.load(getGameActivity().getMusicManager());
        Fonts.load(getContext());
        Colors.load(getContext());
        Preferences.load(getContext());

        // Navigate to menu when finish
        getGameActivity().navigateToFragment(new MenuFragment());
    }
    //========================================================

}
