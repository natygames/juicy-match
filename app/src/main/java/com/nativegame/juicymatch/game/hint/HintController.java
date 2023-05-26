package com.nativegame.juicymatch.game.hint;

import com.nativegame.juicymatch.algorithm.Match3Algorithm;
import com.nativegame.juicymatch.asset.Preferences;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.JuicyMatch;
import com.nativegame.juicymatch.game.effect.TextEffect;
import com.nativegame.juicymatch.game.hint.finder.HintFinderManager;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.juicymatch.level.TutorialType;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.engine.event.Event;
import com.nativegame.nattyengine.engine.event.EventListener;
import com.nativegame.nattyengine.entity.Entity;
import com.nativegame.nattyengine.entity.timer.Timer;
import com.nativegame.nattyengine.entity.timer.TimerEvent;

import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HintController extends Entity implements EventListener,
        Timer.TimerListener, TimerEvent.TimerEventListener {

    private static final long HINT_TIMEOUT = 4000;
    private static final long SHUFFLE_TIMEOUT = 2000;
    private static final long SOUND_TIMEOUT = 1000;

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
        mHintTimer = new Timer(engine, this, HINT_TIMEOUT);
        mShuffleTimer = new Timer(engine, this, SHUFFLE_TIMEOUT);
        mSoundTimer = new Timer(engine, this, SOUND_TIMEOUT);
        mSoundTimer.addTimerEvent(new TimerEvent(this, 300));
        mHintEnable = Preferences.PREF_SETTING.getBoolean(Preferences.KEY_HINT, true);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mSoundTimer.startTimer();
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
    public void onTimerComplete(Timer timer) {
        if (mHintTimer == timer) {
            showHintEffect();
        } else if (mShuffleTimer == timer) {
            startHint();
        } else if (mSoundTimer == timer) {
            Sounds.TILE_SHUFFLE.play();
        }
    }

    @Override
    public void onTimerEvent(long eventTime) {
        Sounds.TILE_SLIDE.play();
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
            mHintTimer.startTimer();
        }
    }

    private void stopHint() {
        mHintTimer.cancelTimer();
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
        mSoundTimer.startTimer();
        mShuffleTimer.startTimer();
        mShuffleText.activate(JuicyMatch.WORLD_WIDTH / 2f, JuicyMatch.WORLD_HEIGHT / 2f);
    }
    //========================================================

}
