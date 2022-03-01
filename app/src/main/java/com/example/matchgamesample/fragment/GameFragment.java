package com.example.matchgamesample.fragment;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.example.matchgamesample.MainActivity;
import com.example.matchgamesample.R;
import com.example.matchgamesample.effect.AnimationManager;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.Game;
import com.example.matchgamesample.game.Tile;
import com.example.matchgamesample.game.GameController;
import com.example.matchgamesample.input.BasicInputController;
import com.example.matchgamesample.level.Level;

public class GameFragment extends BaseFragment {
    private static final String LEVEL = "level";
    private int level;

    private MainActivity mActivity;

    //Device size
    private int mScreen_width;
    private int mScreen_height;

    private GameEngine mGameEngine;

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
            level = getArguments().getInt(LEVEL);
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

        mActivity = getMainActivity();

        //Initializing device parameters
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreen_width = displayMetrics.widthPixels;
        mScreen_height = displayMetrics.heightPixels;
        int tileSize = (int) ((mScreen_width - 20) / 9);

        //Level parameter
        Level mLevel = mActivity.getLevelManager().getLevel(level);
        mActivity.getLevelManager().init();
        int row = mLevel.row;
        int column = mLevel.column;

        //Initializing game board layer
        GridLayout grid_board = (GridLayout) getView().findViewById(R.id.grid_board);
        GridLayout fruit_board = (GridLayout) getView().findViewById(R.id.fruit_board);
        //Initializing effect layer
        RelativeLayout effect_board = (RelativeLayout) getView().findViewById(R.id.effect_board);
        effect_board.getLayoutParams().width = tileSize * column;
        effect_board.getLayoutParams().height = tileSize * row;
        RelativeLayout guide_board = (RelativeLayout) getView().findViewById(R.id.guide_board);

        mGameEngine = new GameEngine(mActivity, mLevel, tileSize);

        //Initializing array
        Tile[][] tileArray = new Tile[row][column];
        // Implement tiles
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                tileArray[i][j] = new Tile(mGameEngine);
                fruit_board.addView(tileArray[i][j].mImage);
            }
        }

        // Render game
        Game mGame = new Game(mActivity, mLevel, tileSize);
        mGame.createGridBoard(grid_board);
        mGame.createFruitBoard(fruit_board, tileArray);

        mGameEngine.setInputController(new BasicInputController(view, mGameEngine));
        // Add all the game object here
        GameController gameController = new GameController(mGameEngine, tileArray);
        gameController.setAnimationManager(new AnimationManager(mGameEngine));

        mGameEngine.addGameObject(gameController);

        mGameEngine.startGame();

    }

    @Override
    public boolean onBackPressed() {
        if (mGameEngine.isRunning() && !mGameEngine.isPaused()){
            mGameEngine.pauseGame();
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGameEngine.isRunning() && !mGameEngine.isPaused()){
            mGameEngine.pauseGame();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGameEngine.isRunning() && mGameEngine.isPaused()){
            mGameEngine.resumeGame();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGameEngine.stopGame();
    }

}