package com.nativegame.match3game.level;

import android.content.Context;

import com.nativegame.nattyengine.util.storage.xml.XMLOpenHelper;
import com.nativegame.nattyengine.util.storage.xml.XMLResultSet;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class Level {

    private static final String FILE_NAME = "level.xml";

    public static LevelData LEVEL_DATA;

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static void load(Context context, int level) {
        // Read level data from xml
        XMLOpenHelper helper = XMLOpenHelper.getInstance();
        try {
            InputStream in = context.getAssets().open(FILE_NAME);
            XMLResultSet resultSet = helper.open(context, in, "level" + level);
            LEVEL_DATA = new LevelData(
                    resultSet.getString("level_type"),
                    resultSet.getString("grid"),
                    resultSet.getString("ices"),
                    resultSet.getString("tile"),
                    resultSet.getString("lock"),
                    resultSet.getString("entr"),
                    resultSet.getString("gene"),
                    resultSet.getString("target_type"),
                    resultSet.getString("target_num"),
                    level,
                    resultSet.getInt("row"),
                    resultSet.getInt("column"),
                    resultSet.getInt("fruit_num"),
                    resultSet.getInt("move"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
    //========================================================

}
