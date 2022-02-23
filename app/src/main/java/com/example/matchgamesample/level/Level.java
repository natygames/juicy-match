package com.example.matchgamesample.level;

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
    public int fruitNum = 0;
    public ArrayList<Integer> target = new ArrayList<>();
    public ArrayList<Integer> collect = new ArrayList<>();
    // Game board in char
    public String board = "", fruit = "", ice = "", advance = "";

    /* Explanation:
     * <targetType> is the target type of level:
     *     1 for reach target score
     *     2 for collect items
     *     3 for clear ice
     *     4 for starfish
     * <move> is the maxim swaps
     * <fruitNum> is the number of fruit, maxim 5
     * <target> is the list stores each target amount
     * <collect> is the list stores items need to be collect
     */
}
