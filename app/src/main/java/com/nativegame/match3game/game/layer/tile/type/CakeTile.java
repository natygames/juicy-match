package com.nativegame.match3game.game.layer.tile.type;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.effect.SmokeEffect;
import com.nativegame.match3game.game.effect.piece.CakePiece;
import com.nativegame.match3game.game.effect.piece.CakePieceEffect;
import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.TileSystem;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class CakeTile extends ObstacleTile {

    private static final int CAKE_PIECE_NUM = 6;

    private final SmokeEffect mSmokeEffect;
    private final List<CakePieceEffect> mCakePieceEffects = new ArrayList<>(CAKE_PIECE_NUM);

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public CakeTile(TileSystem tileSystem, Engine engine, Texture texture, int obstacleLayer) {
        super(tileSystem, engine, texture, FruitType.NONE, obstacleLayer);
        mSmokeEffect = new SmokeEffect(engine, Textures.SMOKE_ANIMATION);
        // Init cake pieces
        for (int i = 0; i < CAKE_PIECE_NUM; i++) {
            CakePiece cakePiece = CakePiece.values()[i];
            mCakePieceEffects.add(new CakePieceEffect(engine, cakePiece.getTexture(), cakePiece));
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean isSwappable() {
        if (mIsObstacle) {
            return false;
        }
        return super.isSwappable();
    }

    @Override
    protected void playObstacleEffect(int obstacleLayer) {
        // Check the current layer
        if (obstacleLayer == 1) {
            // Break the last layer
            playCakeEffect();
        } else {
            // Update next layer
            switch (obstacleLayer) {
                case 2:
                    setTexture(Textures.CAKE_01);
                    break;
                case 3:
                    setTexture(Textures.CAKE_02);
                    break;
                case 4:
                    setTexture(Textures.CAKE_03);
                    break;
            }
            playSmokeEffect();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void playCakeEffect() {
        for (int i = 0; i < CAKE_PIECE_NUM; i++) {
            mCakePieceEffects.get(i).activate(getCenterX(), getCenterY());
        }
        mCakePieceEffects.clear();
        Sounds.CAKE_EXPLODE.play();
    }

    private void playSmokeEffect() {
        mSmokeEffect.activate(getCenterX(), getCenterY());
    }
    //========================================================

}
