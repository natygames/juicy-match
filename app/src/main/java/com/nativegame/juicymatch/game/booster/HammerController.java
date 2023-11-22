package com.nativegame.juicymatch.game.booster;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.GameWorld;
import com.nativegame.juicymatch.game.algorithm.special.handler.ColumnStripedTileHandler;
import com.nativegame.juicymatch.game.algorithm.special.handler.RowStripedTileHandler;
import com.nativegame.juicymatch.game.effect.booster.HammerEffect;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.juicymatch.game.layer.tile.type.EmptyTile;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HammerController extends BoosterController {

    private final RowStripedTileHandler mRowStripedTileHandler;
    private final ColumnStripedTileHandler mColumnStripedTileHandler;
    private final HammerEffect mHammerEffect;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public HammerController(Engine engine, TileSystem tileSystem) {
        super(engine, tileSystem);
        mRowStripedTileHandler = new RowStripedTileHandler(engine);
        mColumnStripedTileHandler = new ColumnStripedTileHandler(engine);
        mHammerEffect = new HammerEffect(engine, Textures.HAMMER);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected boolean isAddBooster(Tile touchDownTile, Tile touchUpTile) {
        return !(touchDownTile instanceof EmptyTile);
    }

    @Override
    protected void onAddBooster(Tile[][] tiles, Tile touchDownTile, Tile touchUpTile, int row, int col) {
        mHammerEffect.activate(GameWorld.WORLD_WIDTH / 2f, GameWorld.WORLD_HEIGHT / 2f,
                touchDownTile.getX(), touchDownTile.getY());
        Sounds.TILE_SLIDE.play();
    }

    @Override
    protected void onRemoveBooster(Tile[][] tiles, Tile touchDownTile, Tile touchUpTile, int row, int col) {
        mRowStripedTileHandler.handleSpecialTile(tiles, touchDownTile, row, col);
        mColumnStripedTileHandler.handleSpecialTile(tiles, touchDownTile, row, col);
        dispatchEvent(GameEvent.PLAYER_USE_BOOSTER);
    }
    //========================================================

}
