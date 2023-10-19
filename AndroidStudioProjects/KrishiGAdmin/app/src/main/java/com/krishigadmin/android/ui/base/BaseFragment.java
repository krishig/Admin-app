package com.krishigadmin.android.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.ui.base.views.BaseFragmentView;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment implements BaseFragmentView {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private BaseActivity baseActivity;
    protected VB viewBinding;
    protected View rootView;
    /**
     * Activity Navigator
     */
    protected ActivityNavigator activityNavigator;

    /**
     * Fragment Navigator
     */
    protected FragmentNavigator fragmentNavigator;
    protected abstract ActivityNavigator getActivityNavigator();
    protected abstract FragmentNavigator getFragmentNavigator();
    protected abstract VB getViewBinding();
    protected abstract void initializeViewModel();
    protected abstract void initializeToolBar();
    protected abstract void initializeObject();
    protected abstract void addTextChangedListener();
    protected abstract void setOnFocusChangeListener();
    protected abstract void observeViewModel();
    protected abstract void setOnClickListener();

    /*
     * OnAttach(Context context) is not call, If API level of the android version is lower than 23.
     * Because OnAttach(Context context) is added in API level 23.
     */
    @TargetApi(23)
    @Override
    @CallSuper
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach(@NonNull Context context)");

        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.baseActivity = activity;
        }
    }

    /*
     * OnAttach(Activity activity) is not call, If API level of the android version is greater than 22.
     * Because OnAttach(Activity activity) is deprecated in API level 23, but must remain to allow compatibility with api<23
     */
    @Override
    @CallSuper
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach(@NonNull Activity activity)");

        if (activity instanceof BaseActivity) {
            BaseActivity baseMVVMActivity = (BaseActivity) activity;
            this.baseActivity = baseMVVMActivity;
        }
    }

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNavigator = getActivityNavigator();
        fragmentNavigator = getFragmentNavigator();
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");
    }

    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)");
        viewBinding = getViewBinding();
        return viewBinding.getRoot();
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated(@NonNull View view, Bundle savedInstanceState)");

        rootView = view;

        initializeViewModel();
        initializeObject();
        initializeToolBar();
        addTextChangedListener();
        observeViewModel();
        setOnClickListener();
    }

    @Override
    @CallSuper
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated(@Nullable Bundle savedInstanceState)");
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    @CallSuper
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView()");
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    @CallSuper
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
        baseActivity = null;
    }

    protected  <VMC extends ViewModel> VMC createViewModel(@NonNull Class<VMC> viewModelClass) {
        return new ViewModelProvider(this).get(viewModelClass);
    }

    public BaseActivity getBaseMVVMActivity() {
        return baseActivity;
    }

    @Override
    public void showToast(String message) {
        Log.i(TAG, message);
        getBaseMVVMActivity().showToast(message);
    }

    @Override
    public void showToast(int messageResId) {
        Log.i(TAG, getString(messageResId));
        getBaseMVVMActivity().showToast(messageResId);
    }

    @Override
    public void showSnackBar(View parent, String message) {
        Log.i(TAG, message);
        getBaseMVVMActivity().snackBar(parent, message);
    }

    @Override
    public void showSnackBar(View parent, int messageResId) {
        Log.i(TAG, getString(messageResId));
        getBaseMVVMActivity().snackBar(parent, getString(messageResId));
    }

    @Override
    public void hideKeyboard() {
        getBaseMVVMActivity().hideKeyboard();
    }
}
