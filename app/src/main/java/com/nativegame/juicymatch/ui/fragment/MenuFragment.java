package com.nativegame.juicymatch.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Musics;
import com.nativegame.juicymatch.asset.Preferences;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.ui.dialog.ExitDialog;
import com.nativegame.juicymatch.ui.dialog.SettingDialog;
import com.nativegame.natyengine.ui.GameButton;
import com.nativegame.natyengine.ui.GameFragment;
import com.nativegame.natyengine.ui.GameImage;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class MenuFragment extends GameFragment implements View.OnClickListener {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public MenuFragment() {
        // Required empty public constructor
    }
    //========================================================

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

        // Init logo image
        GameImage imageLogo = (GameImage) view.findViewById(R.id.image_logo);
        imageLogo.popUp(1000, 300);
        Animation scaleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.logo_pulse);
        imageLogo.startAnimation(scaleAnimation);

        GameImage imageLogoBg = (GameImage) view.findViewById(R.id.image_logo_bg);
        imageLogoBg.popUp(300, 300);
        Animation rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.logo_rotate);
        imageLogoBg.startAnimation(rotateAnimation);

        // Init button
        GameButton btnPlay = (GameButton) view.findViewById(R.id.btn_start);
        btnPlay.popUp(200, 600);
        btnPlay.setOnClickListener(this);
        Animation pulseAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.button_pulse);
        btnPlay.startAnimation(pulseAnimation);

        GameButton btnSetting = (GameButton) view.findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(this);

        // Init audio state from Preference
        boolean musicEnable = Preferences.PREF_SETTING.getBoolean(Preferences.KEY_MUSIC, true);
        boolean soundEnable = Preferences.PREF_SETTING.getBoolean(Preferences.KEY_SOUND, true);
        getGameActivity().getMusicManager().setAudioEnable(musicEnable);
        getGameActivity().getSoundManager().setAudioEnable(soundEnable);

        // Play bg music
        Musics.BG_MUSIC.play();
    }

    @Override
    public boolean onBackPressed() {
        showExitDialog();
        return true;
    }

    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_start) {
            getGameActivity().navigateToFragment(new MapFragment());
        } else if (id == R.id.btn_setting) {
            showSettingDialog();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void showExitDialog() {
        ExitDialog exitDialog = new ExitDialog(getGameActivity()) {
            @Override
            public void exit() {
                getGameActivity().finish();
            }
        };
        getGameActivity().showDialog(exitDialog);
    }

    private void showSettingDialog() {
        SettingDialog settingDialog = new SettingDialog(getGameActivity());
        getGameActivity().showDialog(settingDialog);
    }
    //========================================================

}
