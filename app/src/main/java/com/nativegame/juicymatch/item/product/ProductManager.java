package com.nativegame.juicymatch.item.product;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.item.Item;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.util.resource.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ProductManager {

    private final List<Product> mProducts = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ProductManager(GameActivity activity) {
        // Init all the product
        Product productWatchAd = new Product(Item.COIN, 0, R.drawable.coin_50, R.drawable.btn_watch_ad);
        Product productGlove = new Product(Item.GLOVE, 50, R.drawable.glove, R.drawable.btn_price_50);
        Product productBomb = new Product(Item.BOMB, 60, R.drawable.bomb, R.drawable.btn_price_60);
        Product productHammer = new Product(Item.HAMMER, 70, R.drawable.hammer, R.drawable.btn_price_70);

        // Init product description
        productWatchAd.setDescription(ResourceUtils.getString(activity, R.string.txt_coin));
        productGlove.setDescription(ResourceUtils.getString(activity, R.string.txt_glove));
        productBomb.setDescription(ResourceUtils.getString(activity, R.string.txt_bomb));
        productHammer.setDescription(ResourceUtils.getString(activity, R.string.txt_hammer));

        // Add product to list
        mProducts.add(productWatchAd);
        mProducts.add(productGlove);
        mProducts.add(productBomb);
        mProducts.add(productHammer);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public List<Product> getAllProducts() {
        return mProducts;
    }
    //========================================================

}
