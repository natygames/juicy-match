package com.nativegame.juicymatch.level;

import android.content.Context;


import com.nativegame.natyengine.util.storage.xml.XMLOpenHelper;
import com.nativegame.natyengine.util.storage.xml.XMLResultSet;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Oscar Liang on 2022/02/23
 */

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
                    level,
                    resultSet.getInt("row"),
                    resultSet.getInt("column"),
                    resultSet.getInt("move"),
                    resultSet.getInt("fruit_count"),
                    resultSet.getString("grid"),
                    resultSet.getString("tile"),
                    resultSet.getString("ices"),
                    resultSet.getString("hone"),
                    resultSet.getString("sand"),
                    resultSet.getString("shel"),
                    resultSet.getString("lock"),
                    resultSet.getString("entr"),
                    resultSet.getString("gene"),
                    resultSet.getString("tuto"),
                    resultSet.getString("tutorial_type"),
                    resultSet.getString("target_type"),
                    resultSet.getString("target_count"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
    //========================================================

}
