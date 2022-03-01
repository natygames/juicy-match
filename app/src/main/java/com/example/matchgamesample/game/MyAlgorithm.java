package com.example.matchgamesample.game;

import com.example.matchgamesample.engine.GameEngine;

public class MyAlgorithm {
    private final int row, column;
    private final int tileSize;

    public MyAlgorithm(GameEngine gameEngine) {
        this.row = gameEngine.mLevel.row;
        this.column = gameEngine.mLevel.column;
        this.tileSize = gameEngine.mImageSize;
    }

    public void findMatch(Tile[][] tileArray) {
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row - 2; i++) {
                //Check state
                if (!tileArray[i][j].empty
                        && tileArray[i][j].wait == 0
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != TileID.STAR_FISH) {
                    //Check match 3 in column
                    if ((tileArray[i][j].kind == tileArray[i + 1][j].kind) &&
                            (tileArray[i][j].kind == tileArray[i + 2][j].kind)) {
                        //Add match and explode around
                        for (int n = 0; n <= 2; n++) {
                            tileArray[i + n][j].match++;
                            // explodeAround(tileMatrix[i + n][j]);
                        }

                    }
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column - 2; j++) {
                //Check state
                if (!tileArray[i][j].empty
                        && tileArray[i][j].wait == 0
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != TileID.STAR_FISH) {

                    //Check match 3 in row
                    if ((tileArray[i][j].kind == tileArray[i][j + 1].kind) &&
                            (tileArray[i][j].kind == tileArray[i][j + 2].kind)) {
                        //Add match and explode around
                        for (int n = 0; n <= 2; n++) {
                            tileArray[i][j + n].match++;
                            // explodeAround(tileMatrix[i][j + n]);
                        }

                    }
                }
            }
        }
    }

    public void tile2Top(Tile[][] tileArray) {
        for (int j = 0; j < column; j++) {
            for (int i = row - 1; i >= 0; i--) {
                //Check match
                if (tileArray[i][j].match != 0) {
                    //Swap tile
                    for (int n = i - 1; n >= 0; n--) {
                        //If empty tube do not swap
                        if (tileArray[n][j].match == 0 && !tileArray[n][j].tube) {

                            if (tileArray[n][j].invalid) {
                                tileArray[i][j].kind = 0;
                                tileArray[i][j].match = 0;
                                tileArray[i][j].wait = 1;
                                break;
                            }

                            swap(tileArray, tileArray[n][j], tileArray[i][j]);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void tileReset(Tile[][] tileArray) {
        for (int j = 0; j < column; j++) {
            for (int i = row - 1, n = 1; i >= 0; i--) {

                // Breakable may cause 0 match
                if (tileArray[i][j].isAnimate) {
                    tileArray[i][j].isAnimate = false;
                }

                if (tileArray[i][j].match != 0) {
                    // Go to top
                    tileArray[i][j].y = -tileSize * n++;
                    tileArray[i][j].x = tileArray[i][j].col * tileSize;
                    // Reset fruit
                    tileArray[i][j].match = 0;
                    if (tileArray[i][j].special) {
                        tileArray[i][j].special = false;
                        tileArray[i][j].direct = 'N';
                        tileArray[i][j].specialCombine = 'N';
                        tileArray[i][j].iceCreamTarget = 0;
                    }
                    if (tileArray[i][j].isUpgrade) {
                        tileArray[i][j].isUpgrade = false;
                    }
                    if (tileArray[i][j].breakable) {
                        tileArray[i][j].breakable = false;
                    }

                    tileArray[i][j].isExplode = false;   //Do not put this in "if (fruitArray[i][j].special)", or it won't detect

                    //Assign fruit kind
                    tileArray[i][j].kind = TileID.FRUITS_TO_USE[(int) (Math.random() * TileID.FRUITS_TO_USE.length)];
                    /*
                    if(!isWin) {
                        if (tileArray[i][j].machine == 'H') {
                            if (cherry_num < CHERRY_MAX) {
                                cherry_num++;
                            } else {
                                cherry_num = 0;
                                tileArray[i][j].kind = R.drawable.cherry;
                                tileArray[i][j].special = true;
                                tileArray[i][j].direct = 'H';
                                animationManager.createMachineAnim(advanceArray[0][j - 1]);
                            }
                        }else if (tileArray[i][j].machine == 'V') {
                            if (cherry_num < CHERRY_MAX) {
                                cherry_num ++;
                            }else{
                                cherry_num = 0;
                                tileArray[i][j].kind = R.drawable.cherry;
                                tileArray[i][j].special = true;
                                tileArray[i][j].direct = 'V';
                                animationManager.createMachineAnim(advanceArray[0][j - 1]);
                            }
                        }else if (tileArray[i][j].machine == 'S') {
                            if (strawberry_num < STRAWBERRY_MAX) {
                                strawberry_num ++;
                            }else{
                                strawberry_num = 0;
                                tileArray[i][j].kind = R.drawable.strawberry;
                                tileArray[i][j].special = true;
                                tileArray[i][j].direct = 'S';
                                animationManager.createMachineAnim(advanceArray[0][j - 1]);
                            }
                        } else if (tileArray[i][j].machine == 'O') {
                            if (ice_cream_num < ICE_CREAM_MAX) {
                                ice_cream_num ++;
                            }else{
                                ice_cream_num = 0;
                                tileArray[i][j].kind = TileID.ICE_CREAM;
                                tileArray[i][j].special = true;
                                tileArray[i][j].direct = 'I';
                                animationManager.createMachineAnim(advanceArray[0][j - 1]);
                            }
                        } else if (tileArray[i][j].machine == 'X') {
                            if(star_fish_num < STAR_FISH_MAX){
                                if (Math.random() < 0.5) {
                                    star_fish_num++;
                                    tileArray[i][j].kind = TileID.STAR_FISH;
                                    animationManager.createMachineAnim(advanceArray[0][j - 1]);
                                }
                            }
                        }
                    }

                     */
                }
            }
        }
    }

    public void swap(Tile[][] tileArray, Tile tile1, Tile tile2) {
        if (tile1.invalid || tile2.invalid)
            return;

        //Exchange row
        int temp_row = tile1.row;
        tile1.row = tile2.row;
        tile2.row = temp_row;

        //Exchange column
        int temp_col = tile1.col;
        tile1.col = tile2.col;
        tile2.col = temp_col;

        //Exchange tile
        tileArray[tile1.row][tile1.col] = tile1;
        tileArray[tile2.row][tile2.col] = tile2;

    }

}
