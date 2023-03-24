package com.nativegame.match3game.level;

import com.nativegame.match3game.R;

public enum LevelType {
    COLLECT,
    ICE,
    STARFISH;

    public int getDrawableId() {
        switch (this) {
            case COLLECT:
                return R.drawable.text_target_collect;
            case ICE:
                return R.drawable.text_target_ice;
            case STARFISH:
                return R.drawable.text_target_collect;
            default:
                throw new IllegalArgumentException("Level target drawable not found!");
        }
    }

}
