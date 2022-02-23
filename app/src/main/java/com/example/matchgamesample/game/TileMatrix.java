package com.example.matchgamesample.game;

import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameObject;

public class TileMatrix extends GameObject {
    private final Tile[][] mTileMatrix;
    private final int row, column;
    private final MyAlgorithm myAlgorithm;

    public TileMatrix(Tile[][] tileMatrix, int row, int column, int tileSize) {
        this.mTileMatrix = tileMatrix;
        this.row = row;
        this.column = column;
        myAlgorithm = new MyAlgorithm(row, column, tileSize);
    }

    @Override
    public void startGame() {

        for (int i = 0; i <= row - 1; i++) {
            for (int j = 0; j <= column - 1; j++) {
                mTileMatrix[i][j].startGame();
            }
        }
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        myAlgorithm.run(mTileMatrix);
    }

    @Override
    public void onDraw() {
        for (int i = 0; i <= row - 1; i++) {
            for (int j = 0; j <= column - 1; j++) {
                mTileMatrix[i][j].onDraw();
            }
        }
    }

}
