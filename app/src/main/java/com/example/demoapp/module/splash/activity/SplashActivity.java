package com.example.demoapp.module.splash.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.demoapp.R;
import com.example.demoapp.databinding.ActivitySplashBinding;
import com.example.demoapp.module.splash.viewmodel.SplashViewModel;
import com.example.demoapp.utility.MyApplication;

/**
 * Created by       :ABC
 * Date             : 28/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of Splash
 * Revisions        : 1 - XYZ     19-11-2018
 *			         Change – Add in add()
 *
 *                    2 - PQR     20-11-2018
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */
public class SplashActivity extends AppCompatActivity {
    private SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setmContext(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActivitySplashBinding activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        SplashViewModel.Factory factory = new SplashViewModel.Factory(getApplication(), activitySplashBinding, SplashActivity.this);
        viewModel= ViewModelProviders.of(SplashActivity.this, factory).get(SplashViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}