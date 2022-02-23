package com.example.matchgamesample.level;

import android.content.Context;

import com.example.matchgamesample.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XMLPuller {
    private static final String FILE_NAME = "data.xml";
    private final Context context;
    private Level level;
    private final String currentLevel, nextLevel;
    private final ArrayList<String> collectBuffer = new ArrayList<>();

    public XMLPuller(Context context, int currentLevel) {
        this.context = context;
        this.currentLevel = "level" + currentLevel;
        this.nextLevel = "level" + (currentLevel + 1);
        parseXML();
        deCodeCollect();
    }

    public Level getLevel() {
        return this.level;
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        InputStream file = null;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            file = context.getAssets().open(FILE_NAME);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(file, null);
            processParsing(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = null;
            if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();

                if (currentLevel.equals(tagName)) {
                    // Initialize level when finding currentLevel
                    this.level = new Level();
                } else if (nextLevel.equals(tagName)) {
                    // Break loop when finish assign level data
                    break;
                } else if (this.level != null) {
                    // Assign level data when found
                    switch (tagName) {
                        case ("target_type"):
                            level.targetType = Integer.parseInt(parser.nextText());
                            break;
                        case ("move"):
                            level.move = Integer.parseInt(parser.nextText());
                            break;
                        case ("fruit_num"):
                            level.fruitNum = Integer.parseInt(parser.nextText());
                            break;
                        case ("column"):
                            level.column = Integer.parseInt(parser.nextText());
                            break;
                        case ("row"):
                            level.row = Integer.parseInt(parser.nextText());
                            break;
                        case ("target"):
                            level.target.add(Integer.parseInt(parser.nextText()));
                            break;
                        case ("collect"):
                            this.collectBuffer.add(parser.nextText());
                            break;
                        case ("board"):
                            level.board = parser.nextText();
                            break;
                        case ("fruit"):
                            level.fruit = parser.nextText();
                            break;
                        case ("ice"):
                            level.ice = parser.nextText();
                            break;
                        case ("ad"):
                            level.advance = parser.nextText();
                            break;
                    }
                }
            }

            eventType = parser.next();
        }
    }

    private void deCodeCollect() {
        //Set collect fruit
        if (this.collectBuffer.size() > 0) {

            for (String i : this.collectBuffer) {
                switch (i) {
                    case ("strawberry"):
                        level.collect.add(R.drawable.strawberry);
                        break;
                    case ("cherry"):
                        level.collect.add(R.drawable.cherry);
                        break;
                    case ("lemon"):
                        level.collect.add(R.drawable.lemon);
                        break;
                    case ("striped"):
                        level.collect.add(R.drawable.striped_ball);
                        break;
                    case ("cracker"):
                        level.collect.add(R.drawable.cracker);
                        break;
                    case ("cookie"):
                        level.collect.add(R.drawable.cookie);
                        break;
                    case ("starfish"):
                        level.collect.add(R.drawable.starfish);
                        break;
                    case ("pie"):
                        level.collect.add(R.drawable.pie1_1);
                        break;
                    case ("ice"):
                        // We don't check ice this way
                        level.collect.add(0);
                        break;
                }
            }

        }
    }

}
