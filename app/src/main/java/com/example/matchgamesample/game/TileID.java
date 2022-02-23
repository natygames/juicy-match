package com.example.matchgamesample.game;

import com.example.matchgamesample.R;

public class TileID {

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
    public static final int COOKIE_2 = R.drawable.cookie2;
    public static final int COOKIE_3 = R.drawable.cookie3;
    public static final int COOKIE_4 = R.drawable.cookie4;

    // Pie ID
    public static final int PIE_1 = R.drawable.pie1_1;
    public static final int PIE_2 = R.drawable.pie1_2;
    public static final int PIE_3 = R.drawable.pie1_3;
    public static final int PIE_4 = R.drawable.pie1_4;

    // Star fish ID
    public static final int STAR_FISH = R.drawable.starfish;
    public static final int STAR_FISH_CHOSEN = R.drawable.starfish_chosen;

    // Fruit ID
    public static int[] FRUITS_TO_USE;

    public static int getFruit(Tile tile) {
        if (tile.kind == TileID.COOKIE) {
            switch (tile.layer) {
                case 0:
                    return TileID.COOKIE;
                case 1:
                    return TileID.COOKIE_2;
                case 2:
                    return TileID.COOKIE_3;
                case 3:
                    return TileID.COOKIE_4;
            }
        } else if (tile.kind == TileID.PIE_1) {
            switch (tile.layer) {
                case 0:
                    return R.drawable.pie1_1;
                case 1:
                    return R.drawable.pie2_1;
                case 2:
                    return R.drawable.pie3_1;
                case 3:
                    return R.drawable.pie4_1;
                case 4:
                    return R.drawable.pie5_1;
            }
        } else if (tile.kind == TileID.PIE_2) {
            switch (tile.layer) {
                case 0:
                    return R.drawable.pie1_2;
                case 1:
                    return R.drawable.pie2_2;
                case 2:
                    return R.drawable.pie3_2;
                case 3:
                    return R.drawable.pie4_2;
                case 4:
                    return R.drawable.pie5_2;
            }
        } else if (tile.kind == TileID.PIE_3) {
            switch (tile.layer) {
                case 0:
                    return R.drawable.pie1_3;
                case 1:
                    return R.drawable.pie2_3;
                case 2:
                    return R.drawable.pie3_3;
                case 3:
                    return R.drawable.pie4_3;
                case 4:
                    return R.drawable.pie5_3;
            }
        } else if (tile.kind == TileID.PIE_4) {
            switch (tile.layer) {
                case 0:
                    return R.drawable.pie1_4;
                case 1:
                    return R.drawable.pie2_4;
                case 2:
                    return R.drawable.pie3_4;
                case 3:
                    return R.drawable.pie4_4;
                case 4:
                    return R.drawable.pie5_4;
            }
        } else {
            return tile.kind;
        }

        return 0;
    }

    public static int getSpecialFruit(Tile tile) {
        if (tile.direct == 'H') {
            if (tile.kind == TileID.FRUITS[0]) {
                return TileID.SPECIAL_FRUITS_H[0];
            } else if (tile.kind == TileID.FRUITS[1]) {
                return TileID.SPECIAL_FRUITS_H[1];
            } else if (tile.kind == TileID.FRUITS[2]) {
                return TileID.SPECIAL_FRUITS_H[2];
            } else if (tile.kind == TileID.FRUITS[3]) {
                return TileID.SPECIAL_FRUITS_H[3];
            } else if (tile.kind == TileID.FRUITS[4]) {
                return TileID.SPECIAL_FRUITS_H[4];
            }
        } else if (tile.direct == 'V') {
            if (tile.kind == TileID.FRUITS[0]) {
                return TileID.SPECIAL_FRUITS_V[0];
            } else if (tile.kind == TileID.FRUITS[1]) {
                return TileID.SPECIAL_FRUITS_V[1];
            } else if (tile.kind == TileID.FRUITS[2]) {
                return TileID.SPECIAL_FRUITS_V[2];
            } else if (tile.kind == TileID.FRUITS[3]) {
                return TileID.SPECIAL_FRUITS_V[3];
            } else if (tile.kind == TileID.FRUITS[4]) {
                return TileID.SPECIAL_FRUITS_V[4];
            }
        } else if (tile.direct == 'S') {
            if (tile.kind == TileID.FRUITS[0]) {
                return TileID.SPECIAL_FRUITS_S[0];
            } else if (tile.kind == TileID.FRUITS[1]) {
                return TileID.SPECIAL_FRUITS_S[1];
            } else if (tile.kind == TileID.FRUITS[2]) {
                return TileID.SPECIAL_FRUITS_S[2];
            } else if (tile.kind == TileID.FRUITS[3]) {
                return TileID.SPECIAL_FRUITS_S[3];
            } else if (tile.kind == TileID.FRUITS[4]) {
                return TileID.SPECIAL_FRUITS_S[4];
            }
        } else if (tile.direct == 'I') {
            return TileID.ICE_CREAM;
        }
        return 0;
    }

