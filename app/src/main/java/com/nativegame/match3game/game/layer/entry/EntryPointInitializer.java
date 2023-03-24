package com.nativegame.match3game.game.layer.entry;

public class EntryPointInitializer {

    //--------------------------------------------------------
    // Getter and Setter
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
