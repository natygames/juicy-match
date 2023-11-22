package com.nativegame.juicymatch.game.layer.honey;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.effect.piece.HoneyPiece;
import com.nativegame.juicymatch.game.effect.piece.HoneyPieceEffect;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.layer.LayerSprite;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.ScaleInModifier;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.modifier.tween.OvershootTweener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Honey extends LayerSprite {

    private static final int HONEY_PIECE = 5;
    private static final long TIME_TO_SCALE_IN = 300;

    private final HoneyType mHoneyType;
    private final ScaleInModifier mScaleInModifier;

    private final List<HoneyPieceEffect> mHoneyPieceEffects = new ArrayList<>(HONEY_PIECE);

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public Honey(Engine engine, Texture texture, HoneyType honeyType) {
        super(engine, texture);
        mHoneyType = honeyType;
        mScaleInModifier = new ScaleInModifier(TIME_TO_SCALE_IN, OvershootTweener.getInstance());
        // Init honey pieces
        for (int i = 0; i < HONEY_PIECE; i++) {
            mHoneyPieceEffects.add(new HoneyPieceEffect(engine, Textures.HONEY_PIECE, HoneyPiece.values()[i]));
        }
        setRotation(honeyType.getAngle());
        setLayer(GameLayer.HONEY_LAYER);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public HoneyType getHoneyType() {
        return mHoneyType;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mScaleInModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void playHoneyEffect() {
        // Play explosion effect
        for (int i = 0; i < HONEY_PIECE; i++) {
            mHoneyPieceEffects.get(i).activate(getCenterX(), getCenterY());
        }
        mHoneyPieceEffects.clear();
        Sounds.HONEY_EXPLODE.play();

        // Play show effect and add to game
        mScaleInModifier.init(this);
        addToGame();
    }
    //========================================================

}
