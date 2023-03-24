package com.nativegame.match3game.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nativegame.match3game.R;
import com.nativegame.match3game.asset.Musics;
import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.database.DatabaseHelper;
import com.nativegame.match3game.level.Level;
import com.nativegame.match3game.ui.dialog.LevelDialog;
import com.nativegame.match3game.ui.dialog.SettingDialog;
import com.nativegame.nattyengine.ui.GameButton;
import com.nativegame.nattyengine.ui.GameFragment;
import com.nativegame.nattyengine.ui.GameImage;
import com.nativegame.nattyengine.ui.GameText;

import java.util.List;

public class MapFragment extends GameFragment implements View.OnClickListener {

    private static final int TOTAL_LEVEL = 3;
    private static final int TOTAL_BUTTON = 20;

    public MapFragment() {
        // Required empty public constructor
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GameButton btnSetting = (GameButton) view.findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(this);
        loadLevel(view);
        loadStar(view);
        Musics.BG_MUSIC.play();
    }

    @Override
    public boolean onBackPressed() {
        getGameActivity().navigateToFragment(new MenuFragment());
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_setting) {
            SettingDialog dialog = new SettingDialog(getGameActivity());
            showDialog(dialog);
            Sounds.BUTTON_CLICK.play();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void loadLevel(View view) {
        for (int i = 1; i <= TOTAL_BUTTON; i++) {

            // Init view id
            String name = "btn_level_" + i;
            int id = getResources().getIdentifier(name, "id", getActivity().getPackageName());

            // Init view
            GameText text = (GameText) view.findViewById(id);
            text.setText(String.valueOf(i));
            text.popUp(200, i * 50);

            if (i <= TOTAL_LEVEL) {
                int level = i;
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showLevelDialog(level);
                    }
                });
                text.setBackgroundResource(R.drawable.btn_level);
                text.setEnabled(true);
            } else {
                text.setBackgroundResource(R.drawable.btn_level_lock);
                text.setEnabled(false);
            }
        }
    }

    private void loadStar(View view) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
        List<Integer> stars = databaseHelper.getAllLevelStar();
        int totalStar = stars.size();

        for (int i = 1; i <= TOTAL_BUTTON; i++) {

            // Init view id
            String name = "image_level_star_" + i;
            int id = getResources().getIdentifier(name, "id", getActivity().getPackageName());

            // Init view
            GameImage image = (GameImage) view.findViewById(id);
            image.popUp(200, i * 50);

            if (i <= totalStar) {
                switch (stars.get(i - 1)) {
                    case 1:
                        image.setBackgroundResource(R.drawable.star_set_01);
                        break;
                    case 2:
                        image.setBackgroundResource(R.drawable.star_set_02);
                        break;
                    case 3:
                        image.setBackgroundResource(R.drawable.star_set_03);
                        break;
                }
                image.setVisibility(View.VISIBLE);
            } else {
                image.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void showLevelDialog(int level) {
        // We load level data here before starting game
        Level.load(getContext(), level);
        LevelDialog levelDialog = new LevelDialog(getGameActivity()) {
            @Override
            public void startGame() {
                getGameActivity().navigateToFragment(new MyGameFragment());
                Musics.BG_MUSIC.stop();
            }
        };
        showDialog(levelDialog);
    }
    //========================================================

}
