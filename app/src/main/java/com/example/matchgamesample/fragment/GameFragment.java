package com.example.matchgamesample.fragment;

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
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.GameManager;
import com.example.matchgamesample.game.Tile;
import com.example.matchgamesample.game.TileMatrix;
import com.example.matchgamesample.input.BasicInputController;
import com.example.matchgamesample.level.Level;
import com.example.matchgamesample.level.LevelManager;

public class GameFragment extends BaseFragment {
    private static final String LEVEL = "level";
    private int level;

    private MainActivity mActivity;

    //Device size
    private int mScreen_width;
    private int mScreen_height;
    private int tileSize = 0;

    //Level parameter
    private Level mLevel;
    private int[] mFruit;
    private LevelManager mLevelManager;

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
        tileSize = (int) ((mScreen_width - 20) / 9);

        //Level parameter
        mLevelManager = new LevelManager(mActivity, level);
        mLevel = mLevelManager.getLevel();
        int row = mLevel.row;
        int column = mLevel.column;

        //Initializing fruit layer
        GridLayout grid_board = (GridLayout) getView().findViewById(R.id.grid_board);
        GridLayout fruit_board = (GridLayout) getView().findViewById(R.id.fruit_board);
        //Initializing effect layer
        RelativeLayout effect_board = (RelativeLayout) getView().findViewById(R.id.effect_board);
        effect_board.getLayoutParams().width = tileSize * column;
        effect_board.getLayoutParams().height = tileSize * row;
        RelativeLayout guide_board = (RelativeLayout) getView().findViewById(R.id.guide_board);

        mGameEngine = new GameEngine(mActivity, tileSize);

        //Initializing array
        Tile[][] tileArray = new Tile[row][column];
        // Implement tiles
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                tileArray[i][j] = new Tile(mGameEngine);
                fruit_board.addView(tileArray[i][j].mImage);
            }
        }

        GameManager mGameManager = new GameManager(mActivity, column, row, tileSize);
        mGameManager.createGridBoard(grid_board, mLevelManager.getBoardChar());
        mGameManager.createFruitBoard(fruit_board, mLevelManager.getFruitChar(), tileArray);


        mGameEngine.setInputController(new BasicInputController());
        // Add all the game object here
        mGameEngine.addGameObject(new TileMatrix(tileArray, row, column, tileSize));

        mGameEngine.startGame();

    }

    public LevelManager getLevelManager() {
        return mLevelManager;
    }

    @Override
    public boolean onBackPressed() {
        if (mGameEngine.isRunning()) {
            mGameEngine.stopGame();
            return true;
        }
        return false;
    }

}