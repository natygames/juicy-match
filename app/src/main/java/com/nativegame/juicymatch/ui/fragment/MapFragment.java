package com.nativegame.juicymatch.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nativegame.juicymatch.MainActivity;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.database.DatabaseHelper;
import com.nativegame.juicymatch.item.Item;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.juicymatch.timer.LivesTimer;
import com.nativegame.juicymatch.ui.dialog.LevelDialog;
import com.nativegame.juicymatch.ui.dialog.MoreCoinDialog;
import com.nativegame.juicymatch.ui.dialog.MoreLivesDialog;
import com.nativegame.juicymatch.ui.dialog.SettingDialog;
import com.nativegame.juicymatch.ui.dialog.ShopDialog;
import com.nativegame.juicymatch.ui.dialog.WheelDialog;
import com.nativegame.natyengine.ui.GameButton;
import com.nativegame.natyengine.ui.GameFragment;
import com.nativegame.natyengine.ui.GameImage;
import com.nativegame.natyengine.ui.GameText;

import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class MapFragment extends GameFragment implements View.OnClickListener {

    private static final int TOTAL_LEVEL = 100;
    private static final int LEVEL_PRE_PAGE = 20;
    private static final int MAX_PAGE = 5;

    private LivesTimer mLivesTimer;

    private int mCurrentLevel;
    private int mCurrentPage;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public MapFragment() {
        // Required empty public constructor
    }
    //========================================================

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

        mLivesTimer = ((MainActivity) getGameActivity()).getLivesTimer();

        // Init button
        GameButton btnSetting = (GameButton) view.findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(this);

        GameButton btnNext = (GameButton) view.findViewById(R.id.btn_page_next);
        btnNext.setOnClickListener(this);

        GameButton btnPrevious = (GameButton) view.findViewById(R.id.btn_page_previous);
        btnPrevious.setOnClickListener(this);

        GameButton btnShop = (GameButton) view.findViewById(R.id.btn_shop);
        btnShop.setOnClickListener(this);

        GameButton btnWheel = (GameButton) view.findViewById(R.id.btn_wheel);
        btnWheel.setOnClickListener(this);

        GameButton btnLives = (GameButton) view.findViewById(R.id.btn_lives);
        btnLives.setOnClickListener(this);

        GameButton btnCoin = (GameButton) view.findViewById(R.id.btn_coin);
        btnCoin.setOnClickListener(this);

        // Init level button and star
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
        mCurrentLevel = databaseHelper.getAllLevelStars().size() + 1;
        if (mCurrentLevel > TOTAL_LEVEL) {
            mCurrentLevel = TOTAL_LEVEL;
        }
        mCurrentPage = (int) Math.ceil(mCurrentLevel * 1.0d / LEVEL_PRE_PAGE);
        updatePage(mCurrentPage);
        loadCoin();

        // Show current level dialog
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLevelDialog(mCurrentLevel);
            }
        }, 800);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLivesTimer.startTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLivesTimer.stopTimer();
    }

    @Override
    public boolean onBackPressed() {
        getGameActivity().navigateToFragment(new MenuFragment());
        return true;
    }

    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_page_next) {
            if (mCurrentPage < MAX_PAGE) {
                mCurrentPage++;
                updatePage(mCurrentPage);
            }
        } else if (id == R.id.btn_page_previous) {
            if (mCurrentPage > 1) {
                mCurrentPage--;
                updatePage(mCurrentPage);
            }
        } else if (id == R.id.btn_shop) {
            showShopDialog();
        } else if (id == R.id.btn_wheel) {
            showWheelDialog();
        } else if (id == R.id.btn_coin) {
            showMoreCoinDialog();
        } else if (id == R.id.btn_lives) {
            if (mLivesTimer.getLivesCount() < LivesTimer.MAX_LIVES) {
                showMoreLivesDialog();
            }
        } else if (id == R.id.btn_setting) {
            showSettingDialog();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void updatePage(int page) {
        // Update page number text
        TextView currentPage = (TextView) getView().findViewById(R.id.txt_current_page);
        currentPage.setText(String.valueOf(page));

        TextView previousPage = (TextView) getView().findViewById(R.id.txt_previous_page);
        previousPage.setText(page == 1 ? "" : String.valueOf(page - 1));

        TextView nextPage = (TextView) getView().findViewById(R.id.txt_next_page);
        nextPage.setText(page == MAX_PAGE ? "" : String.valueOf(page + 1));

        // Update level button and star
        loadButton(page);
        loadStar(page);
    }

    private void loadButton(int page) {
        int increment = (page - 1) * 20;

        for (int i = 1; i <= LEVEL_PRE_PAGE; i++) {

            // Init level button
            String name = "btn_level_" + i;
            int id = getResources().getIdentifier(name, "id", getGameActivity().getPackageName());
            GameText txtLevel = (GameText) getView().findViewById(id);

            int level = i + increment;
            if (level <= mCurrentLevel) {
                txtLevel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Sounds.BUTTON_CLICK.play();
                        showLevelDialog(level);
                    }
                });
                txtLevel.setBackgroundResource(R.drawable.ui_btn_level);
                txtLevel.setEnabled(true);
            } else {
                txtLevel.setOnClickListener(null);
                txtLevel.setBackgroundResource(R.drawable.ui_btn_level_lock);
                txtLevel.setEnabled(false);
            }
            txtLevel.setText(String.valueOf(level));
            txtLevel.popUp(200, i * 30);
        }
    }

    private void loadStar(int page) {
        int increment = (page - 1) * 20;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
        List<Integer> stars = databaseHelper.getAllLevelStars();

        for (int i = 1; i <= LEVEL_PRE_PAGE; i++) {

            // Init level star
            String name = "image_level_star_" + i;
            int id = getResources().getIdentifier(name, "id", getActivity().getPackageName());
            GameImage imageStar = (GameImage) getView().findViewById(id);

            int level = i + increment;
            if (level < mCurrentLevel) {
                int star = stars.get(level - 1);
                switch (star) {
                    case 1:
                        imageStar.setBackgroundResource(R.drawable.ui_star_set_01);
                        break;
                    case 2:
                        imageStar.setBackgroundResource(R.drawable.ui_star_set_02);
                        break;
                    case 3:
                        imageStar.setBackgroundResource(R.drawable.ui_star_set_03);
                        break;
                }
                imageStar.setVisibility(View.VISIBLE);
            } else {
                imageStar.setVisibility(View.INVISIBLE);
            }
            imageStar.popUp(200, i * 40);
        }
    }

    private void loadCoin() {
        TextView textCoin = (TextView) getView().findViewById(R.id.txt_coin);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
        int coin = databaseHelper.getItemCount(Item.COIN);
        textCoin.setText(String.valueOf(coin));
    }

    private void showLevelDialog(int level) {
        // We load level data here before starting game
        Level.load(getContext(), level);
        LevelDialog levelDialog = new LevelDialog(getGameActivity()) {
            @Override
            public void startGame() {
                // Check is player lives enough
                if (mLivesTimer.getLivesCount() > 0) {
                    getGameActivity().navigateToFragment(new JuicyMatchFragment());
                } else {
                    showMoreLivesDialog();
                }
            }
        };
        getGameActivity().showDialog(levelDialog);
    }

    private void showShopDialog() {
        ShopDialog shopDialog = new ShopDialog(getGameActivity()) {
            @Override
            public void updateCoin() {
                loadCoin();
            }
        };
        getGameActivity().showDialog(shopDialog);
    }

    private void showWheelDialog() {
        WheelDialog wheelDialog = new WheelDialog(getGameActivity()) {
            @Override
            public void updateCoin() {
                loadCoin();
            }
        };
        getGameActivity().showDialog(wheelDialog);
    }

    private void showMoreCoinDialog() {
        MoreCoinDialog moreCoinDialog = new MoreCoinDialog(getGameActivity()) {
            @Override
            public void updateCoin() {
                loadCoin();
            }
        };
        getGameActivity().showDialog(moreCoinDialog);
    }

    private void showMoreLivesDialog() {
        MoreLivesDialog moreLivesDialog = new MoreLivesDialog(getGameActivity());
        getGameActivity().showDialog(moreLivesDialog);
    }

    private void showSettingDialog() {
        SettingDialog settingDialog = new SettingDialog(getGameActivity());
        getGameActivity().showDialog(settingDialog);
    }
    //========================================================

}
