package com.example.matchgamesample.level;

import android.content.Context;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * A helper class to access level data
 */

public class LevelManager {
    private final XMLPuller mXMLPuller;

    public LevelManager(Context context) {
        mXMLPuller = new XMLPuller(context);
    }

    public Level getLevel(int level) {
        return mXMLPuller.getLevel(level);
    }

}
