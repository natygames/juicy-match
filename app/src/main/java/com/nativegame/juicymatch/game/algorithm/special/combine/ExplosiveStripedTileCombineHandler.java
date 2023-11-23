package com.nativegame.juicymatch.game.algorithm.special.combine;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.effect.combine.ExplosiveStripedTileCombineEffect;
import com.nativegame.juicymatch.game.effect.combine.ExplosiveStripedTileCombineRingEffectSystem;
import com.nativegame.juicymatch.game.effect.flash.ColumnFlashEffectSystem;
import com.nativegame.juicymatch.game.effect.flash.RowFlashEffectSystem;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.particle.ParticleSystem;
import com.nativegame.natyengine.entity.particle.SpriteParticleSystem;
import com.nativegame.natyengine.entity.timer.Timer;
import com.nativegame.natyengine.entity.timer.TimerEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosiveStripedTileCombineHandler extends BaseSpecialCombineHandler implements TimerEvent.TimerEventListener {

    private static final long START_DELAY = 600;

    private final RowFlashEffectSystem mRowFlashEffect;
    private final ColumnFlashEffectSystem mColumnFlashEffect;
    private final ExplosiveStripedTileCombineEffect mExplosiveStripedTileCombineEffect;
    private final ExplosiveStripedTileCombineRingEffectSystem mExplosionRowColumnCombineRingEffect;
    private final ParticleSystem mRingLightParticleSystem;
    private final ParticleSystem mLightParticleSystem;
    private final Timer mTimer;

    private final List<Tile> mTilesToRemove = new ArrayList<>();
    private final List<Tile> mTilesToAddRowFlash = new ArrayList<>();
    private final List<Tile> mTilesToAddColumnFlash = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosiveStripedTileCombineHandler(Engine engine) {
        super(engine);
        mRowFlashEffect = new RowFlashEffectSystem(engine, 3);
        mColumnFlashEffect = new ColumnFlashEffectSystem(engine, 3);
        mExplosiveStripedTileCombineEffect = new ExplosiveStripedTileCombineEffect(engine, Textures.CHERRY);
        mExplosionRowColumnCombineRingEffect = new ExplosiveStripedTileCombineRingEffectSystem(engine, 2);
        mRingLightParticleSystem = new SpriteParticleSystem(engine, Textures.FLASH_RING, 1)
                .setDuration(500)
                .setScale(0, 10)
                .setAlpha(255, 55)
                .setLayer(GameLayer.EFFECT_LAYER);
        mLightParticleSystem = new SpriteParticleSystem(engine, Textures.LIGHT_BG, 1)
                .setDuration(900)
                .setAlpha(255, 0, 500)
                .setScale(4, 4)
                .setLayer(GameLayer.EFFECT_LAYER);
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
        // First tile is explosion special tile
        if ((tileB.getSpecialType() == SpecialType.ROW_STRIPED
                || tileB.getSpecialType() == SpecialType.COLUMN_STRIPED)
                && tileA.getSpecialType() == SpecialType.EXPLOSIVE) {
            // We make sure the origin special tiles not being detected
            tileA.setTileState(TileState.MATCH);
            tileB.setTileState(TileState.MATCH);
            handleSpecialCombine(tiles, tileA, tileB, row, col);
            return true;
        }

        // First tile is row or column special tile
        if ((tileA.getSpecialType() == SpecialType.ROW_STRIPED
                || tileA.getSpecialType() == SpecialType.COLUMN_STRIPED)
                && tileB.getSpecialType() == SpecialType.EXPLOSIVE) {
            // We make sure the origin special tiles not being detected
            tileA.setTileState(TileState.MATCH);
            tileB.setTileState(TileState.MATCH);
            handleSpecialCombine(tiles, tileB, tileA, row, col);
            return true;
        }

        return false;
    }

    @Override
    protected void playTileEffect(Tile tileA, Tile tileB) {
        super.playTileEffect(tileA, tileB);
        mLightParticleSystem.oneShot(tileA.getCenterX(), tileA.getCenterY(), 1);
        mExplosiveStripedTileCombineEffect.activate(tileA.getCenterX(), tileA.getCenterY(), tileA.getTexture());
        mExplosionRowColumnCombineRingEffect.activate(tileA.getCenterX(), tileA.getCenterY());
        Sounds.EXPLOSIVE_STRIPED_COMBINE.play();
    }

    @Override
    public void onTimerEvent(long eventTime) {
        int size = mTilesToRemove.size();
        for (int i = 0; i < size; i++) {
            Tile tile = mTilesToRemove.get(i);
            tile.popTile();
        }

        int sizeRowFlash = mTilesToAddRowFlash.size();
        for (int i = 0; i < sizeRowFlash; i++) {
            Tile tile = mTilesToAddRowFlash.get(i);
            mRowFlashEffect.activate(tile.getCenterX(), tile.getCenterY());
        }

        int sizeColumnFlash = mTilesToAddColumnFlash.size();
        for (int i = 0; i < sizeColumnFlash; i++) {
            Tile tile = mTilesToAddColumnFlash.get(i);
            mColumnFlashEffect.activate(tile.getCenterX(), tile.getCenterY());
        }

        // Add ring effect at intersection
        mTilesToAddRowFlash.retainAll(mTilesToAddColumnFlash);
        Tile tile = mTilesToAddRowFlash.get(0);
        mRingLightParticleSystem.oneShot(tile.getCenterX(), tile.getCenterY(), 1);

        mTilesToRemove.clear();
        mTilesToAddRowFlash.clear();
        mTilesToAddColumnFlash.clear();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void handleSpecialCombine(Tile[][] tiles, Tile tileA, Tile tileB, int row, int col) {
        int targetRow = tileA.getRow();
        int targetCol = tileA.getColumn();

        // Pop 3 row tile
        for (int i = targetRow - 1; i <= targetRow + 1; i++) {
            // We make sure the index not out of bound
            if (i < 0 || i > row - 1) {
                continue;
            }
            for (int j = 0; j < col; j++) {
                Tile t = tiles[i][j];
                // We make sure not pop multiple times
                if (t.getTileState() == TileState.IDLE) {
                    mTilesToRemove.add(t);
                }
            }
        }

        // Pop 3 column tile
        for (int j = targetCol - 1; j <= targetCol + 1; j++) {
            // We make sure the index not out of bound
            if (j < 0 || j > col - 1) {
                continue;
            }
            for (int i = 0; i < row; i++) {
                Tile t = tiles[i][j];
                // We make sure not pop multiple times
                if (t.getTileState() == TileState.IDLE) {
                    mTilesToRemove.add(t);
                }
            }
        }

        // Add 3 row flash
        for (int i = -1; i <= 1; i++) {
            // We make sure the flash not out of bound
            int flashRow = targetRow + i;
            if (flashRow < 0 || flashRow > row - 1) {
                continue;
            }
            mTilesToAddRowFlash.add(tiles[flashRow][targetCol]);
        }
        // Add 3 column flash
        for (int i = -1; i <= 1; i++) {
            // We make sure the flash not out of bound
            int flashCol = targetCol + i;
            if (flashCol < 0 || flashCol > col - 1) {
                continue;
            }
            mTilesToAddColumnFlash.add(tiles[targetRow][flashCol]);
        }

        playTileEffect(tileA, tileB);
        mTimer.start();
    }
    //========================================================

}
