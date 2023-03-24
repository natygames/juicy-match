package com.nativegame.match3game.level;

import com.nativegame.match3game.R;

public enum TargetType {
    STRAWBERRY,
    CHERRY,
    LEMON,
    STRIPED,
    COOKIE,
    CAKE,
    ICE,
    STARFISH;

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
            case STRIPED:
                return R.drawable.striped_ball;
            case COOKIE:
                return R.drawable.cookie;
            case CAKE:
                return R.drawable.cake_01;
            case ICE:
                return R.drawable.ice_center_01;
            case STARFISH:
                return R.drawable.starfish;
            default:
                throw new IllegalArgumentException("TargetType drawable not found!");
        }
    }
    //========================================================

}
