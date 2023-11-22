package com.nativegame.juicymatch.game.layer.tile.type;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.effect.SmokeEffect;
import com.nativegame.juicymatch.game.effect.piece.ExplosionPieceEffectSystem;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.ScaleModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.modifier.tween.OvershootTweener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class PieTile extends LayerObstacleTile {

    private static final int LAYER_PIECE = 5;
    private static final int PIE_PIECE = 20;
    private static final int PAN_PIECE = 10;

    private final Pie mPie;
    private final ExplosionPieceEffectSystem mWhitePiePieceEffect;
    private final ExplosionPieceEffectSystem mYellowPiePieceEffect;
    private final ExplosionPieceEffectSystem mPanPieceEffect;
    private final SmokeEffect mSmokeEffect;

    private final List<DummyTile> mDummyTiles = new ArrayList<>(3);

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public PieTile(TileSystem tileSystem, Engine engine, Texture texture, int obstacleLayer) {
        super(tileSystem, engine, texture, obstacleLayer);
        mPie = new Pie(engine, getPieTexture(obstacleLayer));
        mWhitePiePieceEffect = new ExplosionPieceEffectSystem(engine, Textures.WHITE_PIE_PIECE, PIE_PIECE);
        mYellowPiePieceEffect = new ExplosionPieceEffectSystem(engine, Textures.YELLOW_PIE_PIECE, PIE_PIECE);
        mPanPieceEffect = new ExplosionPieceEffectSystem(engine, Textures.PIE_PAN_PIECE, PAN_PIECE);
        mSmokeEffect = new SmokeEffect(engine, Textures.SMOKE_ANIMATION);
        mSmokeEffect.setScale(1.5f);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mPie.activate(mX, mY);
        initDummyTile();
    }

    @Override
    public boolean isSwappable() {
        if (mIsObstacle) {
            return false;
        }
        return super.isSwappable();
    }

    @Override
    protected void onUpdateLayer(int obstacleLayer) {
        // Check the current layer
        if (obstacleLayer == 0) {
            // Break the last layer
            mPie.removeFromGame();
            removeDummyTile();
            playPieEffect();
        } else {
            // Update next layer texture
            switch (obstacleLayer) {
                case 1:
                    mPie.setTexture(Textures.PIE_01);
                    break;
                case 2:
                    mPie.setTexture(Textures.PIE_02);
                    break;
                case 3:
                    mPie.setTexture(Textures.PIE_03);
                    break;
            }
            mPie.addBounceEffect();
            playLayerEffect();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private Texture getPieTexture(int obstacleLayer) {
        switch (obstacleLayer) {
            case 1:
                return Textures.PIE_01;
            case 2:
                return Textures.PIE_02;
            case 3:
                return Textures.PIE_03;
            case 4:
                return Textures.PIE_04;
            default:
                throw new IllegalArgumentException("Pie texture not found!");
        }
    }

    private void initDummyTile() {
        // Add the DummyTile as the remaining part of pie
        DummyTile dummyA = (DummyTile) mParent.getChildAt(mRow - 1, mCol);
        DummyTile dummyB = (DummyTile) mParent.getChildAt(mRow, mCol - 1);
        DummyTile dummyC = (DummyTile) mParent.getChildAt(mRow - 1, mCol - 1);
        dummyA.setTargetTile(this);
        dummyB.setTargetTile(this);
        dummyC.setTargetTile(this);
        mDummyTiles.add(dummyA);
        mDummyTiles.add(dummyB);
        mDummyTiles.add(dummyC);
    }

    private void removeDummyTile() {
        // We notify all the DummyTiles
        int size = mDummyTiles.size();
        for (int i = 0; i < size; i++) {
            DummyTile dummy = mDummyTiles.get(i);
            dummy.popDummyTile();
        }
    }

    private void playPieEffect() {
        mSmokeEffect.activate(mX, mY);
        mPanPieceEffect.activate(mX, mY, PAN_PIECE);
        mWhitePiePieceEffect.activate(mX, mY, PIE_PIECE);
        mYellowPiePieceEffect.activate(mX, mY, PIE_PIECE);
        Sounds.PIE_EXPLODE.play();
        Sounds.PIE_PAN_EXPLODE.play();
    }

    private void playLayerEffect() {
        mSmokeEffect.activate(mX, mY);
        mWhitePiePieceEffect.activate(mX, mY, LAYER_PIECE);
        mYellowPiePieceEffect.activate(mX, mY, LAYER_PIECE);
        Sounds.PIE_EXPLODE.play();
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    private static class Pie extends Sprite {

        private static final long TIME_TO_BOUNCE_OUT = 200;
        private static final long TIME_TO_BOUNCE_IN = 300;

        private final ScaleModifier mBounceOutModifier;
        private final ScaleModifier mBounceInModifier;

        //--------------------------------------------------------
        // Constructors
        //--------------------------------------------------------
        public Pie(Engine engine, Texture texture) {
            super(engine, texture);
            mBounceOutModifier = new ScaleModifier(1, 1.2f, TIME_TO_BOUNCE_OUT);
            mBounceInModifier = new ScaleModifier(1.2f, 1, TIME_TO_BOUNCE_IN, TIME_TO_BOUNCE_OUT,
                    OvershootTweener.getInstance());
            mBounceInModifier.setModifyBefore(false);
            setLayer(GameLayer.TILE_LAYER);
        }
        //========================================================

        //--------------------------------------------------------
        // Overriding methods
        //--------------------------------------------------------
        @Override
        public void onUpdate(long elapsedMillis) {
            mBounceOutModifier.update(this, elapsedMillis);
            mBounceInModifier.update(this, elapsedMillis);
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

        public void addBounceEffect() {
            mBounceOutModifier.init(this);
            mBounceInModifier.init(this);
        }
        //========================================================

    }
    //========================================================

}
