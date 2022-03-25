package com.example.matchgamesample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.matchgamesample.R;
import com.example.matchgamesample.Utils;
import com.example.matchgamesample.dialog.LevelDialog;
import com.example.matchgamesample.dialog.SettingDialog;
import com.example.matchgamesample.effect.sound.SoundEvent;

public class MapFragment extends BaseFragment implements LevelDialog.LevelDialogListener {

    private int mLevel;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.btn_level1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLevelDialog(1);
            }
        });
        getView().findViewById(R.id.btn_level2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLevelDialog(2);
            }
        });
        getView().findViewById(R.id.btn_level3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLevelDialog(3);
            }
        });
        getView().findViewById(R.id.btn_level4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLevelDialog(4);
            }
        });

        ImageButton imageButton = (ImageButton) getView().findViewById(R.id.btn_setting);
        Utils.createButtonEffect(imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                SettingDialog dialog = new SettingDialog(getMainActivity());
                showDialog(dialog);
            }
        });

        // Resume bgm
        getMainActivity().getSoundManager().resumeBgMusic();

    }

    private void showLevelDialog(int level) {
        mLevel = level;
        LevelDialog dialog = new LevelDialog(getMainActivity(), level);
        dialog.setListener(this);
        showDialog(dialog);
    }

    @Override
    public void startLevel() {
        getMainActivity().startGame(mLevel);
    }

    @Override
    public boolean onBackPressed() {
        if (!super.onBackPressed()) {
            getMainActivity().navigateToFragment(new MenuFragment());
            return true;
        }
        return super.onBackPressed();
    }

}
