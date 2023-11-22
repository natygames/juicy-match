package com.nativegame.juicymatch.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.item.Item;
import com.nativegame.juicymatch.item.product.Product;
import com.nativegame.juicymatch.item.product.ProductManager;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.ui.GameButton;
import com.nativegame.natyengine.ui.GameImage;
import com.nativegame.natyengine.ui.GameText;

import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ShopDialog extends BaseDialog implements View.OnClickListener {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ShopDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_shop);
        setContainerView(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);

        // Init button
        GameButton btnCancel = (GameButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);

        // Init product view
        ProductManager productManager = new ProductManager(mParent);
        initProduct(productManager.getAllProducts());
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            dismiss();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void initProduct(List<Product> products) {
        ProductAdapter productAdapter = new ProductAdapter(mParent, products);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(mParent, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(productAdapter);
    }

    private void showMoreCoinDialog() {
        MoreCoinDialog moreCoinDialog = new MoreCoinDialog(mParent) {
            @Override
            public void updateCoin() {
                ShopDialog.this.updateCoin();
            }
        };
        mParent.showDialog(moreCoinDialog);
    }

    private void showConfirmDialog(Product product) {
        ConfirmDialog confirmDialog = new ConfirmDialog(mParent, product) {
            @Override
            public void updateCoin() {
                ShopDialog.this.updateCoin();
            }
        };
        mParent.showDialog(confirmDialog);
    }

    public void updateCoin() {
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        private final Context mContext;
        private final List<Product> mProducts;

        //--------------------------------------------------------
        // Constructors
        //--------------------------------------------------------
        public ProductAdapter(Context context, List<Product> products) {
            mContext = context;
            mProducts = products;
        }
        //========================================================

        //--------------------------------------------------------
        // Overriding methods
        //--------------------------------------------------------
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_shop_product, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            // Init view holder
            Product product = mProducts.get(position);
            holder.mImageProduct.setImageResource(product.getDrawableId());
            holder.mBtnProduct.setBackgroundResource(product.getButtonId());
            holder.mBtnProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Show confirm dialog
                    if (product.getName().equals(Item.COIN)) {
                        showMoreCoinDialog();
                    } else {
                        showConfirmDialog(product);
                    }
                }
            });
            holder.mTxtProductDescription.setText(product.getDescription());

            // Init pop up effect
            long startDelay = 300 + position * 100L;
            holder.mImageField.popUp(200, startDelay);
            holder.mImageProduct.popUp(200, startDelay);
            holder.mBtnProduct.popUp(200, startDelay + 200);
            holder.mTxtProductDescription.popUp(200, startDelay);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
        //========================================================

        //--------------------------------------------------------
        // Inner Classes
        //--------------------------------------------------------
        public class ProductViewHolder extends RecyclerView.ViewHolder {

            private final GameImage mImageField;
            private final GameImage mImageProduct;
            private final GameButton mBtnProduct;
            private final GameText mTxtProductDescription;

            //--------------------------------------------------------
            // Constructors
            //--------------------------------------------------------
            public ProductViewHolder(View view) {
                super(view);
                mImageField = view.findViewById(R.id.image_field);
                mImageProduct = view.findViewById(R.id.image_product);
                mBtnProduct = view.findViewById(R.id.btn_product);
                mTxtProductDescription = view.findViewById(R.id.txt_product);
            }
            //========================================================

        }
        //========================================================

    }
    //========================================================

}
