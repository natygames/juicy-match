package com.nativegame.match3game.game.booster;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.game.JuicyMatch;
import com.nativegame.match3game.game.algorithm.special.handler.ColumnStripedTileHandler;
import com.nativegame.match3game.game.algorithm.special.handler.RowStripedTileHandler;
import com.nativegame.match3game.game.effect.booster.HammerEffect;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.TileSystem;
import com.nativegame.match3game.game.layer.tile.type.EmptyTile;
import com.nativegame.nattyengine.engine.Engine;

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
        mHammerEffect.activate(JuicyMatch.WORLD_WIDTH / 2f, JuicyMatch.WORLD_HEIGHT / 2f,
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
