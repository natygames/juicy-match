package com.nativegame.juicymatch.game.algorithm;

import android.graphics.Color;

import com.nativegame.juicymatch.algorithm.Match3Algorithm;
import com.nativegame.juicymatch.asset.Fonts;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.GameWorld;
import com.nativegame.juicymatch.game.effect.TextEffect;
import com.nativegame.juicymatch.game.effect.flash.TransformFlashEffectSystem;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.text.Text;
import com.nativegame.natyengine.input.touch.TouchEvent;
import com.nativegame.natyengine.input.touch.TouchEventListener;
import com.nativegame.natyengine.util.RandomUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class BonusTimeAlgorithm extends BaseAlgorithm implements TouchEventListener {

    private static final int MAX_TRANSFORM_NUM = 20;

    private final TransformFlashEffectSystem mTransformFlashEffect;
    private final TextEffect mBonusText;
    private final SkipText mSkipText;
    private final SpecialType[] mBonusSpecialTypes;

    private AlgorithmState mState;
    private int mRemainingMove;
    private long mTotalTime;
    private long mPauseTime = 300;
    private long mBonusIntervalTime = 200;
    private boolean mIsAddBonus = false;
    private boolean mIsSkipBonus = false;

    private enum AlgorithmState {
        CHECK_MATCH,
        MOVE_TILE,
        PAUSE_TILE,
        ADD_BONUS
    }

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public BonusTimeAlgorithm(Engine engine, TileSystem tileSystem) {
        super(engine, tileSystem);
        mTransformFlashEffect = new TransformFlashEffectSystem(engine, MAX_TRANSFORM_NUM);
        mBonusText = new TextEffect(engine, Textures.TEXT_BONUS);
        mSkipText = new SkipText(engine, 1500, 600, "Tap to skip");
        mBonusSpecialTypes = new SpecialType[]{
                SpecialType.ROW_STRIPED,
                SpecialType.COLUMN_STRIPED,
                SpecialType.EXPLOSIVE};
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mSkipText.activate(GameWorld.WORLD_WIDTH / 2f, GameWorld.WORLD_HEIGHT / 2f + 1650);
        mBonusText.activate(GameWorld.WORLD_WIDTH / 2f, GameWorld.WORLD_HEIGHT / 2f);
    }

    @Override
    public void onRemove() {
        if (mSkipText.isRunning()) {
            mSkipText.removeFromGame();
        }
        if (mBonusText.isRunning()) {
            mBonusText.removeFromGame();
        }
    }

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
                mTotalTime += elapsedMillis;
                if (mTotalTime >= mPauseTime) {
                    mState = AlgorithmState.MOVE_TILE;
                    mTotalTime = 0;
                }
                break;
            case ADD_BONUS:
                mTotalTime += elapsedMillis;
                if (mTotalTime >= mBonusIntervalTime) {
                    // Update remaining move
                    if (mRemainingMove == 0) {
                        mState = AlgorithmState.CHECK_MATCH;
                    } else {
                        addBonus();
                        dispatchEvent(GameEvent.ADD_BONUS);
                        mRemainingMove--;
                    }
                    mTotalTime = 0;
                }
                break;
        }
    }

    @Override
    public void initAlgorithm() {
    }

    @Override
    public void startAlgorithm() {
        Level.LEVEL_DATA.setFruitCount(5);
        mRemainingMove = Level.LEVEL_DATA.getMove();
        mState = AlgorithmState.CHECK_MATCH;
        addToGame();
        mTotalTime = 0;
    }

    @Override
    public void removeAlgorithm() {
    }

    @Override
    public void onTouchEvent(int type, float touchX, float touchY) {
        if (mIsSkipBonus) {
            return;
        }
        // Skip the bonus time if player touch screen
        if (type == TouchEvent.TOUCH_DOWN) {
            skipBonus();
            mIsSkipBonus = true;
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void checkMatch() {
        Match3Algorithm.findMatchTile(mTiles, mTotalRow, mTotalCol);
        popSpecialTile();
        // Check is any matches found
        if (!Match3Algorithm.isMatch(mTiles, mTotalRow, mTotalCol)) {
            // Check is bonus added yet
            if (!mIsAddBonus) {
                // Convert remaining moves to bonus if not
                mState = AlgorithmState.ADD_BONUS;
                mIsAddBonus = true;
            } else {
                // Otherwise, stop the bonus time and notify GameController
                dispatchEvent(GameEvent.BONUS_TIME_END);
                removeFromGame();
            }
        } else {
            // Run the algorithm if found
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
    }

    private void popSpecialTile() {
        for (int i = 0; i < mTotalRow; i++) {
            for (int j = 0; j < mTotalCol; j++) {
                Tile t = mTiles[i][j];
                // Pop one special tile at a time
                if (t.getSpecialType() != SpecialType.NONE) {
                    t.popTile();
                    return;
                }
            }
        }
    }

    private void addBonus() {
        Tile targetTile;
        do {
            // Chose a random tile
            int row = RandomUtils.nextInt(mTotalRow);
            int col = RandomUtils.nextInt(mTotalCol);
            targetTile = mTiles[row][col];
        } while (targetTile.getTileType() == FruitType.NONE
                || targetTile.getSpecialType() != SpecialType.NONE);

        // Update special type and add transform effect
        targetTile.setSpecialType(mBonusSpecialTypes[RandomUtils.nextInt(mBonusSpecialTypes.length)]);
        mTransformFlashEffect.activate(targetTile.getCenterX(), targetTile.getCenterY());
        Sounds.ADD_BONUS.play();
    }

    private void skipBonus() {
        mPauseTime = 0;
        mBonusIntervalTime = 50;
        dispatchEvent(GameEvent.BONUS_TIME_SKIP);
        mSkipText.removeFromGame();
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    private static class SkipText extends Text {

        //--------------------------------------------------------
        // Constructors
        //--------------------------------------------------------
        public SkipText(Engine engine, int width, int height, String text) {
            super(engine, width, height, text);
            mPaint.setColor(Color.WHITE);
            setTextSize(300);
            setTextTypeface(Fonts.BALOO);
            setLayer(GameLayer.EFFECT_LAYER);
        }
        //========================================================

        //--------------------------------------------------------
        // Methods
        //--------------------------------------------------------
        public void activate(float x, float y) {
            setCenterX(x);
            setCenterY(y);
            addToGame();
        }
        //========================================================

    }
    //========================================================

}
