package com.example.demoapp.utility;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.demoapp.R;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by       :ABC
 * Date             : 29/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of comman
 * Revisions        : 1 - XYZ     19-11-2018
 *			         Change – Add in add()
 *
 *                    2 - PQR     20-11-2018
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */
public class MyApplication extends Application {
    private static Context mContext;
    public static CompositeDisposable compositeDisposable;
    public static Context getmContext() {
        return mContext;
    }
    public static SharedPref mSharedPref;
    public static void setmContext(Context mContext) {
        MyApplication.mContext = mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public static void addCompositeDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
    public static void disposeCompositeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }
    @SuppressLint("CheckResult")
    public static void showImage(String imageUrl, int defaultImage) {
        Dialog builder = new Dialog(mContext);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });
        ImageView imageView = new ImageView(mContext);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(defaultImage);
        requestOptions.error(defaultImage);
        requestOptions.centerCrop();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.skipMemoryCache(false);
        requestOptions.dontAnimate();
        requestOptions.dontTransform();
        requestOptions.priority(Priority.HIGH);
        Glide.with(mContext)
                .applyDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .into(imageView);
        builder.show();
    }
}
