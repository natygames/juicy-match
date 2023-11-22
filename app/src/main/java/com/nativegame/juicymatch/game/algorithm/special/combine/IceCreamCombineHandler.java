package com.nativegame.juicymatch.game.algorithm.special.combine;

import com.nativegame.juicymatch.game.effect.lightning.LightningEffectSystem;
import com.nativegame.juicymatch.game.effect.lightning.LightningGlitterEffectSystem;
import com.nativegame.juicymatch.game.effect.piece.IceCreamPieceEffectSystem;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class IceCreamCombineHandler extends BaseSpecialCombineHandler {

    private static final int MAX_LIGHTNING_NUM = 20;

    private final LightningEffectSystem mLightningEffectSystem;
    private final LightningGlitterEffectSystem mLightningGlitterEffectSystem;
    private final IceCreamPieceEffectSystem mIceCreamPieceEffectSystem;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected IceCreamCombineHandler(Engine engine) {
        super(engine);
        mLightningEffectSystem = new LightningEffectSystem(engine, MAX_LIGHTNING_NUM);
        mLightningGlitterEffectSystem = new LightningGlitterEffectSystem(engine, MAX_LIGHTNING_NUM);
        mIceCreamPieceEffectSystem = new IceCreamPieceEffectSystem(engine, 1);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void playTileEffect(Tile colorTile, Tile fruitTile) {
        super.playTileEffect(colorTile, fruitTile);
        mIceCreamPieceEffectSystem.activate(colorTile.getCenterX(), colorTile.getCenterY());
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    protected void playLightningEffect(Tile colorTile, Tile targetTile) {
        // Calculate distance and angle between two tiles
        float distanceX = colorTile.getX() - targetTile.getX();
        float distanceY = colorTile.getY() - targetTile.getY();
        int distance = (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        int angle = getAngle(distanceX, distanceY);
        // Add one lightning and glitter
        mLightningEffectSystem.activate(colorTile.getCenterX(), colorTile.getCenterY(), distance, angle);
        mLightningGlitterEffectSystem.activate(targetTile.getCenterX(), targetTile.getCenterY());
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
