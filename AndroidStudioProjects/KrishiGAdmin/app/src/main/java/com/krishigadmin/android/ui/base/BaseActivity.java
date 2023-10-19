package com.krishigadmin.android.ui.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.snackbar.Snackbar;
import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.library.networkconnectivity.NetworkStateChangeListener;
import com.library.networkconnectivity.NetworkStateChangeReceiver;
import com.library.networkconnectivity.RegisterAndUnregisterNetworkReceiver;
import com.krishigadmin.android.ui.base.views.BaseActivityView;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity implements BaseActivityView {

    public static final String TAG = BaseActivity.class.getSimpleName();
    /**
     * Activity Navigator
     */
    protected ActivityNavigator activityNavigator;

    /**
     * Fragment Navigator
     */
    protected FragmentNavigator fragmentNavigator;

    protected VB viewBinding;

    protected abstract VB getViewBinding();
    /**
     * You need override this method.
     * And you use it for navigate to another activity, send bundle, serializable, parcelable to another
     * activity and get result back from activity.
     */
    protected abstract ActivityNavigator getActivityNavigator();
    /**
     * You need override this method.
     * And you use it for add and replace fragment, send bundle, serializable, parcelable to another
     * fragment.
     */
    protected abstract FragmentNavigator getFragmentNavigator();
    protected abstract void initializeViewModel();

    protected abstract void initializeToolBar();

    protected abstract void initializeObject();

    protected abstract void addTextChangedListener();

    protected abstract void setOnFocusChangeListener();

    protected abstract void observeViewModel();

    protected abstract void setOnClickListener();

    private RegisterAndUnregisterNetworkReceiver registerAndUnregisterNetworkReceiver;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");

        registerAndUnregisterNetworkReceiver = new RegisterAndUnregisterNetworkReceiver(this);
        checkInternetConnection();

        viewBinding = getViewBinding();
        setContentView(viewBinding.getRoot());
        activityNavigator = getActivityNavigator();
        fragmentNavigator = getFragmentNavigator();
        initializeViewModel();
        initializeToolBar();
        initializeObject();
        addTextChangedListener();
        observeViewModel();
        setOnClickListener();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    @CallSuper
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        registerAndUnregisterNetworkReceiver.registerNetworkReceiver(this);
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        registerAndUnregisterNetworkReceiver.unregisterNetworkReceiver(this);
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "onBackPressed()");
    }

    @Override
    public void networkAvailable() {
        Log.i(TAG, "INTERNET CONNECT");
        //showToast("Internet Connect");
    }

    @Override
    public void networkUnavailable() {
        Log.i(TAG, "INTERNET DISCONNECT");
        showToast("Internet Disconnect");
    }

    public void toast(String message) {
        Toast toast= Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showToast(String message) {
        Log.i(TAG, message);
        toast(message);
    }

    @Override
    public void showToast(int messageResId) {
        Log.i(TAG, getString(messageResId));
        toast(getString(messageResId));
    }

    public void snackBar(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void showSnackBar(View parent, String message) {
        Log.i(TAG, message);
        snackBar(parent, message);
    }

    @Override
    public void showSnackBar(View parent, int messageResId) {
        Log.i(TAG, getString(messageResId));
        snackBar(parent, getString(messageResId));
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected <VMC extends ViewModel> VMC createViewModel(@NonNull Class<VMC> viewModelClass) {
        return new ViewModelProvider(this).get(viewModelClass);
    }

    private void checkInternetConnection() {
        NetworkStateChangeReceiver.setNetworkStateChangeListener(new NetworkStateChangeListener() {
            @Override
            public void networkAvailable() {
                BaseActivity.this.networkAvailable();
            }

            @Override
            public void networkUnavailable() {
                BaseActivity.this.networkUnavailable();
            }
        });
    }

    protected void setupToolBar(Toolbar toolbar, int background, int navigationIcon, int title) {
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(background));
        toolbar.setNavigationIcon(getResources().getDrawable(navigationIcon));
        toolbar.setTitle(getResources().getString(title));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
