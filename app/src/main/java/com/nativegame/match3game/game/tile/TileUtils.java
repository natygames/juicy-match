package com.nativegame.match3game.game.tile;

import com.nativegame.match3game.R;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TileUtils {

    // Fruit ID
    public static final int[] FRUITS = {
            R.drawable.coconut,
            R.drawable.strawberry,
            R.drawable.cherry,
            R.drawable.lemon,
            R.drawable.banana
    };

    // Horizontal special fruit ID
    public static final int[] SPECIAL_FRUITS_H = {
            R.drawable.speci_h_coconut,
            R.drawable.speci_h_strawberry,
            R.drawable.speci_h_cherry,
            R.drawable.speci_h_lemon,
            R.drawable.speci_h_banana,
    };

    // Vertical special fruit ID
    public static final int[] SPECIAL_FRUITS_V = {
            R.drawable.speci_v_coconut,
            R.drawable.speci_v_strawberry,
            R.drawable.speci_v_cherry,
            R.drawable.speci_v_lemon,
            R.drawable.speci_v_banana,
    };

    // Square special fruit ID
    public static final int[] SPECIAL_FRUITS_S = {
            R.drawable.speci_s_coconut,
            R.drawable.speci_s_strawberry,
            R.drawable.speci_s_cherry,
            R.drawable.speci_s_lemon,
            R.drawable.speci_s_banana,
    };

    // Chosen fruit ID
    public static final int[] FRUITS_CHOSEN = {
            R.drawable.coconut_chosen,
            R.drawable.strawberry_chosen,
            R.drawable.cherry_chosen,
            R.drawable.lemon_chosen,
            R.drawable.banana_chosen
    };

    // Chosen horizontal special fruit ID
    public static final int[] SPECIAL_FRUITS_H_CHOSEN = {
            R.drawable.speci_h_coconut_chosen,
            R.drawable.speci_h_strawberry_chosen,
            R.drawable.speci_h_cherry_chosen,
            R.drawable.speci_h_lemon_chosen,
            R.drawable.speci_h_banana_chosen
    };

    // Chosen vertical special fruit ID
    public static final int[] SPECIAL_FRUITS_V_CHOSEN = {
            R.drawable.speci_v_coconut_chosen,
            R.drawable.speci_v_strawberry_chosen,
            R.drawable.speci_v_cherry_chosen,
            R.drawable.speci_v_lemon_chosen,
            R.drawable.speci_v_banana_chosen
    };

    // Chosen square special fruit ID
    public static final int[] SPECIAL_FRUITS_S_CHOSEN = {
            R.drawable.speci_s_coconut_chosen,
            R.drawable.speci_s_strawberry_chosen,
            R.drawable.speci_s_cherry_chosen,
            R.drawable.speci_s_lemon_chosen,
            R.drawable.speci_s_banana_chosen
    };

    // Ice cream ID
    public static final int ICE_CREAM = R.drawable.icecream;
    public static final int ICE_CREAM_CHOSEN = R.drawable.icecream_chosen;

    // Cracker ID
    public static final int CRACKER = R.drawable.cracker;
    public static final int CRACKER_CHOSEN = R.drawable.cracker_chosen;

    // Cookie ID
    public static final int COOKIE = R.drawable.cookie;
    public static final int[] COOKIES = {
            R.drawable.cookie,
            R.drawable.cookie2,
            R.drawable.cookie3,
            R.drawable.cookie4
    };

    // Star fish ID
    public static final int STAR_FISH = R.drawable.starfish;
    public static final int STAR_FISH_CHOSEN = R.drawable.starfish_chosen;

    public static int getIndex(int id) {
        for (int i = 0; i < 5; i++) {
            if (FRUITS[i] == id)
                return i;
        }
        return -1;
    }

    public static int getFruit(Tile tile) {
        if (tile.kind == COOKIE) {
            return COOKIES[tile.layer];
        }
        return tile.kind;

    }

    public static int getSpecialFruit(Tile tile) {
        switch (tile.direct) {
            case 'I':
                return ICE_CREAM;
            case 'H':
                return SPECIAL_FRUITS_H[getIndex(tile.kind)];
            case 'V':
                return SPECIAL_FRUITS_V[getIndex(tile.kind)];
            case 'S':
                return SPECIAL_FRUITS_S[getIndex(tile.kind)];
        }

        return -1;
    }

    public static int getChosenFruit(Tile tile) {
        if (tile.special) {
            switch (tile.direct) {
                case 'I':
                    return ICE_CREAM_CHOSEN;
                case 'H':
                    return SPECIAL_FRUITS_H_CHOSEN[getIndex(tile.kind)];
                case 'V':
                    return SPECIAL_FRUITS_V_CHOSEN[getIndex(tile.kind)];
                case 'S':
                    return SPECIAL_FRUITS_S_CHOSEN[getIndex(tile.kind)];
            }
        } else {
            switch (tile.kind) {
                case CRACKER:
                    return CRACKER_CHOSEN;
                case STAR_FISH:
                    return STAR_FISH_CHOSEN;
                default:
                    return FRUITS_CHOSEN[getIndex(tile.kind)];
            }
        }

        return -1;
    }

}
