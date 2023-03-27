package com.nativegame.match3game.game.layer.entry;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class EntryPointInitializer {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static EntryPointType getType(char c) {
        switch (c) {
            case 'A':
                return EntryPointType.ENTRY;
            default:
                throw new IllegalArgumentException("EntryPointType not found!");
        }
    }
    //========================================================

}
