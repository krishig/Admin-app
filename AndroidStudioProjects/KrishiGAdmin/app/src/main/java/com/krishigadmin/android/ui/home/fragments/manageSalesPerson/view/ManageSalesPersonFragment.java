package com.krishigadmin.android.ui.home.fragments.manageSalesPerson.view;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.databinding.FragmentManageSalesPersonBinding;
import com.krishigadmin.android.model.User;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseFragment;
import com.krishigadmin.android.ui.home.fragments.EditSalesPerson.EditManageSalesPersonActivity;
import com.krishigadmin.android.ui.home.fragments.manageSalesPerson.adapter.ManageSalesPersonAdapter;
import com.krishigadmin.android.ui.home.fragments.manageSalesPerson.viewmodel.ManageSalesPersonViewModel;
import com.krishigadmin.android.ui.login.view.LoginActivity;
import com.krishigadmin.android.ui.signup.view.SignUpActivity;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ManageSalesPersonFragment extends BaseFragment<FragmentManageSalesPersonBinding> {
    ManageSalesPersonViewModel viewModel;
    ManageSalesPersonAdapter manageSalesPersonAdapter;
    ArrayList<User.result> arrayList = new ArrayList<>();
    ArrayList<User.result> dataStoreHomeArrayList = new ArrayList<>();

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
    protected FragmentManageSalesPersonBinding getViewBinding() {
        return FragmentManageSalesPersonBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViewModel() {
        viewModel = createViewModel(ManageSalesPersonViewModel.class);
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
        manageSalesPersonAdapter = new ManageSalesPersonAdapter(getContext());
        manageSalesPersonAdapter.addArrayList(arrayList);
        recyclerView.setAdapter(manageSalesPersonAdapter);
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
                    count = 1;
                    dataStoreHomeArrayList.clear();
                    viewModel.getUser(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                            sharedPreferencesHelper.getKeyToken());
                    showProgressDialog();
                } else {
                    dataStoreHomeArrayList.clear();
                    count = 1;
                    viewModel.getUserSearch(itemPerPageInProduct, String.valueOf(count), editable.toString(), "application/json", "application/json",
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

        Observer<String> getUserError = new Observer<String>() {
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
        viewModel.getUserError().observe(this, getUserError);

        final Observer<User> getUserSuccess = new Observer<User>() {
            @Override
            public void onChanged(User categories) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                int size = categories.getArrayList().size();
                if (size == 0) {
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);
                } else {
                    totalPages = categories.getTotal_pages();
                    viewBinding.recyclerView.setVisibility(View.VISIBLE);
                    viewBinding.errorImageView.setVisibility(View.GONE);
                    viewBinding.errorTextView.setVisibility(View.GONE);
                    arrayList = categories.getArrayList();
                    dataStoreHomeArrayList.addAll(arrayList);
                    Log.e("", "dataStoreHomeArrayList==" + dataStoreHomeArrayList.size());
                    Log.e("", "homeArrayList==" + arrayList.size());
                    manageSalesPersonAdapter.clearAllItem();
                    manageSalesPersonAdapter.replaceArrayList(dataStoreHomeArrayList);
                }

            }
        };
        viewModel.getUserSuccess().observe(this, getUserSuccess);


        Observer<String> getSearchUserError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                viewBinding.idPBLoading.setVisibility(View.GONE);
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.getSearchUserError().observe(this, getSearchUserError);

        final Observer<User> getSearchUserSuccess = new Observer<User>() {
            @Override
            public void onChanged(User categories) {
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                int size = categories.getArrayList().size();
                if (size == 0) {
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);
                } else {
                    totalPages = categories.getTotal_pages();
                    viewBinding.recyclerView.setVisibility(View.VISIBLE);
                    viewBinding.errorImageView.setVisibility(View.GONE);
                    viewBinding.errorTextView.setVisibility(View.GONE);
                    arrayList = categories.getArrayList();
                    dataStoreHomeArrayList.addAll(arrayList);
                    Log.e("", "dataStoreHomeArrayList==" + dataStoreHomeArrayList.size());
                    Log.e("", "homeArrayList==" + arrayList.size());
                    manageSalesPersonAdapter.clearAllItem();
                    manageSalesPersonAdapter.replaceArrayList(dataStoreHomeArrayList);
                }

            }
        };
        viewModel.getSearchUserSuccess().observe(this, getSearchUserSuccess);


    }

    @Override
    protected void setOnClickListener() {
        manageSalesPersonAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<User.result>() {
            @Override
            public void OnItemChildClick(View viewChild, User.result subCategory, int position) {
                switch (viewChild.getId()) {
                    case R.id.editImageView:
                        if (subCategory.getRole().equalsIgnoreCase("2")) {
                            Intent intent = new Intent(getActivity(), EditManageSalesPersonActivity.class);
                            intent.putExtra(AppConstants.Extras.MANAGE_SALES_ID, subCategory.getId());
                            startActivity(intent);
                        } else {
                            showToast("Only Sales Person Will be Editable");
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        viewBinding.addMaterialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                intent.putExtra("from", "2");
                startActivity(intent);
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
                            viewModel.getUser(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else {
                            viewModel.getUserSearch(itemPerPageInProduct, String.valueOf(count), searchData, "application/json", "application/json",
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
                count = 1;
                dataStoreHomeArrayList.clear();
                viewModel.getUser(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
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
        count = 1;
        dataStoreHomeArrayList.clear();
        viewModel.getUser(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
        showProgressDialog();
    }
}