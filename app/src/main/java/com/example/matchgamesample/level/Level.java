package com.example.matchgamesample.level;

import com.example.matchgamesample.R;

import java.util.ArrayList;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * Level contains level data we are going to
 * use in game, it will be retrieved from a
 * local XML file(assets/data), in which store
 * all the level data
 */

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

    public void setLevelType(String type) {
        switch (type) {
            case "score":
                mLevelType = LevelType.LEVEL_TYPE_SCORE;
                break;
            case "collect":
                mLevelType = LevelType.LEVEL_TYPE_COLLECT;
                break;
            case "ice":
                mLevelType = LevelType.LEVEL_TYPE_ICE;
                break;
            case "starfish":
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
     * <LevelType> is the mTarget type of level:
     *     1 for reach mTarget score
     *     2 for mCollect items
     *     3 for clear ice
     *     4 for starfish
     * <Move> is the maxim swaps
     * <FruitNum> is the number of fruit, default 4, maxim 5
     * <Target> is the list stores each mTarget amount
     * <Collect> is the list stores items need to be mCollect
     */
}
