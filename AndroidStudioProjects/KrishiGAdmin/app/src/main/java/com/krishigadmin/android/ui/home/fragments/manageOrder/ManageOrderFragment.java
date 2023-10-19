package com.krishigadmin.android.ui.home.fragments.manageOrder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.api.ApiService;
import com.krishigadmin.android.data.remote.helper.RetrofitClient;
import com.krishigadmin.android.databinding.FragmentManageOrderBinding;
import com.krishigadmin.android.model.Order;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseFragment;
import com.krishigadmin.android.ui.home.fragments.EditSalesPerson.EditManageSalesPersonActivity;
import com.krishigadmin.android.ui.home.fragments.manageOrder.adapter.ManageOrdersAdapter;
import com.krishigadmin.android.ui.home.fragments.manageOrder.orderDetail.OrderDetailActivity;
import com.krishigadmin.android.ui.home.view.HomeActivity;
import com.krishigadmin.android.ui.login.view.LoginActivity;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.utilities.activity.ActivityUtils;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ManageOrderFragment extends BaseFragment<FragmentManageOrderBinding> {
    private ApiService apiService;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    String itemPerPageInProduct = "15";
    int totalPages = 0, count = 1;
    ManageOrdersAdapter manageProductAdapter;
    ArrayList<Order.Content> homeArrayList = new ArrayList<Order.Content>();
    ArrayList<Order.Content> DataArrayList = new ArrayList<Order.Content>();
    boolean visible = true;

    String orderId = "", createdDate = "", outOfDeliveryDate = "", deliveredDate = "", status = "", searchData = "";

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(getActivity());
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected FragmentManageOrderBinding getViewBinding() {
        return FragmentManageOrderBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViewModel() {

    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {

        setRecyclerView(viewBinding.recyclerView);
        DataArrayList.clear();
        count = 1;
        getAllOrder(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
        showProgressDialog();
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(LayoutManagerUtils.getGridLayoutManagerVertical(getContext(), 1));
        recyclerView.setHasFixedSize(true);
        manageProductAdapter = new ManageOrdersAdapter(getContext());
        manageProductAdapter.addArrayList(homeArrayList);
        recyclerView.setAdapter(manageProductAdapter);
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
                  /*  DataArrayList.clear();
                    count = 1;
                    getAllOrder(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                            sharedPreferencesHelper.getKeyToken());
                    showProgressDialog();*/
                } else {
                    DataArrayList.clear();
                    count = 1;
                    searchOrder(itemPerPageInProduct, String.valueOf(count), searchData, "application/json", "application/json",
                            sharedPreferencesHelper.getKeyToken());
                }

            }
        });

        viewBinding.orderIdTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                orderId = editable.toString();
                count = 1;
                DataArrayList.clear();
                searchData = "";
                viewBinding.searchEditText.setText("");
                orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());

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
        viewBinding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    ++count;
                    if (count <= totalPages) {
                        viewBinding.idPBLoading.setVisibility(View.VISIBLE);
                        if (!orderId.equalsIgnoreCase("")) {
                            orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (!createdDate.equalsIgnoreCase("")) {
                            orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (!outOfDeliveryDate.equalsIgnoreCase("")) {
                            orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (!deliveredDate.equalsIgnoreCase("")) {
                            orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (!status.equalsIgnoreCase("")) {
                            orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (!searchData.equalsIgnoreCase("")) {
                            searchOrder(itemPerPageInProduct, String.valueOf(count), searchData, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else {
                            Log.e("", "scroller--------------------------------");
                            getAllOrder(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                        }
                    }

                }
            }
        });

        manageProductAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<Order.Content>() {
            @Override
            public void OnItemChildClick(View viewChild, Order.Content content, int position) {
                switch (viewChild.getId()) {
                    case R.id.editImageView:
                        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                        intent.putExtra(AppConstants.Extras.ORDER_ID, content.getId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        viewBinding.filterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visible) {
                    visible = false;
                    viewBinding.linear.setVisibility(View.VISIBLE);
                } else {
                    visible = true;
                    viewBinding.linear.setVisibility(View.GONE);
                }
            }
        });

        viewBinding.clearTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBinding.orderIdTextInputEditText.setText("");
                viewBinding.createdDateAppCompatAutoCompleteTextView.setText("");
                viewBinding.outOfDeliveryDateAppCompatAutoCompleteTextView.setText("");
                viewBinding.deliveredDateAppCompatAutoCompleteTextView.setText("");
                viewBinding.statusAppCompatAutoCompleteTextView.setText("");
                orderId = "";
                createdDate = "";
                outOfDeliveryDate = "";
                deliveredDate = "";
                status = "";
                count = 1;
                DataArrayList.clear();
                getAllOrder(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();
            }
        });


        viewBinding.createdDateAppCompatAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = "";
                        String date = "";
                        if (monthOfYear < 10) {
                            month = "0" + (monthOfYear + 1);
                        } else {
                            month = "" + (monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {
                            date = "0" + String.valueOf(dayOfMonth);
                        } else {
                            date = String.valueOf(dayOfMonth);
                        }
                        viewBinding.createdDateAppCompatAutoCompleteTextView.setText(year + "-" + month + "-" + date);
                        createdDate = viewBinding.createdDateAppCompatAutoCompleteTextView.getText().toString();
                        count = 1;
                        DataArrayList.clear();
                        searchData = "";
                        viewBinding.searchEditText.setText("");
                        orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                                sharedPreferencesHelper.getKeyToken());
                    }
                },
                        year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        viewBinding.outOfDeliveryDateAppCompatAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = "";
                        String date = "";
                        if (monthOfYear < 10) {
                            month = "0" + (monthOfYear + 1);
                        } else {
                            month = "" + (monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {
                            date = "0" + String.valueOf(dayOfMonth);
                        } else {
                            date = String.valueOf(dayOfMonth);
                        }
                        viewBinding.outOfDeliveryDateAppCompatAutoCompleteTextView.setText(year + "-" + month + "-" + date);
                        outOfDeliveryDate = viewBinding.outOfDeliveryDateAppCompatAutoCompleteTextView.getText().toString();
                        count = 1;
                        DataArrayList.clear();
                        searchData = "";
                        viewBinding.searchEditText.setText("");
                        orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                                sharedPreferencesHelper.getKeyToken());
                    }
                },
                        year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        viewBinding.deliveredDateAppCompatAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = "";
                        String date = "";
                        if (monthOfYear < 10) {
                            month = "0" + (monthOfYear + 1);
                        } else {
                            month = "" + (monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {
                            date = "0" + String.valueOf(dayOfMonth);
                        } else {
                            date = String.valueOf(dayOfMonth);
                        }
                        viewBinding.deliveredDateAppCompatAutoCompleteTextView.setText(year + "-" + month + "-" + date);
                        deliveredDate = viewBinding.deliveredDateAppCompatAutoCompleteTextView.getText().toString();
                        count = 1;
                        DataArrayList.clear();
                        searchData = "";
                        viewBinding.searchEditText.setText("");
                        orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                                sharedPreferencesHelper.getKeyToken());
                    }
                },
                        year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

