package com.nativegame.juicymatch.game.booster;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.GameWorld;
import com.nativegame.juicymatch.game.algorithm.special.handler.ExplosiveTileHandler;
import com.nativegame.juicymatch.game.effect.booster.BombEffect;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.juicymatch.game.layer.tile.type.EmptyTile;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class BombController extends BoosterController {

    private final ExplosiveTileHandler mExplosiveTileHandler;
    private final BombEffect mBombEffect;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public BombController(Engine engine, TileSystem tileSystem) {
        super(engine, tileSystem);
        mExplosiveTileHandler = new ExplosiveTileHandler(engine);
        mBombEffect = new BombEffect(engine, Textures.BOMB);
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
        mBombEffect.activate(GameWorld.WORLD_WIDTH / 2f, GameWorld.WORLD_HEIGHT / 2f,
                touchDownTile.getX(), touchDownTile.getY());
        Sounds.TILE_SLIDE.play();
    }

    @Override
    protected void onRemoveBooster(Tile[][] tiles, Tile touchDownTile, Tile touchUpTile, int row, int col) {
        mExplosiveTileHandler.handleSpecialTile(tiles, touchDownTile, row, col);
        dispatchEvent(GameEvent.PLAYER_USE_BOOSTER);
    }
    //========================================================

}
