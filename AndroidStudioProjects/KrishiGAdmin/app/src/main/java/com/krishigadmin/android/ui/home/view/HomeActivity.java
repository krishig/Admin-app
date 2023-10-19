package com.krishigadmin.android.ui.home.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.databinding.ActivityHomeBinding;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.login.view.LoginActivity;
import com.library.utilities.activity.ActivityUtils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends BaseActivity<ActivityHomeBinding> {
    AppBarConfiguration appBarConfiguration;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected ActivityHomeBinding getViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(HomeActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return new FragmentNavigator(getSupportFragmentManager(), R.id.nav_host_fragment_content_main);
    }

    @Override
    protected void initializeViewModel() {

    }

    @Override
    protected void initializeToolBar() {
        setSupportActionBar(viewBinding.appBar.toolbar);

    }

    @Override
    protected void initializeObject() {
        viewBinding.appBar.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, viewBinding.drawerLayout, viewBinding.appBar.toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        viewBinding.drawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

      /*  mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_drawer, getTheme());
        mActionBarDrawerToggle.setHomeAsUpIndicator(drawable)*/;

        viewBinding.leftNavigationView.setItemIconTintList(null);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_sub_category, R.id.navigation_manage_product, R.id.navigation_category,
                R.id.navigation_manageBrands, R.id.navigation_customers, R.id.navigation_sales_person,
                R.id.navigation_settings, R.id.navigation_manage_order)
                .setOpenableLayout(viewBinding.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(viewBinding.leftNavigationView, navController);

        /*viewBinding.appBar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });*/
        // viewBinding.leftNavigationView.setNavigationItemSelectedListener(mOnNavigationDrawerItemSelectedListener);
        configureLeftNavDrawerHeaderView(viewBinding.leftNavigationView);

        viewBinding.appBar.settingMaterialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogConfirmExit(HomeActivity.this);

            }
        });
    }

  /*  private NavigationView.OnNavigationItemSelectedListener mOnNavigationDrawerItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            int id = menuItem.getItemId();

            switch (id) {
                case R.id.navigation_home:
                    viewBinding.appBar.toolbar.setTitle("Home");
                    viewBinding.appBar.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    fragmentNavigator.popAll();
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentNavigator.push(homeFragment, false, true);
                    break;

                case R.id.navigation_sub_category:
                    viewBinding.appBar.toolbar.setTitle("Manage Sub Category");
                    viewBinding.appBar.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    fragmentNavigator.popAll();
                    ManageSubCategoryFragment manageSubCategoryFragment = new ManageSubCategoryFragment();
                    fragmentNavigator.push(manageSubCategoryFragment, false, true);
                    break;
                case R.id.navigation_manage_product:
                    viewBinding.appBar.toolbar.setTitle("Manage Product");
                    viewBinding.appBar.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    ManageProductFragment manageProductFragment = new ManageProductFragment();
                    fragmentNavigator.push(manageProductFragment, false, true);
                    break;
                case R.id.navigation_category:
                    viewBinding.appBar.toolbar.setTitle("Manage Category");
                    viewBinding.appBar.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    fragmentNavigator.popAll();
                    ManageCategoryFragment manageCategoryFragment = new ManageCategoryFragment();
                    fragmentNavigator.push(manageCategoryFragment, false, true);
                    break;
                case R.id.navigation_manageBrands:
                    viewBinding.appBar.toolbar.setTitle("Manage Brands");
                    viewBinding.appBar.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    fragmentNavigator.popAll();
                    ManageBrandFragment manageBrandFragment = new ManageBrandFragment();
                    fragmentNavigator.push(manageBrandFragment, false, true);
                    break;
                case R.id.navigation_sales_person:
                    viewBinding.appBar.toolbar.setTitle("Manage Sales Person");
                    viewBinding.appBar.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    fragmentNavigator.popAll();
                    ManageSalesPersonFragment manageSalesPersonFragment = new ManageSalesPersonFragment();
                    fragmentNavigator.push(manageSalesPersonFragment, false, true);
                    break;
                case R.id.navigation_settings:
                    viewBinding.appBar.toolbar.setTitle("Manage Setting");
                    viewBinding.appBar.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    fragmentNavigator.popAll();
                    ManageSettingFragment manageSettingFragment = new ManageSettingFragment();
                    fragmentNavigator.push(manageSettingFragment, false, true);
                    break;
                case R.id.navigation_manage_order:
                    viewBinding.appBar.toolbar.setTitle("Manage Order");
                    viewBinding.appBar.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                    fragmentNavigator.popAll();
                    ManageOrderFragment manageOrderFragment = new ManageOrderFragment();
                    fragmentNavigator.push(manageOrderFragment, false, true);
                    break;
            }

            //drawer
            viewBinding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };*/


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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

    private void configureLeftNavDrawerHeaderView(NavigationView navigationView) {
        View navigationDrawerHeaderView = navigationView.getHeaderView(0);

        RelativeLayout continueButton = viewBinding.leftNavigationView.findViewById(R.id.logoutRelativeLayout);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogConfirmExit(HomeActivity.this);
            }
        });
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

    private void alertDialogConfirmExit(Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setIcon(R.drawable.ic_black_question_mark);
        alertDialogBuilder.setTitle("Confirm Exit");
        alertDialogBuilder.setMessage("Are you sure you want to Exit?");


        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                sharedPreferencesHelper.setRemember(false);
                Intent intent = ActivityUtils.launchActivityWithClearBackStack(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
        positiveButton.setBackgroundColor(Color.parseColor("#000000"));

        negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
        negativeButton.setBackgroundColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(20, 0, 0, 0);
        positiveButton.setLayoutParams(params);
    }

}