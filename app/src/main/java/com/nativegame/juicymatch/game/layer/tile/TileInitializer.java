package com.nativegame.juicymatch.game.layer.tile;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.layer.tile.type.CakeTile;
import com.nativegame.juicymatch.game.layer.tile.type.CandyTile;
import com.nativegame.juicymatch.game.layer.tile.type.CookieTile;
import com.nativegame.juicymatch.game.layer.tile.type.DummyTile;
import com.nativegame.juicymatch.game.layer.tile.type.EmptyTile;
import com.nativegame.juicymatch.game.layer.tile.type.PieTile;
import com.nativegame.juicymatch.game.layer.tile.type.PipeTile;
import com.nativegame.juicymatch.game.layer.tile.type.SolidTile;
import com.nativegame.juicymatch.game.layer.tile.type.StarfishTile;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.util.RandomUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TileInitializer {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static Tile createTile(TileSystem tileSystem, Engine engine, int row, int col, char c) {
        switch (c) {
            case 'e':
                return new EmptyTile(engine, Textures.EMPTY);
            case 'p':
                return new PipeTile(engine, Textures.PIPE);
            case 'x':
                return new StarfishTile(tileSystem, engine, Textures.STARFISH);
            case 'n':
                FruitType fruitType = getShuffledRandomType(tileSystem, row, col);
                return new SolidTile(tileSystem, engine, fruitType.getTexture(), fruitType);
            case 'w':
                return new SolidTile(tileSystem, engine, Textures.EMPTY, FruitType.NONE, TileState.UNREACHABLE);
            case 's':
                return new SolidTile(tileSystem, engine, Textures.STRAWBERRY, FruitType.STRAWBERRY);
            case 'l':
                return new SolidTile(tileSystem, engine, Textures.LEMON, FruitType.LEMON);
            case 'h':
                return new SolidTile(tileSystem, engine, Textures.CHERRY, FruitType.CHERRY);
            case 'o':
                return new SolidTile(tileSystem, engine, Textures.COCONUT, FruitType.COCONUT);
            case 'c':
                return new CookieTile(tileSystem, engine, Textures.COOKIE);
            case 'd':
                return new CandyTile(tileSystem, engine, Textures.CANDY_02, 2);
            case '1':
                return new CakeTile(tileSystem, engine, Textures.CAKE_01, 1);
            case '2':
                return new CakeTile(tileSystem, engine, Textures.CAKE_02, 2);
            case '3':
                return new CakeTile(tileSystem, engine, Textures.CAKE_03, 3);
            case '6':
                return new PieTile(tileSystem, engine, Textures.EMPTY, 1);
            case '7':
                return new PieTile(tileSystem, engine, Textures.EMPTY, 2);
            case '8':
                return new PieTile(tileSystem, engine, Textures.EMPTY, 3);
            case '9':
                return new PieTile(tileSystem, engine, Textures.EMPTY, 4);
            case '0':
                return new DummyTile(tileSystem, engine, Textures.EMPTY);
            case 'R':
                FruitType rowSpecialFruitType = getShuffledRandomType(tileSystem, row, col);
                return new SolidTile(tileSystem, engine, SpecialType.ROW_STRIPED.getTexture(rowSpecialFruitType),
                        rowSpecialFruitType, SpecialType.ROW_STRIPED);
            case 'C':
                FruitType columnSpecialFruitType = getShuffledRandomType(tileSystem, row, col);
                return new SolidTile(tileSystem, engine, SpecialType.COLUMN_STRIPED.getTexture(columnSpecialFruitType),
                        columnSpecialFruitType, SpecialType.COLUMN_STRIPED);
            case 'E':
                FruitType explosionSpecialFruitType = getShuffledRandomType(tileSystem, row, col);
                return new SolidTile(tileSystem, engine, SpecialType.EXPLOSIVE.getTexture(explosionSpecialFruitType),
                        explosionSpecialFruitType, SpecialType.EXPLOSIVE);
            case 'I':
                return new SolidTile(tileSystem, engine, Textures.ICE_CREAM, FruitType.NONE, SpecialType.ICE_CREAM);
            default:
                throw new IllegalArgumentException("Tile not found!");
        }
    }

    public static FruitType getShuffledRandomType(TileSystem tileSystem, int row, int col) {
        FruitType type;
        do {
            type = getRandomType();
            // Reset type if match detected
        } while ((row >= 2 && tileSystem.getChildAt(row - 1, col).getTileType() == type
                && tileSystem.getChildAt(row - 2, col).getTileType() == type)
                || (col >= 2 && tileSystem.getChildAt(row, col - 1).getTileType() == type
                && tileSystem.getChildAt(row, col - 2).getTileType() == type));

        return type;
    }

    public static FruitType getRandomType() {
        return getRandomType(Level.LEVEL_DATA.getFruitCount());
    }

    public static FruitType getRandomType(int fruitCount) {
        int i = RandomUtils.nextInt(fruitCount);
        switch (i) {
            case 0:
                return FruitType.CHERRY;
            case 1:
                return FruitType.STRAWBERRY;
            case 2:
                return FruitType.LEMON;
            case 3:
                return FruitType.COCONUT;
            case 4:
                return RandomUtils.nextFloat(1) < 0.35f ? FruitType.BANANA : getRandomType(4);
            default:
                throw new IllegalArgumentException("FruitType not found!");
        }
    }
    //========================================================

}
