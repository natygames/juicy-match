package com.nativegame.match3game.level;

import java.util.ArrayList;
import java.util.List;

public class LevelData {

    private final LevelType mLevelType;
    private final String mGrid;
    private final String mIce;
    private final String mTile;
    private final String mLock;
    private final String mEntry;
    private final String mGenerator;
    private final List<TargetType> mTargetTypes;
    private final List<Integer> mTargetNums;
    private final int mLevel;
    private final int mRow;
    private final int mColumn;
    private int mFruitNum;
    private int mMove;
    private int mScore = 0;
    private int mStar = 0;

    public LevelData(String levelType,
                     String grid,
                     String ice,
                     String tile,
                     String lock,
                     String entry,
                     String generator,
                     String targetType,
                     String targetNum,
                     int level,
                     int row,
                     int column,
                     int fruitNum,
                     int move) {
        mLevelType = getLevelType(levelType);
        mGrid = grid;
        mIce = ice;
        mTile = tile;
        mLock = lock;
        mEntry = entry;
        mGenerator = generator;
        mTargetTypes = getTargetTypes(targetType);
        mTargetNums = getTargetNums(targetNum);
        mLevel = level;
        mRow = row;
        mColumn = column;
        mFruitNum = fruitNum;
        mMove = move;
    }

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public LevelType getLevelType() {
        return mLevelType;
    }

    public String getGrid() {
        return mGrid;
    }

    public String getIce() {
        return mIce;
    }

    public String getTile() {
        return mTile;
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

    public List<TargetType> getTargetTypes() {
        return mTargetTypes;
    }

    public List<Integer> getTargetNums() {
        return mTargetNums;
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

    public int getFruitNum() {
        return mFruitNum;
    }

    public void setFruitNum(int fruitNum) {
        mFruitNum = fruitNum;
    }

    public int getMove() {
        return mMove;
    }

    public void setMove(int move) {
        mMove = move;
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
    private LevelType getLevelType(String text) {
        switch (text) {
            case "collect":
                return LevelType.COLLECT;
            case "ice":
                return LevelType.ICE;
            case "starfish":
                return LevelType.STARFISH;
            default:
                throw new IllegalArgumentException("LevelType not found!");
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
                    case ("striped"):
                        type = TargetType.STRIPED;
                        break;
                    case ("cookie"):
                        type = TargetType.COOKIE;
                        break;
                    case ("cake"):
                        type = TargetType.CAKE;
                        break;
                    case ("ice"):
                        type = TargetType.ICE;
                        break;
                    case ("starfish"):
                        type = TargetType.STARFISH;
                        break;
                }
                targetTypes.add(type);

                preIndex = i + 1;
            }
        }

        return targetTypes;
    }

    private List<Integer> getTargetNums(String text) {
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
