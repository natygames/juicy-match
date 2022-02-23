package com.example.matchgamesample.engine;

import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class Sprite extends GameObject{

    public ImageView mImage;
    public int x, y;
    public int mWidth;
    public int mHeight;

    protected Sprite(GameEngine gameEngine) {
        mImage = new ImageView(gameEngine.mActivity);
        mWidth = gameEngine.mImageSize;
        mHeight = gameEngine.mImageSize;
        mImage.setLayoutParams(new ViewGroup.LayoutParams(mWidth, mHeight));
    }

    protected Sprite(GameEngine gameEngine, int width, int height) {
        mImage = new ImageView(gameEngine.mActivity);
        mWidth = width;
        mHeight = height;
        mImage.setLayoutParams(new ViewGroup.LayoutParams(mWidth, mHeight));
    }

}
