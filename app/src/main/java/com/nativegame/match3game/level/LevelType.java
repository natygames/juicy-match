package com.nativegame.match3game.level;

import com.nativegame.match3game.R;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum LevelType {
    COLLECT,
    ICE,
    STARFISH;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getDrawableId() {
        switch (this) {
            case COLLECT:
                return R.drawable.text_target_collect;
            case ICE:
                return R.drawable.text_target_ice;
            case STARFISH:
                return R.drawable.text_target_starfish;
            default:
                throw new IllegalArgumentException("Level target drawable not found!");
        }
    }
    //========================================================

}
