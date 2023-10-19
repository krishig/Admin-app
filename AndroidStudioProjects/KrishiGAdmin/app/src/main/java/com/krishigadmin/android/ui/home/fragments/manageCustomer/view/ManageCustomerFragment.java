package com.krishigadmin.android.ui.home.fragments.manageCustomer.view;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.api.ApiService;
import com.krishigadmin.android.data.remote.helper.RetrofitClient;
import com.krishigadmin.android.databinding.FragmentManageCustomerBinding;
import com.krishigadmin.android.model.Customer;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseFragment;
import com.krishigadmin.android.ui.home.fragments.addSubCategory.viewmodel.SubCategoryModel;
import com.krishigadmin.android.ui.home.fragments.manageCustomer.adapter.ManageCustomerAdapter;
import com.krishigadmin.android.ui.home.fragments.manageCustomer.addCustomer.AddCustomerActivity;
import com.krishigadmin.android.ui.login.view.LoginActivity;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.utilities.activity.ActivityUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ManageCustomerFragment extends BaseFragment<FragmentManageCustomerBinding> {
    ManageCustomerAdapter manageCustomerAdapter;
    ArrayList<Customer.Content> customerArrayList = new ArrayList<>();
    ArrayList<Customer.Content> DataArrayList = new ArrayList<>();

    SubCategoryModel viewModel;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    int totalPages = 0, count = 1;
    String itemPerPageInProduct = "15", searchData = "";
    private ApiService apiService;

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(getActivity());
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected FragmentManageCustomerBinding getViewBinding() {
        return FragmentManageCustomerBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViewModel() {

    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
        setRecyclerView();

    }

    private void setRecyclerView() {
        viewBinding.recyclerView.setHasFixedSize(true);
        viewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        manageCustomerAdapter = new ManageCustomerAdapter(getContext());
        manageCustomerAdapter.clearAllItem();
        manageCustomerAdapter.addArrayList(customerArrayList);
        viewBinding.recyclerView.setAdapter(manageCustomerAdapter);
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
                    getCustomer(String.valueOf(count), itemPerPageInProduct, "application/json", "application/json",
                            sharedPreferencesHelper.getKeyToken());
                    showProgressDialog();
                } else {
                    DataArrayList.clear();
                    count = 1;
                    searchCustomer(String.valueOf(count), itemPerPageInProduct,editable.toString(), "application/json", "application/json",
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

    }

    @Override
    protected void setOnClickListener() {

        manageCustomerAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<Customer.Content>() {
            @Override
            public void OnItemChildClick(View viewChild, Customer.Content content, int position) {
                switch (viewChild.getId()) {
                    case R.id.editImageView:
                        String addressId = null;
                        if (content.getAddress().size() != 0) {
                            addressId = content.getAddress().get(0).getId();
                        }
                        Intent intent = new Intent(getActivity(), AddCustomerActivity.class);
                        intent.putExtra(AppConstants.Extras.CUSTOMER_ID, content.getId());
                        intent.putExtra(AppConstants.Extras.ADDRESS_ID, addressId);
                        intent.putExtra(AppConstants.Extras.CUSTOMER_FROM, "1");
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
                            getCustomer(String.valueOf(count), itemPerPageInProduct, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else {
                            searchCustomer(String.valueOf(count), itemPerPageInProduct,searchData, "application/json", "application/json",
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
                getCustomer(String.valueOf(count), itemPerPageInProduct, "application/json", "application/json",
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
        DataArrayList.clear();
        count = 1;
        getCustomer(String.valueOf(count), itemPerPageInProduct, "application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
        showProgressDialog();
    }

    private void getCustomer(String pageNumber, String pageSize, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Customer>> call = apiService.getCustomer(pageNumber, pageSize, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Customer>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Customer>> call, Response<ApiResponseObject<Customer>> response) {
                ApiResponseObject<Customer> list = response.body();
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (list.getData() != null) {
                    if (list.getData().getContent().size() != 0) {
                        viewBinding.recyclerView.setVisibility(View.VISIBLE);
                        viewBinding.errorImageView.setVisibility(View.GONE);
                        viewBinding.errorTextView.setVisibility(View.GONE);
                        totalPages = list.getData().getTotalPages();
                        customerArrayList = list.getData().getContent();
                        DataArrayList.addAll(customerArrayList);
                        manageCustomerAdapter.clearAllItem();
                        manageCustomerAdapter.addArrayList(DataArrayList);
                    }else{
                        viewBinding.recyclerView.setVisibility(View.GONE);
                        viewBinding.errorImageView.setVisibility(View.VISIBLE);
                        viewBinding.errorTextView.setVisibility(View.VISIBLE);
                    }
                }else{
                    if (response.body().getMessage().equalsIgnoreCase("Please login again!")) ;
                    {
                        sharedPreferencesHelper.setRemember(false);
                        Intent intent = ActivityUtils.launchActivityWithClearBackStack(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ApiResponseObject<Customer>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }


    private void searchCustomer(String pageNumber,
                                String pageSize,String findByMobile, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Customer>> call = apiService.searchCustomer(pageNumber,pageSize,findByMobile, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Customer>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Customer>> call, Response<ApiResponseObject<Customer>> response) {
                ApiResponseObject<Customer> list = response.body();
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (list.getData() != null) {
                    if (list.getData().getContent().size() != 0) {
                        viewBinding.recyclerView.setVisibility(View.VISIBLE);
                        viewBinding.errorImageView.setVisibility(View.GONE);
                        viewBinding.errorTextView.setVisibility(View.GONE);
                        totalPages = list.getData().getTotalPages();
                        customerArrayList = list.getData().getContent();
                        DataArrayList.addAll(customerArrayList);
                        manageCustomerAdapter.clearAllItem();
                        manageCustomerAdapter.addArrayList(DataArrayList);
                    }else{
                        viewBinding.recyclerView.setVisibility(View.GONE);
                        viewBinding.errorImageView.setVisibility(View.VISIBLE);
                        viewBinding.errorTextView.setVisibility(View.VISIBLE);
                    }
                }else{
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ApiResponseObject<Customer>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }

}