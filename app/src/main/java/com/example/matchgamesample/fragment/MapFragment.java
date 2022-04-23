package com.example.matchgamesample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.matchgamesample.R;
import com.example.matchgamesample.Utils;
import com.example.matchgamesample.database.DatabaseHelper;
import com.example.matchgamesample.dialog.LevelDialog;
import com.example.matchgamesample.dialog.SettingDialog;
import com.example.matchgamesample.effect.sound.SoundEvent;

import java.util.ArrayList;

public class MapFragment extends BaseFragment implements LevelDialog.LevelDialogListener {
    private int mLevel;
    private DatabaseHelper mDatabaseHelper;

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

        // Load star data
        mDatabaseHelper = new DatabaseHelper(getMainActivity());
        loadStarData();

        init();

        // Resume bgm
        getMainActivity().getSoundManager().resumeBgMusic();

    }

    private void init() {
        // Init view
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

        // Init button
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

    private void loadStarData() {

        ImageView level1Star = (ImageView) getView().findViewById(R.id.image_level1_star);
        ImageView level2Star = (ImageView) getView().findViewById(R.id.image_level2_star);
        ImageView level3Star = (ImageView) getView().findViewById(R.id.image_level3_star);
        ImageView level4Star = (ImageView) getView().findViewById(R.id.image_level4_star);

        ArrayList<ImageView> star = new ArrayList<>();
        star.add(level1Star);
        star.add(level2Star);
        star.add(level3Star);
        star.add(level4Star);

        ArrayList<Integer> data = mDatabaseHelper.getAllLevelStar();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            switch (data.get(i)) {
                case 1:
                    star.get(i).setImageResource(R.drawable.star_set_1);
                    break;
                case 2:
                    star.get(i).setImageResource(R.drawable.star_set_2);
                    break;
                case 3:
                    star.get(i).setImageResource(R.drawable.star_set_3);
                    break;
                default:

            }
        }

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
