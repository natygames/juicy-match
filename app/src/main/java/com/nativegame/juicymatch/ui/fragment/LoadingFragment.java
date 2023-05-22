package com.nativegame.juicymatch.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.MobileAds;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Colors;
import com.nativegame.juicymatch.asset.Fonts;
import com.nativegame.juicymatch.asset.Musics;
import com.nativegame.juicymatch.asset.Preferences;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.nattyengine.ui.GameFragment;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LoadingFragment extends GameFragment {

    private static final long TIME_TO_LOAD = 800;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LoadingFragment() {
        // Required empty public constructor
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load assets
        Textures.load(getGameActivity().getTextureManager(), getContext());
        Sounds.load(getGameActivity().getSoundManager());
        Musics.load(getGameActivity().getMusicManager());
        Fonts.load(getContext());
        Colors.load(getContext());
        Preferences.load(getContext());

        // Init ad
        MobileAds.initialize(getContext());

        // Navigate to menu when loading complete
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                getGameActivity().navigateToFragment(new MenuFragment());
            }
        }, TIME_TO_LOAD);
    }
    //========================================================

}
