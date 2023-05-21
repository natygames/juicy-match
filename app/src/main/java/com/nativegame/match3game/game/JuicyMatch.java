package com.nativegame.match3game.game;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.game.algorithm.Algorithm;
import com.nativegame.match3game.game.algorithm.BonusTimeAlgorithm;
import com.nativegame.match3game.game.algorithm.RegularTimeAlgorithm;
import com.nativegame.match3game.game.algorithm.layer.LayerHandlerManager;
import com.nativegame.match3game.game.algorithm.target.TargetHandlerManager;
import com.nativegame.match3game.game.counter.BoosterCounter;
import com.nativegame.match3game.game.counter.ComboCounter;
import com.nativegame.match3game.game.counter.MoveCounter;
import com.nativegame.match3game.game.counter.ScoreCounter;
import com.nativegame.match3game.game.counter.StarCounter;
import com.nativegame.match3game.game.counter.TargetCounter;
import com.nativegame.match3game.game.hint.HintController;
import com.nativegame.match3game.game.layer.grid.GridSystem;
import com.nativegame.match3game.game.layer.tile.TileSystem;
import com.nativegame.match3game.game.swap.SwapController;
import com.nativegame.match3game.ui.dialog.PauseDialog;
import com.nativegame.nattyengine.Game;
import com.nativegame.nattyengine.engine.camera.Camera;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.ui.GameView;

/**
 * Created by Oscar Liang on 2022/02/23
 */


public class JuicyMatch extends Game {

    public static final int WORLD_WIDTH = 2760;
    public static final int WORLD_HEIGHT = 2760;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public JuicyMatch(GameActivity activity, GameView gameView) {
        super(activity, gameView);

        // Init camera
        int cameraSize;
        if (gameView.getHeight() / gameView.getWidth() < 2) {
            cameraSize = gameView.getHeight() / 2;   // We use half screen height if screen too wide
        } else {
            cameraSize = gameView.getWidth();   // Otherwise, we just use screen width
        }
        mEngine.setCamera(new Camera(gameView, cameraSize, cameraSize, WORLD_WIDTH, WORLD_HEIGHT));

        // Init counter
        new ComboCounter(mEngine).addToGame();
        new ScoreCounter(mActivity, mEngine).addToGame();
        new StarCounter(mActivity, mEngine).addToGame();
        new MoveCounter(mActivity, mEngine).addToGame();
        new TargetCounter(mActivity, mEngine).addToGame();

        // Init layer
        new GridSystem(mEngine);
        TileSystem tileSystem = new TileSystem(mEngine);

        // Init Algorithm
        Algorithm regularTimeAlgorithm = new RegularTimeAlgorithm(mEngine, tileSystem,
                new LayerHandlerManager(mEngine), new TargetHandlerManager());
        Algorithm bonusTimeAlgorithm = new BonusTimeAlgorithm(mEngine, tileSystem);

        // Init controller
        new SwapController(mEngine, tileSystem).addToGame();
        new HintController(mEngine, tileSystem).addToGame();
        new BoosterCounter(mActivity, mEngine, tileSystem).addToGame();
        new GameController(mActivity, mEngine, regularTimeAlgorithm, bonusTimeAlgorithm).addToGame();
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onPause() {
        showPauseDialog();
        mActivity.getSoundManager().pause();
    }

    @Override
    protected void onResume() {
        mActivity.getSoundManager().resume();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void showPauseDialog() {
        PauseDialog dialog = new PauseDialog(mActivity) {
            @Override
            public void resumeGame() {
                resume();
            }

            @Override
            public void quitGame() {
                // Reduce one live
                ((MainActivity) mActivity).getLivesTimer().reduceLive();
                mActivity.navigateBack();
            }
        };
        mActivity.showDialog(dialog);
    }
    //========================================================

}
