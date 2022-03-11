package com.example.matchgamesample.level;

import android.content.Context;

public class LevelManager {
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
