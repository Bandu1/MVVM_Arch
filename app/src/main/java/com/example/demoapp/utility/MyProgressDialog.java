package com.example.demoapp.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

import com.example.demoapp.R;
public class MyProgressDialog {
    public Context mContext;
    private ProgressDialog mDialog;
    private static MyProgressDialog myProgressDialog;
    private MyProgressDialog(Context context) {
        mContext = context;
    }
    public static MyProgressDialog getInstance(Context mContext) {
        if (myProgressDialog == null) {
            myProgressDialog = new MyProgressDialog(mContext);
        }
        return myProgressDialog;
    }
    private MyProgressDialog(){
    }
    public static MyProgressDialog getInstance() {
        if (myProgressDialog == null) {
            myProgressDialog = new MyProgressDialog();
        }
        return myProgressDialog;
    }

    public ProgressDialog showProgressBar(){
        try {
            if (MyApplication.getmContext() == null){
                MyApplication.setmContext(mContext);
            }
            if (mDialog != null) {
                if (!mDialog.isShowing()){
                    mDialog = new ProgressDialog(MyApplication.getmContext());
                    mDialog.show();
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage(MyApplication.getmContext().getString(R.string.please_wait));
                }
            } else{
                mDialog = new ProgressDialog(MyApplication.getmContext());
                mDialog.show();
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setMessage(MyApplication.getmContext().getString(R.string.please_wait));
            }
        } catch (WindowManager.BadTokenException e) {
            Logs.ERROR(e.getMessage());
        }
        return mDialog;
    }
    public void dismissProgressBar() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
