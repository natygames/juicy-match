package com.nativegame.juicymatch.game.hint;

import com.nativegame.juicymatch.algorithm.Match3Algorithm;
import com.nativegame.juicymatch.asset.Preferences;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.GameWorld;
import com.nativegame.juicymatch.game.effect.TextEffect;
import com.nativegame.juicymatch.game.hint.finder.HintFinderManager;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.juicymatch.level.TutorialType;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.Entity;
import com.nativegame.natyengine.entity.timer.Timer;
import com.nativegame.natyengine.entity.timer.TimerEvent;
import com.nativegame.natyengine.event.Event;
import com.nativegame.natyengine.event.EventListener;

import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HintController extends Entity implements EventListener, TimerEvent.TimerEventListener {

    private static final long HINT_TIMEOUT = 4000;
    private static final long SHUFFLE_TIMEOUT = 2000;
    private static final long SHUFFLE_SOUND_TIMEOUT = 1000;
    private static final long SLIDE_SOUND_TIMEOUT = 300;

    private final Tile[][] mTiles;
    private final int mTotalRow;
    private final int mTotalCol;
    private final HintFinderManager mHintFinder;
    private final HintModifier mHintModifier;
    private final TextEffect mShuffleText;
    private final Timer mHintTimer;
    private final Timer mShuffleTimer;
    private final Timer mSoundTimer;
    private final boolean mHintEnable;

    private List<Tile> mHintTiles;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public HintController(Engine engine, TileSystem tileSystem) {
        super(engine);
        mTiles = tileSystem.getChild();
        mTotalRow = tileSystem.getTotalRow();
        mTotalCol = tileSystem.getTotalColumn();
        mHintFinder = new HintFinderManager();
        mHintModifier = new HintModifier(engine);
        mShuffleText = new TextEffect(engine, Textures.TEXT_SHUFFLE);
        mHintTimer = new Timer(engine);
        mHintTimer.addTimerEvent(new TimerEvent(this, HINT_TIMEOUT));
        mShuffleTimer = new Timer(engine);
        mShuffleTimer.addTimerEvent(new TimerEvent(this, SHUFFLE_TIMEOUT));
        mSoundTimer = new Timer(engine);
        mSoundTimer.addTimerEvent(new TimerEvent(this, SHUFFLE_SOUND_TIMEOUT));
        mSoundTimer.addTimerEvent(new TimerEvent(this, SLIDE_SOUND_TIMEOUT));
        mHintEnable = Preferences.PREF_SETTING.getBoolean(Preferences.KEY_HINT, true);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mSoundTimer.start();
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case START_GAME:
                // Start hint if no tutorial
                if (Level.LEVEL_DATA.getTutorialType() == TutorialType.NONE) {
                    startHint();
                }
                break;
            case STOP_COMBO:
            case REMOVE_BOOSTER:
            case ADD_EXTRA_MOVES:
                startHint();
                break;
            case PLAYER_SWAP:
            case ADD_BOOSTER:
                stopHint();
                break;
            case GAME_WIN:
            case GAME_OVER:
                removeFromGame();
                break;
        }
    }

    @Override
    public void onTimerEvent(long eventTime) {
        if (eventTime == HINT_TIMEOUT) {
            showHintEffect();
        } else if (eventTime == SHUFFLE_TIMEOUT) {
            startHint();
        } else if (eventTime == SHUFFLE_SOUND_TIMEOUT) {
            Sounds.TILE_SHUFFLE.play();
        } else if (eventTime == SLIDE_SOUND_TIMEOUT) {
            Sounds.TILE_SLIDE.play();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void startHint() {
        mHintTiles = mHintFinder.findHint(mTiles, mTotalRow, mTotalCol);
        if (mHintTiles == null) {
            // Shuffle the tile if hint not found
            shuffleTile();
            return;
        }
        if (mHintEnable) {
            // Start timer and show effect if hint found
            mHintTimer.start();
        }
    }

    private void stopHint() {
        mHintTimer.stop();
        removeHintEffect();
    }

    private void showHintEffect() {
        mHintModifier.activate(mHintTiles);
    }

    private void removeHintEffect() {
        if (mHintModifier.isRunning()) {
            mHintModifier.removeFromGame();
        }
    }

    private void shuffleTile() {
        Match3Algorithm.shuffleTile(mTiles, mTotalRow, mTotalCol);
        mSoundTimer.start();
        mShuffleTimer.start();
        mShuffleText.activate(GameWorld.WORLD_WIDTH / 2f, GameWorld.WORLD_HEIGHT / 2f);
    }
    //========================================================

}
