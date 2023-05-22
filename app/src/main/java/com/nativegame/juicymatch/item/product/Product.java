package com.nativegame.juicymatch.item.product;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Product {

    private final String mName;
    private final int mPrice;
    private final int mDrawableId;
    private final int mButtonId;

    private String mDescription;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public Product(String name, int price, int drawableId, int buttonId) {
        mName = name;
        mPrice = price;
        mDrawableId = drawableId;
        mButtonId = buttonId;
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public String getName() {
        return mName;
    }

    public int getPrice() {
        return mPrice;
    }

    public int getDrawableId() {
        return mDrawableId;
    }

    public int getButtonId() {
        return mButtonId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
    //========================================================

}
