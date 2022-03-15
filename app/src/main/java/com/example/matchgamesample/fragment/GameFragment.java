package com.example.matchgamesample.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.matchgamesample.R;
import com.example.matchgamesample.game.Hint;
import com.example.matchgamesample.game.counter.MoveCounter;
import com.example.matchgamesample.game.counter.TargetCounter;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.GameBoard;
import com.example.matchgamesample.game.algorithm.GameAlgorithm;
import com.example.matchgamesample.game.counter.ScoreCounter;
import com.example.matchgamesample.game.Tile;
import com.example.matchgamesample.game.GameController;
import com.example.matchgamesample.input.BasicInputController;
import com.example.matchgamesample.level.Level;
import com.example.matchgamesample.level.LevelType;

public class GameFragment extends BaseFragment {
    private static final String LEVEL = "level";
    private int level;
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
        Level mLevel = getMainActivity().getLevelManager().getLevel(level);
        int row = mLevel.mRow;
        int column = mLevel.mColumn;

        // Init engine
        mGameEngine = new GameEngine(getMainActivity(), mLevel, tileSize);
        GameAlgorithm gameAlgorithm = new GameAlgorithm(mGameEngine);

        // Init tile
        Tile[][] tileArray = new Tile[row][column];

        // Init game board
        GameBoard mGameBoard = new GameBoard(mGameEngine);
        mGameBoard.createGridBoard(getView().findViewById(R.id.grid_board));
        mGameBoard.createFruitBoard(getView().findViewById(R.id.fruit_board), tileArray);
        if (mLevel.mLevelType == LevelType.LEVEL_TYPE_ICE) {
            ImageView[][] iceArray = new ImageView[row][column];
            ImageView[][] iceArray2 = new ImageView[row][column];
            mGameBoard.createIceBoard(getView().findViewById(R.id.ice_board), iceArray,
                    getView().findViewById(R.id.ice_board2), iceArray2, tileArray);
            gameAlgorithm.setIceArray(iceArray, iceArray2);
        }
        if (mLevel.advance != null) {
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
        mGameEngine.addGameObject(new ScoreCounter(getView()));
        mGameEngine.addGameObject(new MoveCounter(getView(), mGameEngine));
        mGameEngine.addGameObject(new TargetCounter(getView(), mGameEngine));
        mGameEngine.addGameObject(new Hint(mGameEngine, tileArray));

        mGameEngine.startGame();
    }

    @Override
    public boolean onBackPressed() {
        if (mGameEngine.isRunning() && !mGameEngine.isPaused()) {
            mGameEngine.pauseGame();
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGameEngine.isRunning() && !mGameEngine.isPaused()) {
            mGameEngine.pauseGame();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGameEngine.isRunning() && mGameEngine.isPaused()) {
            mGameEngine.resumeGame();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGameEngine.stopGame();
    }

}