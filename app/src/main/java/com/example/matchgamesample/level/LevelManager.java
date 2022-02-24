package com.example.matchgamesample.level;

import android.content.Context;
import com.example.matchgamesample.game.TileID;

/* This class let you design mLevel
 * Assign mLevel data with Level class
 * Use getmLevel() to get data
 */

public class LevelManager {
    //Current mLevel
    private Level mLevel;
    private XMLPuller mXMLPuller;


    public LevelManager(Context context) {
        mXMLPuller = new XMLPuller(context);
    }

    public Level getLevel(int level) {
        mLevel = mXMLPuller.getLevel(level);
        return this.mLevel;
    }

    public void init() {
        // Set fruitID array
        int size = mLevel.fruitNum;
        if(size == 0){
            // Default fruit count is 4
            size = 4;
        }
        TileID.FRUITS_TO_USE = new int[size];
        System.arraycopy(TileID.FRUITS, 0, TileID.FRUITS_TO_USE, 0, size);
    }


}
