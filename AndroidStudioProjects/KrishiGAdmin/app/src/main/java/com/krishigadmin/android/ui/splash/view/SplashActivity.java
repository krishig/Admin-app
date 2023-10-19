package com.krishigadmin.android.ui.splash.view;

import android.content.Intent;
import android.os.Handler;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.databinding.ActivitySplashBinding;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.home.view.HomeActivity;
import com.krishigadmin.android.ui.login.view.LoginActivity;
import com.library.utilities.activity.ActivityUtils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private Handler handler;
    private Runnable runnable;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected ActivitySplashBinding getViewBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(SplashActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void initializeViewModel() {

    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (sharedPreferencesHelper.getRemember()) {
                    Intent intent = ActivityUtils.launchActivityWithClearBackStack(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = ActivityUtils.launchActivityWithClearBackStack(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void addTextChangedListener() {

    }

    @Override
    protected void setOnFocusChangeListener() {

    }

    @Override
    protected void observeViewModel() {

    }

    @Override
    protected void setOnClickListener() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (handler != null) {
            handler.postDelayed(runnable, AppConstants.Delay.SPLASH);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}