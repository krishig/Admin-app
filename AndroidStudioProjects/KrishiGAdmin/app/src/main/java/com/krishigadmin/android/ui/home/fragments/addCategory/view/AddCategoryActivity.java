package com.krishigadmin.android.ui.home.fragments.addCategory.view;

import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.RequestUtils;
import com.krishigadmin.android.databinding.ActivityAddCategorysBinding;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.User;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.home.fragments.addCategory.viewmodel.CategoryModel;

import org.json.JSONObject;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class AddCategoryActivity extends BaseActivity<ActivityAddCategorysBinding> {
    CategoryModel viewModel;

    String from = "", categoryId = "", categoryName = "";
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected ActivityAddCategorysBinding getViewBinding() {
        return ActivityAddCategorysBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(AddCategoryActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void initializeViewModel() {
        viewModel = createViewModel(CategoryModel.class);
    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
        if (getIntent().getExtras() != null) {
            from = getIntent().getExtras().getString(AppConstants.Extras.FROM);
            categoryId = getIntent().getExtras().getString(AppConstants.Extras.CATEGORY_ID);
            categoryName = getIntent().getExtras().getString(AppConstants.Extras.CATEGORY_NAME);
        }
        if (categoryName != null) {
            viewBinding.categoryNameTextInputEditText.setText(categoryName);
        }

    }

    @Override
    protected void addTextChangedListener() {

    }

    @Override
    protected void setOnFocusChangeListener() {

    }

    @Override
    protected void observeViewModel() {
        Observer<String> addCategoryUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.addCategoryUserError().observe(this, addCategoryUserError);

        final Observer<User> addCategoryUserSuccess = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                hideProgressDialog();
                finish();
            }
        };
        viewModel.addCategoryUserSuccess().observe(this, addCategoryUserSuccess);


        Observer<String> editCategoryUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.editCategoryUserError().observe(this, editCategoryUserError);

        final Observer<Category> editCategoryUserSuccess = new Observer<Category>() {
            @Override
            public void onChanged(Category user) {
                hideProgressDialog();
                finish();
            }
        };
        viewModel.editCategoryUserSuccess().observe(this, editCategoryUserSuccess);
    }

    @Override
    protected void setOnClickListener() {
        viewBinding.icBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewBinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = viewBinding.categoryNameTextInputEditText.getText().toString().trim();
                if (category.equalsIgnoreCase("")) {
                    viewBinding.categoryNameInputLayout.setError("Please Enter Category");
                    viewBinding.categoryNameInputLayout.setErrorEnabled(true);
                } else {
                    viewBinding.categoryNameInputLayout.setError("");
                    viewBinding.categoryNameInputLayout.setErrorEnabled(false);

                    if (from.equalsIgnoreCase("0")) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("category_name", category);
                            RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject.toString());
                            viewModel.addCategories(requestBody, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                        } catch (Exception exception) {

                        }
                    } else if (from.equalsIgnoreCase("1")) {
                        if(categoryId!=null){
                            try {
                                JSONObject jsonObject = new JSONObject();
                                //jsonObject.put("id", categoryId);
                                jsonObject.put("category_name", category);
                                RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject.toString());
                                viewModel.editCategories(categoryId,requestBody, "application/json", "application/json",
                                        sharedPreferencesHelper.getKeyToken());
                                showProgressDialog();
                            } catch (Exception exception) {

                            }
                        }
                    }
                }
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
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.GONE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void hideProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}