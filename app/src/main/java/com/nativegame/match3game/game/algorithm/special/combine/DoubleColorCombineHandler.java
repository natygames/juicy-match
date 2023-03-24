package com.nativegame.match3game.game.algorithm.special.combine;

import com.nativegame.match3game.algorithm.TileState;
import com.nativegame.match3game.asset.Colors;
import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.game.effect.combine.ColorCombineBeamEffectSystem;
import com.nativegame.match3game.game.effect.combine.ColorCombineEffectSystem;
import com.nativegame.match3game.game.effect.combine.ColorCombineRingEffectSystem;
import com.nativegame.match3game.game.layer.Layer;
import com.nativegame.match3game.game.layer.tile.SpecialType;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.particles.ParticleSystem;
import com.nativegame.nattyengine.entity.primitive.Color;
import com.nativegame.nattyengine.entity.timer.Timer;

import java.util.ArrayList;
import java.util.List;

public class DoubleColorCombineHandler extends BaseSpecialCombineHandler implements Timer.TimerListener {

    private static final long START_DELAY = 2000;

    private final ColorCombineEffectSystem mColorCombineEffectSystem;
    private final ColorCombineBeamEffectSystem mColorCombineBeamEffectSystem;
    private final ColorCombineRingEffectSystem mColorCombineRingEffectSystem;
    private final ParticleSystem mBlueLightBgParticleSystem;
    private final ParticleSystem mWhiteLightBgParticleSystem;
    private final ParticleSystem mRingLightParticleSystem;
    private final Color mShadowBg;
    private final Timer mTimer;

    private final List<Tile> mPopTiles = new ArrayList<>();

    public DoubleColorCombineHandler(Engine engine) {
        super(engine);
        mColorCombineEffectSystem = new ColorCombineEffectSystem(engine, 1);
        mColorCombineBeamEffectSystem = new ColorCombineBeamEffectSystem(engine, 10);
        mColorCombineRingEffectSystem = new ColorCombineRingEffectSystem(engine, 3);
        mBlueLightBgParticleSystem = new ParticleSystem(engine, Textures.BLUE_LIGHT_BG, 1)
                .setDuration(2000)
                .setScale(4, 2)
                .setLayer(Layer.EFFECT_LAYER);
        mWhiteLightBgParticleSystem = new ParticleSystem(engine, Textures.LIGHT_BG, 1)
                .setDuration(2000)
                .setScale(2, 1)
                .setLayer(Layer.EFFECT_LAYER + 2);
        mRingLightParticleSystem = new ParticleSystem(engine, Textures.BLUE_RING_FLASH, 1)
                .setDuration(2600)
                .setScale(0, 30, 2000)
                .setAlpha(255, 55, 2000)
                .setLayer(Layer.EFFECT_LAYER);
        mShadowBg = new Color(engine, Colors.BLACK_80);
        mShadowBg.setLayer(Layer.EFFECT_LAYER);
        mTimer = new Timer(engine, this, START_DELAY);
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public long getStartDelay() {
        return START_DELAY;
    }

    @Override
    public boolean checkSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        // Check are both tiles explosion special tile
        if (tileA.getSpecialType() == SpecialType.COLOR_SPECIAL_TILE
                && tileB.getSpecialType() == SpecialType.COLOR_SPECIAL_TILE) {
            // We make sure the origin special tiles not being detected
            tileA.setTileState(TileState.MATCH);
            tileB.setTileState(TileState.MATCH);
            handleSpecialCombine(tiles, tileA, tileB, row, col);
            return true;
        }

        return false;
    }

    @Override
    protected void playTileEffect(Tile tileA, Tile tileB) {
        mShadowBg.addToGame();
        super.playTileEffect(tileA, tileB);
        float x = (tileA.getCenterX() + tileB.getCenterX()) / 2;
        float y = (tileA.getCenterY() + tileB.getCenterY()) / 2;
        mColorCombineBeamEffectSystem.activate(x, y);
        mColorCombineRingEffectSystem.activate(x, y);
        mBlueLightBgParticleSystem.oneShot(x, y, 1);
        mWhiteLightBgParticleSystem.oneShot(x, y, 1);
        mRingLightParticleSystem.oneShot(x, y, 1);
        mColorCombineEffectSystem.activate(x, y);
        tileA.hideTile();
        tileB.hideTile();
        // Important to hide these tiles here, since the Algorithm hasn't been added yet
    }

    @Override
    public void onTimerComplete(Timer timer) {
        mShadowBg.removeFromGame();
        mEngine.dispatchEvent(GameEvent.SHAKE_GAME);
        Sounds.COLOR_SPECIAL_TILE_EXPLODE.play();
        int size = mPopTiles.size();
        for (int i = 0; i < size; i++) {
            Tile tile = mPopTiles.get(i);
            tile.popTile();
        }
        mPopTiles.clear();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void handleSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        // Pop all the tiles
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Tile t = tiles[i][j];
                // We make sure not pop multiple times
                if (t.getTileState() == TileState.IDLE) {
                    mPopTiles.add(t);
                }
            }
        }

        playTileEffect(tileA, tileB);
        mTimer.startTimer();
    }
    //========================================================

}
