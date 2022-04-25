package com.example.matchgamesample.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matchgamesample.R;
import com.example.matchgamesample.Utils;
import com.example.matchgamesample.dialog.PauseDialog;
import com.example.matchgamesample.effect.sound.SoundEvent;
import com.example.matchgamesample.game.tile.Hint;
import com.example.matchgamesample.game.counter.FPSCounter;
import com.example.matchgamesample.game.counter.MoveCounter;
import com.example.matchgamesample.game.counter.ScoreBarCounter;
import com.example.matchgamesample.game.counter.TargetCounter;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.GameBoard;
import com.example.matchgamesample.game.algorithm.GameAlgorithm;
import com.example.matchgamesample.game.counter.ScoreCounter;
import com.example.matchgamesample.game.tile.Tile;
import com.example.matchgamesample.game.GameController;
import com.example.matchgamesample.game.input.BasicInputController;
import com.example.matchgamesample.level.Level;
import com.example.matchgamesample.level.LevelType;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class GameFragment extends BaseFragment implements PauseDialog.PauseDialogListener {
    private static final String LEVEL = "LEVEL";
    private int mLevel;
    private GameEngine mGameEngine;
    private AnimationDrawable mScoreBarAnimation;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(int param) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt(LEVEL, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLevel = getArguments().getInt(LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init level title
        TextView textView = (TextView) getView().findViewById(R.id.txt_level);
        textView.setText("LV. " + String.valueOf(mLevel));

        // Init pause button
        ImageButton imageButton = (ImageButton) getView().findViewById(R.id.btn_pause);
        Utils.createButtonEffect(imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                pauseGameAndShowPauseDialog();
            }
        });

        // We don't need bgm in game
        getMainActivity().getSoundManager().pauseBgMusic();

        startGame();
    }

    private void startGame() {
        // Init device size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getMainActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screen_width = displayMetrics.widthPixels;
        int screen_height = displayMetrics.heightPixels;
        int tileSize = (int) ((screen_width - 20) / 9);

        // Init Level
        Level level = getMainActivity().getLevelManager().getLevel(mLevel);
        int row = level.mRow;
        int column = level.mColumn;

        // Init engine
        mGameEngine = new GameEngine(getMainActivity(), level, tileSize);
        GameAlgorithm gameAlgorithm = new GameAlgorithm(mGameEngine);

        // Init tile
        Tile[][] tileArray = new Tile[row][column];

        // Init game board
        GameBoard mGameBoard = new GameBoard(mGameEngine);
        mGameBoard.createGridBoard(getView().findViewById(R.id.grid_board));
        mGameBoard.createFruitBoard(getView().findViewById(R.id.fruit_board), tileArray);

        // Optional init
        if (level.mLevelType == LevelType.LEVEL_TYPE_ICE) {
            ImageView[][] iceArray = new ImageView[row][column];
            ImageView[][] iceArray2 = new ImageView[row][column];
            mGameBoard.createIceBoard(getView().findViewById(R.id.ice_board), iceArray,
                    getView().findViewById(R.id.ice_board2), iceArray2, tileArray);
            gameAlgorithm.setIceArray(iceArray, iceArray2);
        }
        if (level.advance != null) {
            ImageView[][] advanceArray = new ImageView[row + 2][column];
            mGameBoard.createAdvanceBoard(getView().findViewById(R.id.advance_board),
                    advanceArray, tileArray);
            gameAlgorithm.setAdvanceArray(advanceArray);
        }

        // Add all the object to engine
        mGameEngine.setInputController(new BasicInputController(getView(), mGameEngine));
        GameController gameController = new GameController(mGameEngine, tileArray);
        gameController.setMyAlgorithm(gameAlgorithm);
        mGameEngine.addGameObject(gameController);
        mGameEngine.addGameObject(new FPSCounter(mGameEngine));
        mGameEngine.addGameObject(new ScoreCounter(mGameEngine));
        mGameEngine.addGameObject(new MoveCounter(mGameEngine));
        mGameEngine.addGameObject(new TargetCounter(mGameEngine));
        mGameEngine.addGameObject(new ScoreBarCounter(mGameEngine));
        mGameEngine.addGameObject(new Hint(mGameEngine, tileArray));

        mGameEngine.startGame();

        // We start score bar animation here
        ClipDrawable clipDrawable = (ClipDrawable) getView().findViewById(R.id.score_bar).getBackground();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mScoreBarAnimation = (AnimationDrawable) clipDrawable.getDrawable();
        }
        getMainActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mScoreBarAnimation != null)
                    mScoreBarAnimation.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();

            if (mScoreBarAnimation != null)
                mScoreBarAnimation.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (mGameEngine.isRunning() && !mGameEngine.isPaused()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return super.onBackPressed();
    }

    private void pauseGameAndShowPauseDialog() {
        if (mGameEngine.isPaused()) {
            return;
        }
        mGameEngine.pauseGame();
        PauseDialog dialog = new PauseDialog(getMainActivity());
        dialog.setListener(this);
        showDialog(dialog);
    }

    @Override
    public void resumeGame() {
        mGameEngine.resumeGame();
        if (mScoreBarAnimation != null)
            mScoreBarAnimation.start();
    }

    @Override
    public void quitGame() {
        mGameEngine.stopGame();
        getMainActivity().navigateBack();
    }

}