/*
        viewBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio1:
                        status = "OPEN";
                        break;
                    case R.id.radio2:
                        status = "PACKAGING";
                        break;
                    case R.id.radio3:
                        status = "READY_TO_DISPATCH";
                        break;
                    case R.id.radio4:
                        status = "OUT_OF_DELIVERED";
                        break;
                    case R.id.radio5:
                        status = "DELIVERED";
                        break;
                }
                count = 1;
                int itemPerPageInt = Integer.parseInt(itemPerPageInProduct);
                orderFilter(String.valueOf(itemPerPageInt * totalPages), String.valueOf(count),orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
            }
        });
*/

        viewBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewBinding.refreshLayout.setRefreshing(false);
                viewBinding.orderIdTextInputEditText.setText("");
                viewBinding.createdDateAppCompatAutoCompleteTextView.setText("");
                viewBinding.outOfDeliveryDateAppCompatAutoCompleteTextView.setText("");
                viewBinding.deliveredDateAppCompatAutoCompleteTextView.setText("");
                viewBinding.statusAppCompatAutoCompleteTextView.setText("");
                orderId = "";
                createdDate = "";
                outOfDeliveryDate = "";
                deliveredDate = "";
                status = "";
                viewBinding.searchEditText.setText("");
                DataArrayList.clear();
                count = 1;
                getAllOrder(itemPerPageInProduct, String.valueOf(count), "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();


            }
        });

        viewBinding.statusAppCompatAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (viewBinding.statusAppCompatAutoCompleteTextView.getText().toString().equalsIgnoreCase("OPEN")) {
                    status = "OPEN";
                } else if (viewBinding.statusAppCompatAutoCompleteTextView.getText().toString().equalsIgnoreCase("PACKAGING")) {
                    status = "PACKAGING";
                } else if (viewBinding.statusAppCompatAutoCompleteTextView.getText().toString().equalsIgnoreCase("READY TO DISPATCH")) {
                    status = "READY_TO_DISPATCH";
                } else if (viewBinding.statusAppCompatAutoCompleteTextView.getText().toString().equalsIgnoreCase("OUT OF DELIVERED")) {
                    status = "OUT_OF_DELIVERED";
                } else if (viewBinding.statusAppCompatAutoCompleteTextView.getText().toString().equalsIgnoreCase("DELIVERED")) {
                    status = "DELIVERED";
                }
                count = 1;
                DataArrayList.clear();
                searchData = "";
                viewBinding.searchEditText.setText("");
                orderFilter(itemPerPageInProduct, String.valueOf(count), orderId, createdDate, outOfDeliveryDate, deliveredDate, status, "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
            }
        });
    }

    private static final String[] gender = new String[]{
            "OPEN", "PACKAGING", "READY TO DISPATCH",
            "OUT OF DELIVERED", "DELIVERED"
    };


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

    private void getAllOrder(String items_per_page, String page_number, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Order>> call = apiService.getAllOrder(items_per_page, page_number, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Order>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Order>> call, Response<ApiResponseObject<Order>> response) {
                ApiResponseObject<Order> categories = response.body();
                hideProgressDialog();
                if (categories.getData() != null) {
                    totalPages = categories.getData().getTotalPages();
                    viewBinding.idPBLoading.setVisibility(View.GONE);
                    if (categories.getData().getContent().size() == 0) {
                        viewBinding.recyclerView.setVisibility(View.GONE);
                        viewBinding.errorImageView.setVisibility(View.VISIBLE);
                        viewBinding.errorTextView.setVisibility(View.VISIBLE);
                    } else {
                        viewBinding.recyclerView.setVisibility(View.VISIBLE);
                        viewBinding.errorImageView.setVisibility(View.GONE);
                        viewBinding.errorTextView.setVisibility(View.GONE);
                        homeArrayList = categories.getData().getContent();
                        DataArrayList.addAll(homeArrayList);
                        manageProductAdapter.clearAllItem();
                        manageProductAdapter.replaceArrayList(DataArrayList);
                    }
                } else {

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
            public void onFailure(Call<ApiResponseObject<Order>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }

    private void searchOrder(String items_per_page,
                             String page_number, String orderId, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Order>> call = apiService.searchOrder(items_per_page, page_number, orderId, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Order>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Order>> call, Response<ApiResponseObject<Order>> response) {
                ApiResponseObject<Order> categories = response.body();
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (categories.getData() != null) {
                    totalPages = categories.getData().getTotalPages();
                    if (categories.getData().getContent().size() == 0) {
                        viewBinding.recyclerView.setVisibility(View.GONE);
                        viewBinding.errorImageView.setVisibility(View.VISIBLE);
                        viewBinding.errorTextView.setVisibility(View.VISIBLE);
                    } else {
                        viewBinding.recyclerView.setVisibility(View.VISIBLE);
                        viewBinding.errorImageView.setVisibility(View.GONE);
                        viewBinding.errorTextView.setVisibility(View.GONE);
                        homeArrayList = categories.getData().getContent();
                        DataArrayList.addAll(homeArrayList);
                        manageProductAdapter.clearAllItem();
                        manageProductAdapter.replaceArrayList(DataArrayList);
                    }
                } else {
                  /*  viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageView.setVisibility(View.VISIBLE);
                    viewBinding.errorTextView.setVisibility(View.VISIBLE);*/
                }

            }

            @Override
            public void onFailure(Call<ApiResponseObject<Order>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }

    private void orderFilter(String items_per_page,
                             String page_number, String orderId, String createdDate, String outOfDeliveryDate, String deliveredDate, String status, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Order>> call = apiService.orderFilter(orderId, items_per_page, page_number, createdDate, outOfDeliveryDate, deliveredDate, status, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Order>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Order>> call, Response<ApiResponseObject<Order>> response) {
                ApiResponseObject<Order> categories = response.body();
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (categories.getData() != null) {
                    totalPages = categories.getData().getTotalPages();
                    if (categories.getData().getContent().size() == 0) {
                        viewBinding.recyclerView.setVisibility(View.GONE);
                        viewBinding.errorImageView.setVisibility(View.VISIBLE);
                        viewBinding.errorTextView.setVisibility(View.VISIBLE);
                    } else {
                        viewBinding.recyclerView.setVisibility(View.VISIBLE);
                        viewBinding.errorImageView.setVisibility(View.GONE);
                        viewBinding.errorTextView.setVisibility(View.GONE);
                        homeArrayList = categories.getData().getContent();
                        DataArrayList.addAll(homeArrayList);
                        manageProductAdapter.clearAllItem();
                        manageProductAdapter.replaceArrayList(DataArrayList);
                    }
                }

            }

            @Override
            public void onFailure(Call<ApiResponseObject<Order>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, gender);
        viewBinding.statusAppCompatAutoCompleteTextView.setAdapter(adapter);
    }
}