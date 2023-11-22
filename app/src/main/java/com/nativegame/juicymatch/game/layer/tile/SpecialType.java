package com.nativegame.juicymatch.game.layer.tile;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum SpecialType {
    NONE,
    UPGRADE,
    ROW_STRIPED,
    COLUMN_STRIPED,
    EXPLOSIVE,
    ICE_CREAM;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public boolean hasPower() {
        return this != NONE && this != UPGRADE;
    }

    public boolean hasEffect() {
        return this != ICE_CREAM;
    }

    public Texture getTexture(FruitType fruitType) {
        switch (this) {
            case ROW_STRIPED:
                return getRowStripedTileTexture(fruitType);
            case COLUMN_STRIPED:
                return getColumnStripedTileTexture(fruitType);
            case EXPLOSIVE:
                return getExplosiveTileTexture(fruitType);
            case ICE_CREAM:
                return Textures.ICE_CREAM;
            default:
                throw new IllegalArgumentException("Special tile Texture not found!");
        }
    }

    public Texture[] getPiecesTexture(FruitType fruitType) {
        switch (this) {
            case ROW_STRIPED:
                return getRowStripedTilePiecesTexture(fruitType);
            case COLUMN_STRIPED:
                return getColumnStripedTilePiecesTexture(fruitType);
            case EXPLOSIVE:
                return getExplosiveTilePiecesTexture(fruitType);
            default:
                throw new IllegalArgumentException("Special tile pieces Texture not found!");
        }
    }

    private Texture getRowStripedTileTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return Textures.ROW_STRIPED_CHERRY;
            case STRAWBERRY:
                return Textures.ROW_STRIPED_STRAWBERRY;
            case LEMON:
                return Textures.ROW_STRIPED_LEMON;
            case COCONUT:
                return Textures.ROW_STRIPED_COCONUT;
            case BANANA:
                return Textures.ROW_STRIPED_BANANA;
            default:
                throw new IllegalArgumentException("Row striped tile Texture not found!");
        }
    }

    private Texture getColumnStripedTileTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return Textures.COLUMN_STRIPED_CHERRY;
            case STRAWBERRY:
                return Textures.COLUMN_STRIPED_STRAWBERRY;
            case LEMON:
                return Textures.COLUMN_STRIPED_LEMON;
            case COCONUT:
                return Textures.COLUMN_STRIPED_COCONUT;
            case BANANA:
                return Textures.COLUMN_STRIPED_BANANA;
            default:
                throw new IllegalArgumentException("Column striped tile Texture not found!");
        }
    }

    private Texture getExplosiveTileTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return Textures.EXPLOSIVE_CHERRY;
            case STRAWBERRY:
                return Textures.EXPLOSIVE_STRAWBERRY;
            case LEMON:
                return Textures.EXPLOSIVE_LEMON;
            case COCONUT:
                return Textures.EXPLOSIVE_COCONUT;
            case BANANA:
                return Textures.EXPLOSIVE_BANANA;
            default:
                throw new IllegalArgumentException("Explosive tile Texture not found!");
        }
    }

    private Texture[] getRowStripedTilePiecesTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return new Texture[]{Textures.ROW_STRIPED_CHERRY_PIECE_01, Textures.ROW_STRIPED_CHERRY_PIECE_02};
            case STRAWBERRY:
                return new Texture[]{Textures.ROW_STRIPED_STRAWBERRY_PIECE_01, Textures.ROW_STRIPED_STRAWBERRY_PIECE_02};
            case LEMON:
                return new Texture[]{Textures.ROW_STRIPED_LEMON_PIECE_01, Textures.ROW_STRIPED_LEMON_PIECE_02};
            case COCONUT:
                return new Texture[]{Textures.ROW_STRIPED_COCONUT_PIECE_01, Textures.ROW_STRIPED_COCONUT_PIECE_02};
            case BANANA:
                return new Texture[]{Textures.ROW_STRIPED_BANANA_PIECE_01, Textures.ROW_STRIPED_BANANA_PIECE_02};
            default:
                throw new IllegalArgumentException("Row striped tile pieces Texture not found!");
        }
    }

    private Texture[] getColumnStripedTilePiecesTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return new Texture[]{Textures.COLUMN_STRIPED_CHERRY_PIECE_01, Textures.COLUMN_STRIPED_CHERRY_PIECE_02};
            case STRAWBERRY:
                return new Texture[]{Textures.COLUMN_STRIPED_STRAWBERRY_PIECE_01, Textures.COLUMN_STRIPED_STRAWBERRY_PIECE_02};
            case LEMON:
                return new Texture[]{Textures.COLUMN_STRIPED_LEMON_PIECE_01, Textures.COLUMN_STRIPED_LEMON_PIECE_02};
            case COCONUT:
                return new Texture[]{Textures.COLUMN_STRIPED_COCONUT_PIECE_01, Textures.COLUMN_STRIPED_COCONUT_PIECE_02};
            case BANANA:
                return new Texture[]{Textures.COLUMN_STRIPED_BANANA_PIECE_01, Textures.COLUMN_STRIPED_BANANA_PIECE_02};
            default:
                throw new IllegalArgumentException("Column striped tile pieces Texture not found!");
        }
    }

    private Texture[] getExplosiveTilePiecesTexture(FruitType fruitType) {
        switch (fruitType) {
            case CHERRY:
                return new Texture[]{Textures.EXPLOSIVE_CHERRY_PIECE_01, Textures.EXPLOSIVE_CHERRY_PIECE_02};
            case STRAWBERRY:
                return new Texture[]{Textures.EXPLOSIVE_STRAWBERRY_PIECE_01, Textures.EXPLOSIVE_STRAWBERRY_PIECE_02};
            case LEMON:
                return new Texture[]{Textures.EXPLOSIVE_LEMON_PIECE_01, Textures.EXPLOSIVE_LEMON_PIECE_02};
            case COCONUT:
                return new Texture[]{Textures.EXPLOSIVE_COCONUT_PIECE_01, Textures.EXPLOSIVE_COCONUT_PIECE_02};
            case BANANA:
                return new Texture[]{Textures.EXPLOSIVE_BANANA_PIECE_01, Textures.EXPLOSIVE_BANANA_PIECE_02};
            default:
                throw new IllegalArgumentException("Explosive tile pieces Texture not found!");
        }
    }
    //========================================================

}
