package com.krishigadmin.android.ui.home.fragments.manageBrands.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.databinding.FragmentManageBrandBinding;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.ProductBrands;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseFragment;
import com.krishigadmin.android.ui.home.fragments.addCategory.view.AddCategoryActivity;
import com.krishigadmin.android.ui.home.fragments.addProductBrands.view.AddProductBrandsActivity;
import com.krishigadmin.android.ui.home.fragments.manageBrands.adapter.BrandsAdapter;
import com.krishigadmin.android.ui.home.fragments.manageBrands.viewmodel.BrandModel;
import com.krishigadmin.android.ui.home.fragments.manageCategory.adapter.CategoryAdapter;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ManageBrandFragment extends BaseFragment<FragmentManageBrandBinding> {

    BrandsAdapter brandsAdapter;

    ArrayList<ProductBrands.Result> arrayList = new ArrayList<>();
    ArrayList<ProductBrands.Result> DataArrayList = new ArrayList<>();
    BrandModel viewModel;


    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;


    int totalPages = 0, count = 1;
    String itemPerPageInProduct = "15", searchData = "";
    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(getActivity());
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected FragmentManageBrandBinding getViewBinding() {
        return FragmentManageBrandBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViewModel() {
        viewModel = createViewModel(BrandModel.class);
    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
        setRecyclerView(viewBinding.recyclerView);

    }

    private void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        brandsAdapter = new BrandsAdapter(getContext());
        brandsAdapter.addArrayList(arrayList);
        recyclerView.setAdapter(brandsAdapter);
    }

    @Override
    protected void addTextChangedListener() {
        viewBinding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchData = editable.toString();
                if (searchData.equalsIgnoreCase("")) {
                    DataArrayList.clear();
                    count = 1;
                    viewModel.getProductBrands(itemPerPageInProduct, String.valueOf(count),"application/json", "application/json",
                            sharedPreferencesHelper.getKeyToken());
                    showProgressDialog();
                } else {
                    DataArrayList.clear();
                    count = 1;
                    viewModel.subProductBrandsSearch(itemPerPageInProduct, String.valueOf(count),editable.toString(), "application/json", "application/json",
                            sharedPreferencesHelper.getKeyToken());
                }



            }
        });

    }

    @Override
    protected void setOnFocusChangeListener() {

    }

    @Override
    protected void observeViewModel() {


        Observer<String> getProductBrandsUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.getProductBrandsUserError().observe(this, getProductBrandsUserError);

        final Observer<ProductBrands> getProductBrandsUserSuccess = new Observer<ProductBrands>() {
            @Override
            public void onChanged(ProductBrands categories) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                int size = categories.getResultArrayList().size();
                if (size == 0) {
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);
                } else {
                    totalPages = categories.getTotal_pages();
                    viewBinding.recyclerView.setVisibility(View.VISIBLE);
                    viewBinding.errorImageView.setVisibility(View.GONE);
                    viewBinding.errorTextView.setVisibility(View.GONE);
                    arrayList = categories.getResultArrayList();
                    DataArrayList.addAll(arrayList);
                    brandsAdapter.clearAllItem();
                    brandsAdapter.addArrayList(DataArrayList);
                }

            }
        };
        viewModel.getProductBrandsUserSuccess().observe(this, getProductBrandsUserSuccess);


        Observer<String> deleteProductBrandsUserError = new Observer<String>() {
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
        viewModel.deleteProductBrandsUserError().observe(this, deleteProductBrandsUserError);

        final Observer<ArrayList<ProductBrands>> deleteProductBrandsUserSuccess = new Observer<ArrayList<ProductBrands>>() {
            @Override
            public void onChanged(ArrayList<ProductBrands> categories) {
                hideProgressDialog();
                DataArrayList.clear();
                count = 1;
                viewModel.getProductBrands(itemPerPageInProduct, String.valueOf(count),"application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();
            }
        };
        viewModel.deleteProductBrandsUserSuccess().observe(this, deleteProductBrandsUserSuccess);



        Observer<String> getSubCategorySearchUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.getSubCategorySearchUserError().observe(this, getSubCategorySearchUserError);

        final Observer<ProductBrands> getSubCategorySearchUserSuccess = new Observer<ProductBrands>() {
            @Override
            public void onChanged(ProductBrands categories) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                int size = categories.getResultArrayList().size();
                if (size == 0) {
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);
                } else {
                    totalPages = categories.getTotal_pages();
                    viewBinding.recyclerView.setVisibility(View.VISIBLE);
                    viewBinding.errorImageView.setVisibility(View.GONE);
                    viewBinding.errorTextView.setVisibility(View.GONE);
                    arrayList = categories.getResultArrayList();
                    DataArrayList.addAll(arrayList);
                    brandsAdapter.clearAllItem();
                    brandsAdapter.addArrayList(DataArrayList);
                }

            }
        };
        viewModel.getSubCategorySearchUserSuccess().observe(this, getSubCategorySearchUserSuccess);


    }

    @Override
    protected void setOnClickListener() {

        viewBinding.addMaterialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProductBrandsActivity.class);
                intent.putExtra(AppConstants.Extras.FROM, "0");
                startActivity(intent);
            }
        });

        brandsAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<ProductBrands.Result>() {
            @Override
            public void OnItemChildClick(View viewChild, ProductBrands.Result category, int position) {
                switch (viewChild.getId()) {
                    case R.id.deleteImageView:
                        alertDialogConfirmExit(getActivity(), category.getId());
                        break;
                    case R.id.editImageView:
                        Intent intent = new Intent(getActivity(), AddProductBrandsActivity.class);
                        intent.putExtra(AppConstants.Extras.FROM, "1");
                        intent.putExtra(AppConstants.Extras.PRODUCT_BRAND_ID, category.getId());
                        intent.putExtra(AppConstants.Extras.PRODUCT_BRAND_NAME, category.getBrandName());
                        intent.putExtra(AppConstants.Extras.PRODUCT_BRAND_IMAGE, category.getBrandImageUrl());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        viewBinding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    ++count;
                    if (count <= totalPages) {
                        viewBinding.idPBLoading.setVisibility(View.VISIBLE);

                        if (searchData.equalsIgnoreCase("")) {
                            viewModel.getProductBrands(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                        } else {
                            viewModel.subProductBrandsSearch(itemPerPageInProduct, String.valueOf(count),searchData, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        }


                    }

                }
            }
        });



        viewBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewBinding.refreshLayout.setRefreshing(false);
                viewBinding.searchEditText.setText("");
                DataArrayList.clear();
                count = 1;
                viewModel.getProductBrands(itemPerPageInProduct, String.valueOf(count),"application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();
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
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void hideProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("===========Resume============");
        DataArrayList.clear();
        count = 1;
        viewModel.getProductBrands(itemPerPageInProduct, String.valueOf(count),"application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
        showProgressDialog();
    }

    private void alertDialogConfirmExit(Activity activity, String id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setIcon(R.drawable.ic_black_question_mark);
        alertDialogBuilder.setTitle("Confirm Delete");
        alertDialogBuilder.setMessage("Are you sure you want to Delete?");


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
                viewModel.deleteProductBrands(id, "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();
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