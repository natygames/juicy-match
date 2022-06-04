package com.nativegame.match3game.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nativegame.match3game.R;
import com.nativegame.match3game.Utils;
import com.nativegame.match3game.database.DatabaseHelper;
import com.nativegame.match3game.dialog.LevelDialog;
import com.nativegame.match3game.dialog.SettingDialog;
import com.nativegame.match3game.effect.sound.SoundEvent;

import java.util.ArrayList;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class MapFragment extends BaseFragment {

    private DatabaseHelper mDatabaseHelper;

    private ArrayList<Integer> mLevelStar;
    private static final int TOTAL_LEVEL = 4;
    private static final int TOTAL_BUTTON = 20;

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

        // Load star database
        mDatabaseHelper = getMainActivity().getDatabaseHelper();

        // Init level
        mLevelStar = mDatabaseHelper.getAllLevelStar();

        // Init button and star
        init();
        loadStarData();

        // Resume bgm
        getMainActivity().getSoundManager().resumeBgMusic();

    }

    private void init() {
        // Init button listener and star
        for (int i = 1; i <= TOTAL_BUTTON; i++) {
            // Get current level
            int level = i;

            // Get level button name
            String levelName = "btn_level" + i;
            int levelId = getResources().getIdentifier(levelName, "id", getMainActivity().getPackageName());

            // Init button
            TextView textView = (TextView) getView().findViewById(levelId);

            // Init button listener
            if (level <= TOTAL_LEVEL) {
                Utils.createButtonEffect(textView);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                        showLevelDialog(level);
                    }
                });
            }

            // Init pop up
            Utils.createPopUpEffect(textView, i - 1);
        }

        // Init button
        ImageButton imageButton = (ImageButton) getView().findViewById(R.id.btn_setting);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                SettingDialog dialog = new SettingDialog(getMainActivity());
                showDialog(dialog);
            }
        });
        Utils.createButtonEffect(imageButton);
        Utils.createButtonEffect(getView().findViewById(R.id.btn_level_next));
        Utils.createButtonEffect(getView().findViewById(R.id.btn_level_previous));
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

        int size = mLevelStar.size();
        for (int i = 0; i < TOTAL_LEVEL; i++) {

            if (i < size) {
                star.get(i).setVisibility(View.VISIBLE);

                switch (mLevelStar.get(i)) {
                    case 1:
                        star.get(i).setBackgroundResource(R.drawable.star_set_1);
                        break;
                    case 2:
                        star.get(i).setBackgroundResource(R.drawable.star_set_2);
                        break;
                    case 3:
                        star.get(i).setBackgroundResource(R.drawable.star_set_3);
                        break;
                }

                // Init pop up
                Utils.createPopUpEffect(star.get(i), i);
            } else {
                star.get(i).setVisibility(View.INVISIBLE);
            }

        }

    }

    private void showLevelDialog(int level) {
        LevelDialog dialog = new LevelDialog(getMainActivity(), level) {
            @Override
            public void startGame() {
                getMainActivity().startGame(level);
            }
        };
        showDialog(dialog);
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
