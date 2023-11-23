package com.nativegame.juicymatch.game.algorithm.special.combine;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.asset.Colors;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.effect.combine.IceCreamCombineBeamEffectSystem;
import com.nativegame.juicymatch.game.effect.combine.IceCreamCombineEffectSystem;
import com.nativegame.juicymatch.game.effect.combine.IceCreamCombineRingEffectSystem;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.particle.ParticleSystem;
import com.nativegame.natyengine.entity.particle.SpriteParticleSystem;
import com.nativegame.natyengine.entity.shape.primitive.Plane;
import com.nativegame.natyengine.entity.timer.Timer;
import com.nativegame.natyengine.entity.timer.TimerEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class DoubleIceCreamCombineHandler extends BaseSpecialCombineHandler implements TimerEvent.TimerEventListener {

    private static final long START_DELAY = 2000;

    private final IceCreamCombineEffectSystem mIceCreamCombineEffectSystem;
    private final IceCreamCombineBeamEffectSystem mIceCreamCombineBeamEffectSystem;
    private final IceCreamCombineRingEffectSystem mIceCreamCombineRingEffectSystem;
    private final ParticleSystem mBlueLightBgParticleSystem;
    private final ParticleSystem mWhiteLightBgParticleSystem;
    private final ParticleSystem mRingLightParticleSystem;
    private final Plane mShadowBg;
    private final Timer mTimer;

    private final List<Tile> mPopTiles = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public DoubleIceCreamCombineHandler(Engine engine) {
        super(engine);
        mIceCreamCombineEffectSystem = new IceCreamCombineEffectSystem(engine, 1);
        mIceCreamCombineBeamEffectSystem = new IceCreamCombineBeamEffectSystem(engine, 10);
        mIceCreamCombineRingEffectSystem = new IceCreamCombineRingEffectSystem(engine, 3);
        mBlueLightBgParticleSystem = new SpriteParticleSystem(engine, Textures.LIGHT_BG_BLUE, 1)
                .setDuration(2000)
                .setScale(4, 2)
                .setLayer(GameLayer.EFFECT_LAYER);
        mWhiteLightBgParticleSystem = new SpriteParticleSystem(engine, Textures.LIGHT_BG, 1)
                .setDuration(2000)
                .setScale(2, 1)
                .setLayer(GameLayer.EFFECT_LAYER + 2);
        mRingLightParticleSystem = new SpriteParticleSystem(engine, Textures.FLASH_RING_BLUE, 1)
                .setDuration(2600)
                .setScale(0, 30, 2000)
                .setAlpha(255, 55, 2000)
                .setLayer(GameLayer.EFFECT_LAYER);
        mShadowBg = new Plane(engine, Colors.BLACK_80);
        mShadowBg.setLayer(GameLayer.EFFECT_LAYER);
        mTimer = new Timer(engine);
        mTimer.addTimerEvent(new TimerEvent(this, START_DELAY));
    }
    //========================================================

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
        if (tileA.getSpecialType() == SpecialType.ICE_CREAM
                && tileB.getSpecialType() == SpecialType.ICE_CREAM) {
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
        mIceCreamCombineBeamEffectSystem.activate(x, y);
        mIceCreamCombineRingEffectSystem.activate(x, y);
        mBlueLightBgParticleSystem.oneShot(x, y, 1);
        mWhiteLightBgParticleSystem.oneShot(x, y, 1);
        mRingLightParticleSystem.oneShot(x, y, 1);
        mIceCreamCombineEffectSystem.activate(x, y);
        tileA.hideTile();
        tileB.hideTile();
        // Important to hide these tiles here, since the Algorithm hasn't been added yet
    }

    @Override
    public void onTimerEvent(long eventTime) {
        mShadowBg.removeFromGame();
        mEngine.dispatchEvent(GameEvent.SHAKE_GAME);
        Sounds.ICE_CREAM_EXPLODE.play();
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
        mTimer.start();
    }
    //========================================================

}
