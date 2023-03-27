package com.nativegame.match3game.game.algorithm;

import com.nativegame.match3game.algorithm.Match3Algorithm;
import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.game.algorithm.layer.LayerHandlerManager;
import com.nativegame.match3game.game.algorithm.target.TargetHandlerManager;
import com.nativegame.match3game.game.layer.tile.TileSystem;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class RegularTimeAlgorithm extends BaseAlgorithm {

    private static final int TIME_TO_PAUSE = 300;

    private final LayerHandlerManager mLayerHandlerManager;
    private final TargetHandlerManager mTargetHandlerManager;

    private AlgorithmState mState;
    private long mTotalTime;

    private enum AlgorithmState {
        CHECK_MATCH,
        MOVE_TILE,
        PAUSE_TILE
    }

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public RegularTimeAlgorithm(Engine engine, TileSystem tileSystem, LayerHandlerManager layerHandlerManager,
                                TargetHandlerManager targetHandlerManager) {
        super(engine, tileSystem);
        mLayerHandlerManager = layerHandlerManager;
        mTargetHandlerManager = targetHandlerManager;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        switch (mState) {
            case CHECK_MATCH:
                checkMatch();
                break;
            case MOVE_TILE:
                moveTile(elapsedMillis);
                break;
            case PAUSE_TILE:
                // We pause the tiles for a while before moving
                mTotalTime += elapsedMillis;
                if (mTotalTime >= TIME_TO_PAUSE) {
                    mState = AlgorithmState.MOVE_TILE;
                    mTotalTime = 0;
                }
                break;
        }
    }

    @Override
    public void initAlgorithm() {
        mLayerHandlerManager.initLayers(mTiles, mTotalRow, mTotalCol);
    }

    @Override
    public void startAlgorithm() {
        mState = AlgorithmState.CHECK_MATCH;
        addToGame();
        mTotalTime = 0;
    }

    @Override
    public void removeAlgorithm() {
        mLayerHandlerManager.removeLayers(mTiles, mTotalRow, mTotalCol);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void checkMatch() {
        Match3Algorithm.findMatchTile(mTiles, mTotalRow, mTotalCol);
        // Update target and layer
        mTargetHandlerManager.checkTargets(mTiles, mTotalRow, mTotalCol);
        mLayerHandlerManager.updateLayers(mTargetHandlerManager, mTiles, mTotalRow, mTotalCol);
        if (mTargetHandlerManager.isTargetChange()) {
            dispatchEvent(GameEvent.PLAYER_COLLECT);
        }
        // Check is any matches found
        if (!Match3Algorithm.isMatch(mTiles, mTotalRow, mTotalCol)) {
            // Dispatch next event if no more matches
            if (mTargetHandlerManager.isTargetComplete()) {
                // Player collect all the target
                dispatchEvent(GameEvent.GAME_WIN);
            } else if (Level.LEVEL_DATA.getMove() == 0) {
                // Player run out of moves
                dispatchEvent(GameEvent.GAME_OVER);
            } else {
                // Continue the game
                dispatchEvent(GameEvent.STOP_COMBO);
            }
            removeFromGame();
        } else {
            // Add one combo and run the algorithm if found
            dispatchEvent(GameEvent.ADD_COMBO);
            mSpecialTileFinder.findSpecialTile(mTiles, mTotalRow, mTotalCol);
            Match3Algorithm.playTileEffect(mTiles, mTotalRow, mTotalCol);
            Match3Algorithm.checkUnreachableTile(mTiles, mTotalRow, mTotalCol);
            Match3Algorithm.resetMatchTile(mTiles, mTotalRow, mTotalCol);
            mState = AlgorithmState.PAUSE_TILE;
        }
    }

    private void moveTile(long elapsedMillis) {
        Match3Algorithm.moveTile(mTiles, mTotalRow, mTotalCol, elapsedMillis);
        // Update waiting tile state when moving
        if (Match3Algorithm.isWaiting(mTiles, mTotalRow, mTotalCol)) {
            Match3Algorithm.findUnreachableTile(mTiles, mTotalRow, mTotalCol);
            Match3Algorithm.checkWaitingTile(mTiles, mTotalRow, mTotalCol);
            Match3Algorithm.resetMatchTile(mTiles, mTotalRow, mTotalCol);
            // Important to not check isMoving(), so the tile will move continuously
        }
        // Check match if tiles stop moving
        if (!Match3Algorithm.isMoving(mTiles, mTotalRow, mTotalCol)) {
            Sounds.TILE_BOUNCE.play();
            mState = AlgorithmState.CHECK_MATCH;
        }
        // Use this if run into bug
//        if (!Match3Algorithm.isMoving(mTiles, mTotalRow, mTotalCol)
//                && !Match3Algorithm.isWaiting(mTiles, mTotalRow, mTotalCol)) {
//            mState = AlgorithmState.CHECK_MATCH;
//        }
    }
    //========================================================

}
