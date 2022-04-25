package com.example.matchgamesample.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.matchgamesample.R;
import com.example.matchgamesample.Utils;
import com.example.matchgamesample.dialog.ExitDialog;
import com.example.matchgamesample.dialog.SettingDialog;
import com.example.matchgamesample.effect.sound.SoundEvent;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class MenuFragment extends BaseFragment implements ExitDialog.ExitDialogListener {

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
    public void exit() {
        getMainActivity().finish();
    }

    @Override
    public boolean onBackPressed() {
        if (!super.onBackPressed()) {
            ExitDialog quitDialog = new ExitDialog(getMainActivity());
            quitDialog.setListener(this);
            showDialog(quitDialog);
            return true;
        }
        return super.onBackPressed();
    }

}