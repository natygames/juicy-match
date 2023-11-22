package com.nativegame.juicymatch.game.algorithm.special.finder;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.game.effect.UpgradeFruitEffectSystem;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class QuadSpecialTileFinder extends BaseSpecialTileFinder {

    private static final int UPGRADE_NUM = 4;

    private final UpgradeFruitEffectSystem mUpgradeFruitEffectSystem;

    private final int[] mPositionXFactors = new int[UPGRADE_NUM];
    private final int[] mPositionYFactors = new int[UPGRADE_NUM];

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected QuadSpecialTileFinder(Engine engine) {
        super(engine);
        mUpgradeFruitEffectSystem = new UpgradeFruitEffectSystem(engine, MAX_FIND_NUM * UPGRADE_NUM);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    protected void setPositionXFactors(int positionA, int positionB, int positionC, int positionD) {
        mPositionXFactors[0] = positionA;
        mPositionXFactors[1] = positionB;
        mPositionXFactors[2] = positionC;
        mPositionXFactors[3] = positionD;
    }

    protected void setPositionYFactors(int positionA, int positionB, int positionC, int positionD) {
        mPositionYFactors[0] = positionA;
        mPositionYFactors[1] = positionB;
        mPositionYFactors[2] = positionC;
        mPositionYFactors[3] = positionD;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void setUpgradeTiles(Tile[][] tiles, int row, int col) {
        for (int i = 0; i < UPGRADE_NUM; i++) {
            Tile t = tiles[row + mPositionYFactors[i]][col + mPositionXFactors[i]];
            // We only upgrade the none special tile and tile have not been reset
            if (t.getSpecialType() == SpecialType.NONE && t.getTileState() == TileState.MATCH) {
                t.setSpecialType(SpecialType.UPGRADE);
            }
        }
    }

    @Override
    protected void playUpgradeEffect(Tile tile) {
        super.playUpgradeEffect(tile);

        // Init end position
        float endX = tile.getX();
        float endY = tile.getY();

        // Add 4 upgrade fruit
        for (int i = 0; i < UPGRADE_NUM; i++) {
            // Init start position
            float startX = endX + mPositionXFactors[i] * tile.getWidth();
            float startY = endY + mPositionYFactors[i] * tile.getHeight();
            mUpgradeFruitEffectSystem.activate(startX, startY, endX, endY, (FruitType) tile.getTileType());
        }
    }
    //========================================================

}
