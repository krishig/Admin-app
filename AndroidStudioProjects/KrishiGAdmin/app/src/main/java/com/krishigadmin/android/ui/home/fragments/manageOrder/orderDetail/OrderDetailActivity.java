package com.krishigadmin.android.ui.home.fragments.manageOrder.orderDetail;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.RequestUtils;
import com.krishigadmin.android.data.remote.api.ApiService;
import com.krishigadmin.android.data.remote.helper.RetrofitClient;
import com.krishigadmin.android.databinding.ActivityOrderDetailBinding;
import com.krishigadmin.android.model.Order;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.home.fragments.addSubCategory.viewmodel.SubCategoryModel;
import com.krishigadmin.android.ui.home.fragments.manageOrder.orderDetail.adapter.OrderDetailAdapter;
import com.library.adapter.recyclerview.LayoutManagerUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class OrderDetailActivity extends BaseActivity<ActivityOrderDetailBinding> {
    SubCategoryModel viewModel;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    String orderId = "", status = "";
    OrderDetailAdapter manageProductAdapter;
    ArrayList<Order.ProductResponseDtos> homeArrayList = new ArrayList<Order.ProductResponseDtos>();
    boolean visible = false;
    boolean visible2 = false;
    private ApiService apiService;

    @Override
    protected ActivityOrderDetailBinding getViewBinding() {
        return ActivityOrderDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(OrderDetailActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void initializeViewModel() {
        viewModel = createViewModel(SubCategoryModel.class);

    }


    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
        if (getIntent().getExtras() != null) {
            orderId = getIntent().getExtras().getString(AppConstants.Extras.ORDER_ID);
        }
        if (orderId != null) {
            fetchOrderDetail(orderId, "application/json", "application/json",
                    sharedPreferencesHelper.getKeyToken());
            showProgressDialog();
        }
        setRecyclerView(viewBinding.seedsRecyclerView);
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(LayoutManagerUtils.getGridLayoutManagerVertical(this, 1));
        recyclerView.setHasFixedSize(true);
        manageProductAdapter = new OrderDetailAdapter(this);
        manageProductAdapter.addArrayList(homeArrayList);
        recyclerView.setAdapter(manageProductAdapter);
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

    public static String convertDateFormat(String inputDateString) {
        try {
            SimpleDateFormat inputFormat = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US);
            }
            Date date = inputFormat.parse(inputDateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void setOnClickListener() {
        viewBinding.icBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewBinding.dropDownImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visible) {
                    visible = false;
                    viewBinding.dropDownImageView.setRotation(360);
                    viewBinding.contentLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    visible = true;
                    viewBinding.dropDownImageView.setRotation(180);
                    viewBinding.contentLinearLayout.setVisibility(View.GONE);
                }
            }

        });
        viewBinding.customerDetailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visible) {
                    visible = false;
                    viewBinding.dropDownImageView.setRotation(360);
                    viewBinding.contentLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    visible = true;
                    viewBinding.dropDownImageView.setRotation(180);
                    viewBinding.contentLinearLayout.setVisibility(View.GONE);
                }
            }

        });

        viewBinding.customerAddressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visible2) {
                    visible2 = false;
                    viewBinding.dropDownAddressImageView.setRotation(360);
                    viewBinding.locationLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    visible2 = true;
                    viewBinding.dropDownAddressImageView.setRotation(180);
                    viewBinding.locationLinearLayout.setVisibility(View.GONE);
                }
            }

        });
        viewBinding.dropDownAddressImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visible2) {
                    visible2 = false;
                    viewBinding.dropDownImageView.setRotation(360);
                    viewBinding.locationLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    visible2 = true;
                    viewBinding.dropDownImageView.setRotation(180);
                    viewBinding.locationLinearLayout.setVisibility(View.GONE);
                }
            }

        });


        viewBinding.updateStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("status", status);
                    RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject.toString());
                    updateOrderDetail(orderId, requestBody, "application/json", "application/json",
                            sharedPreferencesHelper.getKeyToken());
                    showProgressDialog();
                } catch (Exception exception) {
                    System.out.println(exception);
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

    private void fetchOrderDetail(String orderId, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Order>> call = apiService.fetchOrderDetail(orderId, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Order>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Order>> call, Response<ApiResponseObject<Order>> response) {
                ApiResponseObject<Order> order = response.body();
                hideProgressDialog();
                if (order.getData() != null) {
                    status = order.getData().getStatus();
                    if (status.equalsIgnoreCase("DELIVERED")) {
                        viewBinding.updateStatusButton.setVisibility(View.GONE);
                    } else {
                        viewBinding.updateStatusButton.setVisibility(View.VISIBLE);
                    }
                    viewBinding.categoryTextView.setText(order.getData().getOrderId());
                    viewBinding.categoryPriceTextView.setText("Rs. " + order.getData().getTotalPrice());
                    viewBinding.statusTextView.setText(order.getData().getStatus());
                    viewBinding.contactNumberTextView.setText(order.getData().getContactNumber());

                    String inputDateString = order.getData().getCreatedDate();
                    String formattedDate = convertDateFormat(inputDateString);

                    viewBinding.dateTextView.setText(formattedDate);

                    viewBinding.customerNameTextView.setText("Name - " + order.getData().getCustomerId().getFullName());
                    viewBinding.customerMobileNumberTextView.setText("Contact Number - " + order.getData().getCustomerId().getMobileNumber());

                    if (order.getData().getAddressResponseDto() != null) {
                        viewBinding.locationTextView.setText(order.getData().getAddressResponseDto().getHouseNumber() + ","
                                + order.getData().getAddressResponseDto().getStreetName() + ","
                                + order.getData().getAddressResponseDto().getVillageName() + ","
                                + order.getData().getAddressResponseDto().getDistrict() + ","
                                + order.getData().getAddressResponseDto().getState() + ","
                                + order.getData().getAddressResponseDto().getPostalCode()
                        );
                    }
                    homeArrayList = order.getData().getProductResponseDtos();
                    manageProductAdapter.clearAllItem();
                    manageProductAdapter.replaceArrayList(homeArrayList);
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

    private void updateOrderDetail(String orderId, RequestBody requestBody, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Order>> call = apiService.updateOrderDetail(orderId, requestBody, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Order>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Order>> call, Response<ApiResponseObject<Order>> response) {
                ApiResponseObject<Order> order = response.body();
                hideProgressDialog();
                finish();
            }

            @Override
            public void onFailure(Call<ApiResponseObject<Order>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }

}