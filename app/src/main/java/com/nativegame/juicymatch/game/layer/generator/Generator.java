package com.nativegame.juicymatch.game.layer.generator;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.LayerSprite;
import com.nativegame.juicymatch.game.layer.tile.TileResetter;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.RotationModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.modifier.tween.OvershootTweener;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class Generator extends LayerSprite {

    private final GeneratorPivot mPivot;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public Generator(Engine engine, Texture texture) {
        super(engine, texture);
        mPivot = new GeneratorPivot(engine, Textures.GENERATOR_PIVOT);
        setLayer(GameLayer.GENERATOR_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mY -= 225;
        mPivot.setX(mX);
        mPivot.setEndY(getEndY());
        mPivot.addToGame();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public abstract TileResetter getResetter();

    public void playGeneratorEffect() {
        mPivot.activate();
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    private static class GeneratorPivot extends Sprite {

        private static final long TIME_TO_ROTATE = 500;
        private static final long TIME_TO_PAUSE = 200;   // We pause for a while before the tile appear

        private final RotationModifier mRotationModifier;

        //--------------------------------------------------------
        // Constructors
        //--------------------------------------------------------
        public GeneratorPivot(Engine engine, Texture texture) {
            super(engine, texture);
            mRotationModifier = new RotationModifier(0, 180, TIME_TO_ROTATE, TIME_TO_PAUSE,
                    OvershootTweener.getInstance());
            setLayer(GameLayer.GENERATOR_LAYER);
        }
        //========================================================

        //--------------------------------------------------------
        // Overriding methods
        //--------------------------------------------------------
        @Override
        public void onUpdate(long elapsedMillis) {
            mRotationModifier.update(this, elapsedMillis);
        }
        //========================================================

        //--------------------------------------------------------
        // Methods
        //--------------------------------------------------------
        public void activate() {
            mRotationModifier.init(this);
        }
        //========================================================

    }
    //========================================================

}
