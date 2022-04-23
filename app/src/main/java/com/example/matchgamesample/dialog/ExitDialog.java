package com.example.matchgamesample.dialog;

import android.view.View;
import android.widget.ImageButton;

import com.example.matchgamesample.MainActivity;
import com.example.matchgamesample.R;
import com.example.matchgamesample.Utils;
import com.example.matchgamesample.effect.sound.SoundEvent;

public class ExitDialog extends BaseDialog implements View.OnClickListener {

    private ExitDialogListener mListener;

    public ExitDialog(MainActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_exit);

        // Init button
        ImageButton btnExit = (ImageButton) findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(this);
        Utils.createButtonEffect(btnExit);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        Utils.createButtonEffect(btnCancel);

        // Init pop up
        Utils.createPopUpEffect(btnExit);
    }

    public void setListener(ExitDialogListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_exit) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            super.dismiss();
            mListener.exit();
        } else if (view.getId() == R.id.btn_cancel) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.SWEEP2);
            super.dismiss();
        }
    }

    public interface ExitDialogListener {
        void exit();
    }

}
