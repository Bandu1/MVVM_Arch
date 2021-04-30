package com.example.demoapp.module.splash.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.demoapp.databinding.ActivitySplashBinding;
import com.example.demoapp.module.login.activity.LoginActivity;
import com.example.demoapp.module.splash.activity.SplashActivity;
/**
 * Created by       :ABC
 * Date             : 28/04/2021
 * Purpose/Usage    : This class is used for business logic of splash screen
 * Revisions        : 1 - XYZ     30-04-2019
 *			         Change – Add in add()
 *
 *                    2 - PQR     1-05-2019
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */
public class SplashViewModel extends AndroidViewModel {
    private SplashActivity activity;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private SplashViewModel(@NonNull final Application application, ActivitySplashBinding spalshBinding, final SplashActivity activity) {
        super(application);
        this.activity = activity;
        init();
    }
    private void init() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Login-Activity. */
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                activity.startActivity(intent);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private ActivitySplashBinding splashBinding;
        private SplashActivity activity;
        public Factory(@NonNull Application application, ActivitySplashBinding binding, SplashActivity activity) {
            this.application = application;
            this.splashBinding = binding;
            this.activity = activity;
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SplashViewModel(application, splashBinding, activity);
        }
    }
}
