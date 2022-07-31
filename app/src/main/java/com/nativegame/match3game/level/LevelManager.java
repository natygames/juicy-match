package com.nativegame.match3game.level;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * A helper class to pull level data
 * from a local XML file (assets/data)
 * to Level
 */

public class LevelManager {

    private static final String FILE_NAME = "data.xml";
    private static final String FILE_TAG = "level";

    private String mLevelTagName;

    private final Context mContext;
    private Level mLevel;

    public LevelManager(Context context) {
        mContext = context;
        // Use Xml.newPullParser()
        // https://developer.android.com/training/basics/network-ops/xml
    }

    public Level getLevel(int level) {
        mLevelTagName = "level" + level;
        mLevel = new Level(level);
        // Open file
        try {
            InputStream file = mContext.getAssets().open(FILE_NAME);
            parse(file);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        return mLevel;
    }

    private void parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            read(parser);
        } finally {
            in.close();
        }
    }

    private void read(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, FILE_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the level tag
            if (name.equals(mLevelTagName)) {
                readLevel(parser);
            } else {
                skip(parser);
            }
        }
    }

    private void readLevel(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, mLevelTagName);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the level info tag
            setLevelInfo(name, parser.nextText());   // We pass in tag name and text
        }
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private void setLevelInfo(String name, String text) {
        switch (name) {
            case ("target_type"):
                mLevel.setLevelType(text);
                break;
            case ("move"):
                mLevel.mMove = Integer.parseInt(text);
                break;
            case ("fruit_num"):
                mLevel.mFruitNum = Integer.parseInt(text);
                break;
            case ("column"):
                mLevel.mColumn = Integer.parseInt(text);
                break;
            case ("row"):
                mLevel.mRow = Integer.parseInt(text);
                break;
            case ("target"):
                mLevel.addTarget(Integer.parseInt(text));
                break;
            case ("collect"):
                mLevel.addCollect(text);
                break;
            case ("board"):
                mLevel.board = text;
                break;
            case ("fruit"):
                mLevel.fruit = text;
                break;
            case ("ice"):
                mLevel.ice = text;
                break;
            case ("ad"):
                mLevel.advance = text;
                break;
        }
    }

}
