package com.nativegame.juicymatch.game.algorithm.special.combine;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.algorithm.TileType;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.timer.Timer;
import com.nativegame.natyengine.entity.timer.TimerEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceCreamExplosiveTileCombineHandler extends IceCreamCombineHandler implements TimerEvent.TimerEventListener {

    private static final long START_DELAY = 1200;

    private final Timer mTimer;

    private final List<Tile> mSpecialTiles = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public IceCreamExplosiveTileCombineHandler(Engine engine) {
        super(engine);
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
        if (tileA.getSpecialType() == SpecialType.ICE_CREAM
                && tileB.getSpecialType() == SpecialType.EXPLOSIVE) {
            handleSpecialCombine(tiles, tileA, tileB, row, col);
            return true;
        }
        if (tileB.getSpecialType() == SpecialType.ICE_CREAM
                && tileA.getSpecialType() == SpecialType.EXPLOSIVE) {
            handleSpecialCombine(tiles, tileB, tileA, row, col);
            return true;
        }

        return false;
    }

    @Override
    protected void playTileEffect(Tile colorTile, Tile fruitTile) {
        super.playTileEffect(colorTile, fruitTile);
        fruitTile.playTileEffect();
        colorTile.hideTile();
        fruitTile.hideTile();
        // Important to hide these tiles here, since the Algorithm hasn't been added yet
    }

    @Override
    public void onTimerEvent(long eventTime) {
        int size = mSpecialTiles.size();
        for (int i = 0; i < size; i++) {
            Tile tile = mSpecialTiles.get(i);
            tile.popTile();
        }
        mSpecialTiles.clear();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void handleSpecialCombine(Tile[][] tiles, Tile colorTile, Tile fruitTile, int row, int col) {
        colorTile.setTileState(TileState.MATCH);
        fruitTile.setTileState(TileState.MATCH);

        // Transform and pop the same type tile
        TileType targetType = fruitTile.getTileType();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Tile t = tiles[i][j];
                // We make sure not pop multiple times
                if (t.getTileType() == targetType && t.getTileState() == TileState.IDLE) {
                    // We transform this tile to explosion special type
                    t.setSpecialType(SpecialType.EXPLOSIVE);
                    mSpecialTiles.add(t);
                    // Add lightning effect from color tile to target tile
                    playLightningEffect(colorTile, t);
                }
            }
        }

        playTileEffect(colorTile, fruitTile);
        mTimer.start();
    }
    //========================================================

}
