package com.nativegame.juicymatch.game.layer.tile;

import com.nativegame.juicymatch.algorithm.TileType;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum FruitType implements TileType {
    CHERRY,
    STRAWBERRY,
    LEMON,
    COCONUT,
    BANANA,
    NONE;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public boolean hasEffect() {
        return this != NONE;
    }

    public Texture getTexture() {
        switch (this) {
            case CHERRY:
                return Textures.CHERRY;
            case STRAWBERRY:
                return Textures.STRAWBERRY;
            case LEMON:
                return Textures.LEMON;
            case COCONUT:
                return Textures.COCONUT;
            case BANANA:
                return Textures.BANANA;
            default:
                throw new IllegalArgumentException("Fruit Texture not found!");
        }
    }

    public Texture[] getPiecesTexture() {
        switch (this) {
            case CHERRY:
                return new Texture[]{Textures.CHERRY_PIECE_01, Textures.CHERRY_PIECE_02};
            case STRAWBERRY:
                return new Texture[]{Textures.STRAWBERRY_PIECE_01, Textures.STRAWBERRY_PIECE_02};
            case LEMON:
                return new Texture[]{Textures.LEMON_PIECE_01, Textures.LEMON_PIECE_02};
            case COCONUT:
                return new Texture[]{Textures.COCONUT_PIECE_01, Textures.COCONUT_PIECE_02};
            case BANANA:
                return new Texture[]{Textures.BANANA_PIECE_01, Textures.BANANA_PIECE_02};
            default:
                throw new IllegalArgumentException("Fruit pieces Texture not found!");
        }
    }

    public Texture getScoreTexture() {
        switch (this) {
            case CHERRY:
                return Textures.SCORE_PINK;
            case STRAWBERRY:
                return Textures.SCORE_RED;
            case LEMON:
                return Textures.SCORE_YELLOW;
            case COCONUT:
                return Textures.SCORE_BROWN;
            case BANANA:
                return Textures.SCORE_WHITE;
            default:
                throw new IllegalArgumentException("Fruit score Texture not found!");
        }
    }
    //========================================================

}
