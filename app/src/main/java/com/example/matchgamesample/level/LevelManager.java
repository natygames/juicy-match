package com.example.matchgamesample.level;

import android.content.Context;
import com.example.matchgamesample.game.TileID;

/* This class let you design mLevel
 * Assign mLevel data with Level class
 * Use getLevel() to get data
 */

public class LevelManager {
    //Current mLevel
    private Level mLevel;
    private final XMLPuller mXMLPuller;

    public LevelManager(Context context) {
        mXMLPuller = new XMLPuller(context);
    }

    public Level getLevel(int level) {
        mLevel = mXMLPuller.getLevel(level);
        return mLevel;
    }

}
