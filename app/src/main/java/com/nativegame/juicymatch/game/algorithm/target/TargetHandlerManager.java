package com.nativegame.juicymatch.game.algorithm.target;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.juicymatch.level.TargetType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TargetHandlerManager {

    private final List<TargetType> mTargetTypes;
    private final List<Integer> mTargetNums;

    private boolean mIsTargetChanged = false;

    private final List<TargetHandler> mTargetHandlers = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TargetHandlerManager() {
        mTargetTypes = Level.LEVEL_DATA.getTargetTypes();
        mTargetNums = Level.LEVEL_DATA.getTargetCounts();
        // Init TargetHandler from TargetType
        int size = mTargetTypes.size();
        for (int i = 0; i < size; i++) {
            TargetType type = mTargetTypes.get(i);
            mTargetHandlers.add(getTargetHandle(type));
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void checkTargets(Tile[][] tiles, int row, int col) {
        mIsTargetChanged = false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // Check is tile match
                Tile t = tiles[i][j];
                if (t.getTileState() != TileState.MATCH) {
                    continue;
                }
                // Check is tile contain target
                int size = mTargetHandlers.size();
                for (int n = 0; n < size; n++) {
                    TargetHandler handler = mTargetHandlers.get(n);
                    if (handler != null && handler.checkTarget(t)) {
                        // Update target count if found
                        updateTarget(mTargetTypes.get(n));
                    }
                }
            }
        }
    }

    public void updateTarget(TargetType type) {
        // Update LevelData
        int index = mTargetTypes.indexOf(type);
        int num = mTargetNums.get(index);
        if (num > 0) {
            mTargetNums.set(index, num - 1);
            mIsTargetChanged = true;
        }
    }

    public boolean isTargetChange() {
        return mIsTargetChanged;
    }

    public boolean isTargetComplete() {
        // Check is all the target num equal 0
        int size = mTargetNums.size();
        for (int i = 0; i < size; i++) {
            if (mTargetNums.get(i) > 0) {
                return false;
            }
        }

        return true;
    }

    private TargetHandler getTargetHandle(TargetType type) {
        switch (type) {
            case STRAWBERRY:
                return new StrawberryTargetHandler();
            case CHERRY:
                return new CherryTargetHandler();
            case LEMON:
                return new LemonTargetHandler();
            case COOKIE:
                return new CookieTargetHandler();
            case CAKE:
                return new CakeTargetHandler();
            case PIE:
                return new PieTargetHandler();
            case CANDY:
                return new CandyTargetHandler();
        }

        return null;
    }
    //========================================================

}
