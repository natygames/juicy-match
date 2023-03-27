package com.nativegame.match3game.game.layer.tile;

import com.nativegame.match3game.algorithm.TileState;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.layer.tile.type.CakeTile;
import com.nativegame.match3game.game.layer.tile.type.CookieTile;
import com.nativegame.match3game.game.layer.tile.type.EmptyTile;
import com.nativegame.match3game.game.layer.tile.type.SolidTile;
import com.nativegame.match3game.game.layer.tile.type.StarfishTile;
import com.nativegame.match3game.game.layer.tile.type.TubeTile;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TileInitializer {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static Tile createTile(TileSystem tileSystem, Engine engine, char c) {
        switch (c) {
            case 'e':
                return new EmptyTile(engine, Textures.EMPTY);
            case 'n':
                return new SolidTile(tileSystem, engine, Textures.EMPTY, FruitType.NONE);
            case 'w':
                return new SolidTile(tileSystem, engine, Textures.EMPTY, FruitType.NONE, TileState.UNREACHABLE);
            case 's':
                return new SolidTile(tileSystem, engine, Textures.STRAWBERRY, FruitType.STRAWBERRY);
            case 'l':
                return new SolidTile(tileSystem, engine, Textures.LEMON, FruitType.LEMON);
            case 'i':
                return new SolidTile(tileSystem, engine, Textures.ICE_CREAM, FruitType.NONE, SpecialType.COLOR_SPECIAL_TILE);
            case 'c':
                return new CookieTile(tileSystem, engine, Textures.COOKIE);
            case 'o':
                return new CakeTile(tileSystem, engine, Textures.CAKE_01, 1);
            case 'O':
                return new CakeTile(tileSystem, engine, Textures.CAKE_02, 2);
            case 'q':
                return new CakeTile(tileSystem, engine, Textures.CAKE_03, 3);
            case 'Q':
                return new CakeTile(tileSystem, engine, Textures.CAKE_04, 4);
            case 'x':
                return new StarfishTile(tileSystem, engine, Textures.STARFISH);
            case 'I':
                return new TubeTile(engine, Textures.TUBE);
            default:
                throw new IllegalArgumentException("Tile not found!");
        }
    }

    public static FruitType getRandomFruit() {
        int random = (int) (Math.random() * Level.LEVEL_DATA.getFruitNum());
        switch (random) {
            case 0:
                return FruitType.CHERRY;
            case 1:
                return FruitType.STRAWBERRY;
            case 2:
                return FruitType.LEMON;
            case 3:
                return FruitType.COCONUT;
            case 4:
                return FruitType.BANANA;
            default:
                throw new IllegalArgumentException("Tile not found!");
        }
    }
    //========================================================

}
