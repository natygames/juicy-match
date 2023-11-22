package com.nativegame.juicymatch.ui.dialog;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.item.product.Product;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.ui.GameButton;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class NewBoosterDialog extends BaseDialog implements View.OnClickListener {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public NewBoosterDialog(GameActivity activity, Product product) {
        super(activity);
        setContentView(R.layout.dialog_new_booster);
        setContainerView(R.layout.dialog_container);

        // Init booster image
        ImageView imageBooster = (ImageView) findViewById(R.id.image_booster);
        imageBooster.setImageResource(product.getDrawableId());

        // Init booster bg image
        ImageView imageBoosterBg = (ImageView) findViewById(R.id.image_booster_bg);
        Animation animation = AnimationUtils.loadAnimation(mParent, R.anim.logo_rotate);
        imageBoosterBg.startAnimation(animation);

        // Init button
        GameButton btnNext = (GameButton) findViewById(R.id.btn_next);
        btnNext.popUp(200, 300);
        btnNext.setOnClickListener(this);

        Sounds.GAME_WIN.play();
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_next) {
            dismiss();
        }
    }
    //========================================================

}
