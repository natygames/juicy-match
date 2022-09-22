package com.nativegame.match3game.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nativegame.match3game.R;
import com.nativegame.match3game.Utils;
import com.nativegame.match3game.dialog.ExitDialog;
import com.nativegame.match3game.dialog.SettingDialog;
import com.nativegame.match3game.effect.sound.SoundEvent;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class MenuFragment extends BaseFragment {

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {
        // Init logo
        ImageView imageLogo = (ImageView) getView().findViewById(R.id.image_logo);
        Animation scaleAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.logo_scale);
        imageLogo.setScaleX(0);
        imageLogo.setScaleY(0);
        imageLogo.animate().setDuration(1000).scaleX(1).scaleY(1).setInterpolator(new OvershootInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imageLogo.startAnimation(scaleAnimation);
                    }
                });

        ImageView imageLogoBackground = (ImageView) getView().findViewById(R.id.image_logo_background);
        Animation rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.logo_rotate);
        imageLogoBackground.startAnimation(rotateAnimation);

        // Init button
        ImageButton btnPlay = (ImageButton) getView().findViewById(R.id.btn_play);
        Utils.createButtonEffect(btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                getMainActivity().navigateToFragment(new MapFragment());
            }
        });
        Animation pulseAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.button_pulse);
        btnPlay.startAnimation(pulseAnimation);

        // Init pop up
        Utils.createPopUpEffect(btnPlay);

        ImageButton btnSetting = (ImageButton) getView().findViewById(R.id.btn_setting);
        Utils.createButtonEffect(btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                SettingDialog dialog = new SettingDialog(getMainActivity());
                showDialog(dialog);
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if (!super.onBackPressed()) {
            ExitDialog quitDialog = new ExitDialog(getMainActivity()) {
                @Override
                public void exit() {
                    getMainActivity().finish();
                }
            };
            showDialog(quitDialog);
            return true;
        }
        return super.onBackPressed();
    }

}