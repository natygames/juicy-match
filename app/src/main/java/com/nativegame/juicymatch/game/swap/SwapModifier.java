package com.nativegame.juicymatch.game.swap;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.Entity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SwapModifier extends Entity {

    private SwapListener mListener;
    private Tile mTileA;
    private Tile mTileB;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public SwapModifier(Engine engine) {
        super(engine);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public SwapListener getListener() {
        return mListener;
    }

    public void setListener(SwapListener listener) {
        mListener = listener;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mTileA.swapTile(elapsedMillis);
        mTileB.swapTile(elapsedMillis);
        // Remove after swap finished
        if (!mTileA.isMoving() && !mTileB.isMoving()) {
            if (mListener != null) {
                mListener.onSwap(mTileA, mTileB);
            }
            removeFromGame();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(Tile tileA, Tile tileB) {
        mTileA = tileA;
        mTileB = tileB;
        Sounds.TILE_SWAP.play();
        addToGame();
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    public interface SwapListener {

        void onSwap(Tile tileA, Tile tileB);

    }
    //========================================================

}
