package com.nativegame.match3game.game.algorithm.special.handler;

import com.nativegame.match3game.algorithm.TileState;
import com.nativegame.match3game.game.effect.lightning.LightningEffectSystem;
import com.nativegame.match3game.game.effect.lightning.LightningGlitterEffectSystem;
import com.nativegame.match3game.game.effect.piece.IceCreamPieceEffectSystem;
import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.Tile;
import com.nativegame.match3game.game.layer.tile.TileInitializer;
import com.nativegame.nattyengine.engine.Engine;

public class ColorSpecialTileHandler extends BaseSpecialTileHandler {

    private static final int MAX_LIGHTNING_NUM = 10;

    private final LightningEffectSystem mLightningEffectSystem;
    private final LightningGlitterEffectSystem mLightningGlitterEffectSystem;
    private final IceCreamPieceEffectSystem mIceCreamPieceEffectSystem;

    public ColorSpecialTileHandler(Engine engine) {
        super(engine);
        mLightningEffectSystem = new LightningEffectSystem(engine, MAX_LIGHTNING_NUM);
        mLightningGlitterEffectSystem = new LightningGlitterEffectSystem(engine, MAX_LIGHTNING_NUM);
        mIceCreamPieceEffectSystem = new IceCreamPieceEffectSystem(engine, 1);
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void handleSpecialTile(Tile[][] tiles, Tile tile, int row, int col) {
        // Generate a random fruit mType
        FruitType targetType = TileInitializer.getRandomFruit();

        // Pop the same type tile
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Tile t = tiles[i][j];
                // We make sure not pop multiple times
                if (t.getTileType() == targetType && t.getTileState() == TileState.IDLE) {
                    // We pop this tile and obstacle around
                    t.matchTile();
                    // Add lightning effect from color tile to target tile
                    playLightningEffect(tile, t);
                }
            }
        }

        playTileEffect(tile);
    }

    @Override
    protected void playTileEffect(Tile tile) {
        super.playTileEffect(tile);
        mIceCreamPieceEffectSystem.activate(tile.getCenterX(), tile.getCenterY());
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void playLightningEffect(Tile colorTile, Tile targetTile) {
        // Calculate distance and angle between two tiles
        float distanceX = colorTile.getX() - targetTile.getX();
        float distanceY = colorTile.getY() - targetTile.getY();
        int distance = (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        int angle = getAngle(distanceX, distanceY);
        // Add one lightning and glitter
        mLightningEffectSystem.activate(colorTile.getX() + colorTile.getWidth() / 2f,
                colorTile.getY() + colorTile.getHeight() / 2f, distance, angle);
        mLightningGlitterEffectSystem.activate(targetTile.getX() + targetTile.getWidth() / 2f,
                targetTile.getY() + targetTile.getHeight() / 2f);
    }

    private int getAngle(float distanceX, float distanceY) {
        //  dx+  | dx-
        //  dy+  | dy+
        // ------|------
        //  dx+  | dx-
        //  dy-  | dy-
        double angleInRads = Math.atan2(Math.abs(distanceY), Math.abs(distanceX));
        int angle = (int) Math.toDegrees(angleInRads);
        if (distanceX >= 0) {
            if (distanceY >= 0) {
                return 90 + angle;
            } else {
                return 90 - angle;
            }
        } else {
            if (distanceY >= 0) {
                return -90 - angle;
            } else {
                return -90 + angle;
            }
        }
    }
    //========================================================

}