    public static int getChosenFruit(Tile tile) {
        if (tile.special) {
            if (tile.direct == 'H') {
                if (tile.kind == TileID.FRUITS[0]) {
                    return TileID.SPECIAL_FRUITS_H_CHOSEN[0];
                } else if (tile.kind == TileID.FRUITS[1]) {
                    return TileID.SPECIAL_FRUITS_H_CHOSEN[1];
                } else if (tile.kind == TileID.FRUITS[2]) {
                    return TileID.SPECIAL_FRUITS_H_CHOSEN[2];
                } else if (tile.kind == TileID.FRUITS[3]) {
                    return TileID.SPECIAL_FRUITS_H_CHOSEN[3];
                } else if (tile.kind == TileID.FRUITS[4]) {
                    return TileID.SPECIAL_FRUITS_H_CHOSEN[4];
                }
            } else if (tile.direct == 'V') {
                if (tile.kind == TileID.FRUITS[0]) {
                    return TileID.SPECIAL_FRUITS_V_CHOSEN[0];
                } else if (tile.kind == TileID.FRUITS[1]) {
                    return TileID.SPECIAL_FRUITS_V_CHOSEN[1];
                } else if (tile.kind == TileID.FRUITS[2]) {
                    return TileID.SPECIAL_FRUITS_V_CHOSEN[2];
                } else if (tile.kind == TileID.FRUITS[3]) {
                    return TileID.SPECIAL_FRUITS_V_CHOSEN[3];
                } else if (tile.kind == TileID.FRUITS[4]) {
                    return TileID.SPECIAL_FRUITS_V_CHOSEN[4];
                }
            } else if (tile.direct == 'S') {
                if (tile.kind == TileID.FRUITS[0]) {
                    return TileID.SPECIAL_FRUITS_S_CHOSEN[0];
                } else if (tile.kind == TileID.FRUITS[1]) {
                    return TileID.SPECIAL_FRUITS_S_CHOSEN[1];
                } else if (tile.kind == TileID.FRUITS[2]) {
                    return TileID.SPECIAL_FRUITS_S_CHOSEN[2];
                } else if (tile.kind == TileID.FRUITS[3]) {
                    return TileID.SPECIAL_FRUITS_S_CHOSEN[3];
                } else if (tile.kind == TileID.FRUITS[4]) {
                    return TileID.SPECIAL_FRUITS_S_CHOSEN[4];
                }
            } else if (tile.direct == 'I') {
                return TileID.ICE_CREAM_CHOSEN;
            }
        } else {
            if (tile.kind == TileID.CRACKER) {
                return TileID.CRACKER_CHOSEN;
            } else if (tile.kind == TileID.STAR_FISH) {
                return TileID.STAR_FISH_CHOSEN;
            } else if (tile.kind == TileID.FRUITS[0]) {
                return TileID.FRUITS_CHOSEN[0];
            } else if (tile.kind == TileID.FRUITS[1]) {
                return TileID.FRUITS_CHOSEN[1];
            } else if (tile.kind == TileID.FRUITS[2]) {
                return TileID.FRUITS_CHOSEN[2];
            } else if (tile.kind == TileID.FRUITS[3]) {
                return TileID.FRUITS_CHOSEN[3];
            } else if (tile.kind == TileID.FRUITS[4]) {
                return TileID.FRUITS_CHOSEN[4];
            }
        }
        return 0;
    }

    public static boolean isFruit(int kind){
        int size = FRUITS.length;
        for(int i = 0; i < size; i ++){
            if(kind == FRUITS[i])
                return true;
        }

        return false;
    }
}
