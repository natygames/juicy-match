package com.nativegame.juicymatch.level;

import com.nativegame.juicymatch.R;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum TargetType {
    STRAWBERRY,
    CHERRY,
    LEMON,
    LOCK,
    COOKIE,
    CAKE,
    CANDY,
    PIE,
    ICE,
    HONEY,
    STARFISH,
    SHELL;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getDrawableId() {
        switch (this) {
            case STRAWBERRY:
                return R.drawable.strawberry;
            case CHERRY:
                return R.drawable.cherry;
            case LEMON:
                return R.drawable.lemon;
            case LOCK:
                return R.drawable.lock;
            case COOKIE:
                return R.drawable.cookie;
            case CAKE:
                return R.drawable.cake_01;
            case CANDY:
                return R.drawable.candy_02;
            case PIE:
                return R.drawable.pie_04;
            case ICE:
                return R.drawable.ice_center_01;
            case HONEY:
                return R.drawable.honey_center;
            case STARFISH:
                return R.drawable.starfish;
            case SHELL:
                return R.drawable.shell_02;
            default:
                throw new IllegalArgumentException("TargetType drawable not found!");
        }
    }
    //========================================================

}
