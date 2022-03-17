package com.example.matchgamesample.dialog;

import android.view.View;

import com.example.matchgamesample.MainActivity;
import com.example.matchgamesample.R;

public class QuitDialog extends BaseDialog implements View.OnClickListener {

    private QuitDialogListener mListener;

    public QuitDialog(MainActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_quit);
        findViewById(R.id.btn_exit).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    public void setListener(QuitDialogListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_exit){
            dismiss();
            mListener.exit();
        } else if(view.getId() == R.id.btn_cancel){
            dismiss();
        }
    }

    public interface QuitDialogListener {
        void exit();
    }

}
