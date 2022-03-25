package com.example.matchgamesample.level;

import com.example.matchgamesample.R;

import java.util.ArrayList;

public class Level {

    public int mLevel;
    public int mStar;
    public LevelType mLevelType;
    public int mMove;
    public int mScore;
    public int mColumn, mRow;
    public int mFruitNum = 4;

    public ArrayList<Integer> mTarget = new ArrayList<>();
    public ArrayList<Integer> mCollect = new ArrayList<>();

    // Game board in char
    public String board, fruit, ice, advance;

    public void setLevelType(int type) {
        switch (type) {
            case 1:
                mLevelType = LevelType.LEVEL_TYPE_SCORE;
                break;
            case 2:
                mLevelType = LevelType.LEVEL_TYPE_COLLECT;
                break;
            case 3:
                mLevelType = LevelType.LEVEL_TYPE_ICE;
                break;
            case 4:
                mLevelType = LevelType.LEVEL_TYPE_STARFISH;
                break;
        }
    }

    public void addTarget(int target) {
        mTarget.add(target);
    }

    public void addCollect(String s) {
        switch (s) {
            case ("strawberry"):
                mCollect.add(R.drawable.strawberry);
                break;
            case ("cherry"):
                mCollect.add(R.drawable.cherry);
                break;
            case ("lemon"):
                mCollect.add(R.drawable.lemon);
                break;
            case ("striped"):
                mCollect.add(R.drawable.striped_ball);
                break;
            case ("cracker"):
                mCollect.add(R.drawable.cracker);
                break;
            case ("cookie"):
                mCollect.add(R.drawable.cookie);
                break;
            case ("starfish"):
                mCollect.add(R.drawable.starfish);
                break;
            case ("ice"):
                mCollect.add(R.drawable.ice);
                break;
        }

    }

    /* Explanation:
     * <mLevelType> is the mTarget type of level:
     *     1 for reach mTarget score
     *     2 for mCollect items
     *     3 for clear ice
     *     4 for starfish
     * <mMove> is the maxim swaps
     * <mFruitNum> is the number of fruit, default 4, maxim 5
     * <mTarget> is the list stores each mTarget amount
     * <mCollect> is the list stores items need to be mCollect
     */
}
