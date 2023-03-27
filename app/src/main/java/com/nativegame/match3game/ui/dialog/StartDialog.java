package com.nativegame.match3game.ui.dialog;

import android.widget.ImageView;
import android.widget.TextView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.level.Level;
import com.nativegame.match3game.level.LevelType;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.util.resource.ResourceUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class StartDialog extends BaseDialog {

    private static final long TIME_TO_LIVE = 1500;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public StartDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_start);
        setContainerView(R.layout.dialog_container_game);
        setEnterAnimationId(R.anim.enter_from_top);
        setExitAnimationId(R.anim.exit_to_bottom);
        init();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void init() {
        int level = Level.LEVEL_DATA.getLevel();
        LevelType type = Level.LEVEL_DATA.getLevelType();

        // Init level text
        TextView txtLevel = (TextView) findViewById(R.id.txt_level);
        txtLevel.setText(ResourceUtils.getString(mParent, R.string.txt_level, level));

        // Init level target image
        ImageView imageTarget = (ImageView) findViewById(R.id.image_level_type);
        imageTarget.setImageResource(type.getDrawableId());

        getContentView().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, TIME_TO_LIVE);
    }
    //========================================================

}
