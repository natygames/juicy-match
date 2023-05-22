package com.nativegame.juicymatch.level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LevelData {

    private final String mGrid;
    private final String mIce;
    private final String mTile;
    private final String mLock;
    private final String mEntry;
    private final String mHoney;
    private final String mSand;
    private final String mShell;
    private final String mGenerator;
    private final String mTutorialHint;
    private final TutorialType mTutorialType;
    private final List<TargetType> mTargetTypes;
    private final List<Integer> mTargetCounts;
    private final int mLevel;
    private final int mRow;
    private final int mColumn;

    private int mMove;
    private int mFruitCount;
    private int mScore = 0;
    private int mStar = 0;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LevelData(int level,
                     int row,
                     int column,
                     int move,
                     int fruitCount,
                     String grid,
                     String tile,
                     String ice,
                     String honey,
                     String sand,
                     String shell,
                     String lock,
                     String entry,
                     String generator,
                     String tutorialHint,
                     String tutorialType,
                     String targetType,
                     String targetCount) {
        mLevel = level;
        mRow = row;
        mColumn = column;
        mMove = move;
        mFruitCount = fruitCount;
        mGrid = grid;
        mTile = tile;
        mIce = ice;
        mHoney = honey;
        mSand = sand;
        mShell = shell;
        mLock = lock;
        mEntry = entry;
        mGenerator = generator;
        mTutorialHint = tutorialHint;
        mTutorialType = getTutorialType(tutorialType);
        mTargetTypes = getTargetTypes(targetType);
        mTargetCounts = getTargetCounts(targetCount);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public String getGrid() {
        return mGrid;
    }

    public String getTile() {
        return mTile;
    }

    public String getIce() {
        return mIce;
    }

    public String getHoney() {
        return mHoney;
    }

    public String getSand() {
        return mSand;
    }

    public String getShell() {
        return mShell;
    }

    public String getLock() {
        return mLock;
    }

    public String getEntry() {
        return mEntry;
    }

    public String getGenerator() {
        return mGenerator;
    }

    public String getTutorialHint() {
        return mTutorialHint;
    }

    public TutorialType getTutorialType() {
        return mTutorialType;
    }

    public List<TargetType> getTargetTypes() {
        return mTargetTypes;
    }

    public List<Integer> getTargetCounts() {
        return mTargetCounts;
    }

    public int getLevel() {
        return mLevel;
    }

    public int getRow() {
        return mRow;
    }

    public int getColumn() {
        return mColumn;
    }

    public int getMove() {
        return mMove;
    }

    public void setMove(int move) {
        mMove = move;
    }

    public int getFruitCount() {
        return mFruitCount;
    }

    public void setFruitCount(int fruitCount) {
        mFruitCount = fruitCount;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public int getStar() {
        return mStar;
    }

    public void setStar(int star) {
        mStar = star;
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private TutorialType getTutorialType(String text) {
        if (text == null) {
            // If text is null means no tutorial in current level
            return TutorialType.NONE;
        }

        switch (text) {
            case "match_3":
                return TutorialType.MATCH_3;
            case "match_4":
                return TutorialType.MATCH_4;
            case "match_t":
                return TutorialType.MATCH_T;
            case "match_l":
                return TutorialType.MATCH_L;
            case "match_5":
                return TutorialType.MATCH_5;
            case "combine":
                return TutorialType.COMBINE;
            case "lock":
                return TutorialType.LOCK;
            case "cookie":
                return TutorialType.COOKIE;
            case "cake":
                return TutorialType.CAKE;
            case "candy":
                return TutorialType.CANDY;
            case "pie":
                return TutorialType.PIE;
            case "ice":
                return TutorialType.ICE;
            case "honey":
                return TutorialType.HONEY;
            case "starfish":
                return TutorialType.STARFISH;
            case "shell":
                return TutorialType.SHELL;
            case "pipe":
                return TutorialType.PIPE;
            case "generator":
                return TutorialType.GENERATOR;
            case "hammer":
                return TutorialType.HAMMER;
            case "bomb":
                return TutorialType.BOMB;
            case "glove":
                return TutorialType.GLOVE;
            default:
                throw new IllegalArgumentException("TutorialType not found!");
        }
    }

    private List<TargetType> getTargetTypes(String text) {
        List<TargetType> targetTypes = new ArrayList<>();
        text = text + " ";
        int preIndex = 0;
        int size = text.length();
        for (int i = 0; i < size; i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                String s = text.substring(preIndex, i);
                TargetType type = null;
                switch (s) {
                    case ("strawberry"):
                        type = TargetType.STRAWBERRY;
                        break;
                    case ("cherry"):
                        type = TargetType.CHERRY;
                        break;
                    case ("lemon"):
                        type = TargetType.LEMON;
                        break;
                    case ("cookie"):
                        type = TargetType.COOKIE;
                        break;
                    case ("cake"):
                        type = TargetType.CAKE;
                        break;
                    case ("pie"):
                        type = TargetType.PIE;
                        break;
                    case ("candy"):
                        type = TargetType.CANDY;
                        break;
                    case ("ice"):
                        type = TargetType.ICE;
                        break;
                    case ("lock"):
                        type = TargetType.LOCK;
                        break;
                    case ("starfish"):
                        type = TargetType.STARFISH;
                        break;
                    case ("shell"):
                        type = TargetType.SHELL;
                        break;
                    case ("honey"):
                        type = TargetType.HONEY;
                        break;
                }
                targetTypes.add(type);

                preIndex = i + 1;
            }
        }

        return targetTypes;
    }

    private List<Integer> getTargetCounts(String text) {
        List<Integer> targetNums = new ArrayList<>();
        text = text + " ";
        int preIndex = 0;
        int size = text.length();
        for (int i = 0; i < size; i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                String s = text.substring(preIndex, i);
                targetNums.add(Integer.parseInt(s));
                preIndex = i + 1;
            }
        }

        return targetNums;
    }
    //========================================================

}
