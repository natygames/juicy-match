package com.example.matchgamesample.level;

import com.example.matchgamesample.R;

import java.util.ArrayList;

/** This class stores variables of each level
 *  First, Data class will pull data from XML file
 *  Then, LevelManager will assign to each variable in Level
 *  Our game will get a Level object to access level data
**/

public class Level {
    public int targetType = 0;
    public int move = 0;
    public int column = 0, row = 0;
    public int fruitNum = 4;
    public ArrayList<Integer> target = new ArrayList<>();
    public ArrayList<Integer> collect = new ArrayList<>();
    // Game board in char
    public String board, fruit, ice, advance;

    public void addCollect(String s){
            switch (s) {
                case ("strawberry"):
                    collect.add(R.drawable.strawberry);
                    break;
                case ("cherry"):
                    collect.add(R.drawable.cherry);
                    break;
                case ("lemon"):
                    collect.add(R.drawable.lemon);
                    break;
                case ("striped"):
                    collect.add(R.drawable.striped_ball);
                    break;
                case ("cracker"):
                    collect.add(R.drawable.cracker);
                    break;
                case ("cookie"):
                    collect.add(R.drawable.cookie);
                    break;
                case ("starfish"):
                    collect.add(R.drawable.starfish);
                    break;
                case ("pie"):
                    collect.add(R.drawable.pie1_1);
                    break;
                case ("ice"):
                    // We don't check ice this way
                    collect.add(0);
                    break;
            }

    }

    /* Explanation:
     * <targetType> is the target type of level:
     *     1 for reach target score
     *     2 for collect items
     *     3 for clear ice
     *     4 for starfish
     * <move> is the maxim swaps
     * <fruitNum> is the number of fruit, default 4, maxim 5
     * <target> is the list stores each target amount
     * <collect> is the list stores items need to be collect
     */
}
