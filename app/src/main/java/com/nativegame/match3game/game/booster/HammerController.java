package com.nativegame.match3game.game.booster;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.game.JuicyMatch;
import com.nativegame.match3game.game.algorithm.special.handler.ColumnSpecialTileHandler;
import com.nativegame.match3game.game.algorithm.special.handler.RowSpecialTileHandler;
import com.nativegame.match3game.game.effect.booster.HammerEffect;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.TileSystem;
import com.nativegame.match3game.game.layer.tile.type.EmptyTile;
import com.nativegame.nattyengine.engine.Engine;

public class HammerController extends BoosterController {

    private final RowSpecialTileHandler mRowSpecialTileHandler;
    private final ColumnSpecialTileHandler mColumnSpecialTileHandler;
    private final HammerEffect mHammerEffect;

    public HammerController(Engine engine, TileSystem tileSystem) {
        super(engine, tileSystem);
        mRowSpecialTileHandler = new RowSpecialTileHandler(engine);
        mColumnSpecialTileHandler = new ColumnSpecialTileHandler(engine);
        mHammerEffect = new HammerEffect(engine, Textures.HAMMER);
    }

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
        mRowSpecialTileHandler.handleSpecialTile(tiles, touchDownTile, row, col);
        mColumnSpecialTileHandler.handleSpecialTile(tiles, touchDownTile, row, col);
        dispatchEvent(GameEvent.PLAYER_USE_BOOSTER);
    }
    //========================================================

}
