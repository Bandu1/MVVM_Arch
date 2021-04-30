package com.example.demoapp.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.demoapp.R;
public class ProgressHUD extends Dialog {
    public static String mStringMessage;
    public static ProgressHUD mProgressHUD;

    public ProgressHUD(Context context) {
        super(context);
    }
    public static ProgressHUD getInstance(Context mContext) {
        if (mProgressHUD == null) {
            mProgressHUD = new ProgressHUD((Activity) mContext);
        }
        return mProgressHUD;
    }
    public ProgressHUD(Context context, int theme) {
        super(context, theme);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        TextView mTextViewTitle = (TextView) findViewById(R.id.progress_title);
        mTextViewTitle.setText(mStringMessage);
    }
    public static ProgressHUD showDialog(Context context, String title, boolean indeterminate, boolean cancelable,
                                         OnCancelListener cancelListener) {
        ProgressHUD dialog = new ProgressHUD(context);
        dialog.setTitle("");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_hud);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        ColorDrawable back = new ColorDrawable(context.getResources().getColor(R.color.colorTransparent));
        InsetDrawable inset = new InsetDrawable(back, 0);
        dialog.getWindow().setBackgroundDrawable(inset);
        mStringMessage = title;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
    }
}
