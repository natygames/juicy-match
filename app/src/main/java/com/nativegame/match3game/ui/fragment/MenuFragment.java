package com.nativegame.match3game.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nativegame.match3game.R;
import com.nativegame.match3game.asset.Musics;
import com.nativegame.match3game.asset.Preferences;
import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.ui.dialog.ExitDialog;
import com.nativegame.match3game.ui.dialog.SettingDialog;
import com.nativegame.nattyengine.ui.GameButton;
import com.nativegame.nattyengine.ui.GameFragment;
import com.nativegame.nattyengine.ui.GameImage;

public class MenuFragment extends GameFragment implements View.OnClickListener {

    public MenuFragment() {
        // Required empty public constructor
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Musics.BG_MUSIC.play();
        init();
    }

    @Override
    public boolean onBackPressed() {
        ExitDialog quitDialog = new ExitDialog(getGameActivity()) {
            @Override
            public void exit() {
                getGameActivity().finish();
            }
        };
        showDialog(quitDialog);
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_start) {
            getGameActivity().navigateToFragment(new MapFragment());
            Sounds.BUTTON_CLICK.play();
        } else if (id == R.id.btn_setting) {
            SettingDialog dialog = new SettingDialog(getGameActivity());
            showDialog(dialog);
            Sounds.BUTTON_CLICK.play();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void init() {
        // Init logo
        GameImage imageLogo = (GameImage) getView().findViewById(R.id.image_logo);
        imageLogo.popUp(1000, 300);
        Animation scaleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.logo_pulse);
        imageLogo.startAnimation(scaleAnimation);

        GameImage imageLogoBg = (GameImage) getView().findViewById(R.id.image_logo_bg);
        imageLogoBg.popUp(300, 300);
        Animation rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.logo_rotate);
        imageLogoBg.startAnimation(rotateAnimation);

        // Init button
        GameButton btnPlay = (GameButton) getView().findViewById(R.id.btn_start);
        btnPlay.popUp(300, 500);
        btnPlay.setOnClickListener(this);
        Animation pulseAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.button_pulse);
        btnPlay.startAnimation(pulseAnimation);

        GameButton btnSetting = (GameButton) getView().findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(this);

        // Init audio state
        boolean musicEnable = Preferences.PREF_SETTING.getBoolean(Preferences.KEY_MUSIC, true);
        boolean soundEnable = Preferences.PREF_SETTING.getBoolean(Preferences.KEY_SOUND, true);
        getGameActivity().getMusicManager().setAudioEnable(musicEnable);
        getGameActivity().getSoundManager().setAudioEnable(soundEnable);
    }
    //========================================================

}
