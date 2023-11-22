package com.nativegame.juicymatch.game.layer.tile.type;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.effect.piece.StarfishPieceEffect;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class StarfishTile extends SolidTile {

    private final StarfishPieceEffect mStarfishPieceEffect;

    private boolean mIsStarfish = true;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public StarfishTile(TileSystem tileSystem, Engine engine, Texture texture) {
        super(tileSystem, engine, texture, FruitType.NONE);
        mStarfishPieceEffect = new StarfishPieceEffect(engine, Textures.STARFISH);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public boolean isStarfish() {
        return mIsStarfish;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void popTile() {
        if (mIsStarfish) {
            return;
        }
        super.popTile();
    }

    @Override
    public void playTileEffect() {
        if (mIsStarfish) {
            playStarfishEffect();
            mIsStarfish = false;
            return;
        }
        super.playTileEffect();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void popStarfishTile() {
        // Important to not reuse popTile() or matchTile()
        mTileState = TileState.MATCH;
    }

    private void playStarfishEffect() {
        mStarfishPieceEffect.activate(getCenterX(), getCenterY());
        Sounds.COLLECT_STARFISH.play();
    }
    //========================================================

}
