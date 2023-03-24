package com.nativegame.match3game.game.layer.tile;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.texture.Texture;

public enum SpecialType {
    NONE,
    UPGRADE,
    ROW_SPECIAL_TILE,
    COLUMN_SPECIAL_TILE,
    EXPLOSION_SPECIAL_TILE,
    COLOR_SPECIAL_TILE;

    public boolean hasPower() {
        return this != NONE && this != UPGRADE;
    }

    public boolean hasEffect() {
        return this != COLOR_SPECIAL_TILE;
    }

    public Texture getTexture(FruitType fruitType) {
        switch (this) {
            case ROW_SPECIAL_TILE:
                return getRowSpecialTileTexture(fruitType);
            case COLUMN_SPECIAL_TILE:
                return getColumnSpecialTileTexture(fruitType);
            case EXPLOSION_SPECIAL_TILE:
                return getExplosionSpecialTileTexture(fruitType);
            case COLOR_SPECIAL_TILE:
                return Textures.ICE_CREAM;
            default:
                throw new IllegalArgumentException("No such tile drawable!");
        }
    }

    public Texture[] getPiecesTexture(FruitType fruitType) {
        switch (this) {
            case ROW_SPECIAL_TILE:
                return getRowSpecialTilePiecesTexture(fruitType);
            case COLUMN_SPECIAL_TILE:
                return getColumnSpecialTilePiecesTexture(fruitType);
            case EXPLOSION_SPECIAL_TILE:
                return getExplosionSpecialTilePiecesTexture(fruitType);
            default:
                throw new IllegalArgumentException("No such tile drawable!");
        }
    }

    //--------------------------------------------------------
    // Methods to get special tile drawable
    //--------------------------------------------------------
    private static Texture getRowSpecialTileTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return Textures.ROW_CHERRY;
            case STRAWBERRY:
                return Textures.ROW_STRAWBERRY;
            case LEMON:
                return Textures.ROW_LEMON;
            case COCONUT:
                return Textures.ROW_COCONUT;
            case BANANA:
                return Textures.ROW_BANANA;
            default:
                throw new IllegalArgumentException("No such tile drawable!");
        }
    }

    private static Texture getColumnSpecialTileTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return Textures.COLUMN_CHERRY;
            case STRAWBERRY:
                return Textures.COLUMN_STRAWBERRY;
            case LEMON:
                return Textures.COLUMN_LEMON;
            case COCONUT:
                return Textures.COLUMN_COCONUT;
            case BANANA:
                return Textures.COLUMN_BANANA;
            default:
                throw new IllegalArgumentException("No such tile drawable!");
        }
    }

    private static Texture getExplosionSpecialTileTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return Textures.EXPLOSION_CHERRY;
            case STRAWBERRY:
                return Textures.EXPLOSION_STRAWBERRY;
            case LEMON:
                return Textures.EXPLOSION_LEMON;
            case COCONUT:
                return Textures.EXPLOSION_COCONUT;
            case BANANA:
                return Textures.EXPLOSION_BANANA;
            default:
                throw new IllegalArgumentException("No such tile drawable!");
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods to get special tile pieces drawables
    //--------------------------------------------------------
    private static Texture[] getRowSpecialTilePiecesTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return new Texture[]{Textures.ROW_CHERRY_PIECE_01, Textures.ROW_CHERRY_PIECE_02};
            case STRAWBERRY:
                return new Texture[]{Textures.ROW_STRAWBERRY_PIECE_01, Textures.ROW_STRAWBERRY_PIECE_02};
            case LEMON:
                return new Texture[]{Textures.ROW_LEMON_PIECE_01, Textures.ROW_LEMON_PIECE_02};
            case COCONUT:
                return new Texture[]{Textures.ROW_COCONUT_PIECE_01, Textures.ROW_COCONUT_PIECE_02};
            case BANANA:
                return new Texture[]{Textures.ROW_BANANA_PIECE_01, Textures.ROW_BANANA_PIECE_02};
            default:
                throw new IllegalArgumentException("No such tile drawable!");
        }
    }

    private static Texture[] getColumnSpecialTilePiecesTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return new Texture[]{Textures.COLUMN_CHERRY_PIECE_01, Textures.COLUMN_CHERRY_PIECE_02};
            case STRAWBERRY:
                return new Texture[]{Textures.COLUMN_STRAWBERRY_PIECE_01, Textures.COLUMN_STRAWBERRY_PIECE_02};
            case LEMON:
                return new Texture[]{Textures.COLUMN_LEMON_PIECE_01, Textures.COLUMN_LEMON_PIECE_02};
            case COCONUT:
                return new Texture[]{Textures.COLUMN_COCONUT_PIECE_01, Textures.COLUMN_COCONUT_PIECE_02};
            case BANANA:
                return new Texture[]{Textures.COLUMN_BANANA_PIECE_01, Textures.COLUMN_BANANA_PIECE_02};
            default:
                throw new IllegalArgumentException("No such tile drawable!");
        }
    }

    private static Texture[] getExplosionSpecialTilePiecesTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return new Texture[]{Textures.EXPLOSION_CHERRY_PIECE_01, Textures.EXPLOSION_CHERRY_PIECE_02};
            case STRAWBERRY:
                return new Texture[]{Textures.EXPLOSION_STRAWBERRY_PIECE_01, Textures.EXPLOSION_STRAWBERRY_PIECE_02};
            case LEMON:
                return new Texture[]{Textures.EXPLOSION_LEMON_PIECE_01, Textures.EXPLOSION_LEMON_PIECE_02};
            case COCONUT:
                return new Texture[]{Textures.EXPLOSION_COCONUT_PIECE_01, Textures.EXPLOSION_COCONUT_PIECE_02};
            case BANANA:
                return new Texture[]{Textures.EXPLOSION_BANANA_PIECE_01, Textures.EXPLOSION_BANANA_PIECE_02};
            default:
                throw new IllegalArgumentException("No such tile drawable!");
        }
    }
    //========================================================

}
