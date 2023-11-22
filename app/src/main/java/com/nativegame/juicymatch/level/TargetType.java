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
                return R.drawable.ui_target_strawberry;
            case CHERRY:
                return R.drawable.ui_target_cherry;
            case LEMON:
                return R.drawable.ui_target_lemon;
            case LOCK:
                return R.drawable.ui_target_lock;
            case COOKIE:
                return R.drawable.ui_target_cookie;
            case CAKE:
                return R.drawable.ui_target_cake;
            case CANDY:
                return R.drawable.ui_target_candy;
            case PIE:
                return R.drawable.ui_target_pie;
            case ICE:
                return R.drawable.ui_target_ice;
            case HONEY:
                return R.drawable.ui_target_honey;
            case STARFISH:
                return R.drawable.ui_target_starfish;
            case SHELL:
                return R.drawable.ui_target_shell;
            default:
                throw new IllegalArgumentException("TargetType drawable not found!");
        }
    }
    //========================================================

}
