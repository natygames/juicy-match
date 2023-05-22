package com.nativegame.juicymatch.algorithm;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public interface Match3Tile {

    int getRow();

    void setRow(int row);

    int getColumn();

    void setColumn(int column);

    TileState getTileState();

    void setTileState(TileState tileState);

    TileType getTileType();

    void setTileType(TileType tileType);

    void resetXByColumn(int column);

    void resetYByRow(int row);

    void initTile();

    void popTile();

    void matchTile();

    void resetTile();

    void hideTile();

    void shuffleTile();

    void playTileEffect();

    void moveTile(long elapsedMillis);

    void swapTile(long elapsedMillis);

    boolean isMoving();

    boolean isSwappable();

    void setSwappable(boolean swappable);

    boolean isPoppable();

    void setPoppable(boolean poppable);

    boolean isMatchable();

    void setMatchable(boolean matchable);

    boolean isShufflable();

    void setShufflable(boolean shufflable);

    boolean isNegligible();

    void setNegligible(boolean negligible);

}
