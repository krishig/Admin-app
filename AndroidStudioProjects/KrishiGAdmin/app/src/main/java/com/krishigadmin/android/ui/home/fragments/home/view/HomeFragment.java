package com.krishigadmin.android.ui.home.fragments.home.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.api.ApiService;
import com.krishigadmin.android.data.remote.helper.RetrofitClient;
import com.krishigadmin.android.databinding.FragmentHomeBinding;
import com.krishigadmin.android.model.Home;
import com.krishigadmin.android.model.Order;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseFragment;
import com.krishigadmin.android.ui.home.fragments.home.adapter.HomeAdapter;
import com.krishigadmin.android.ui.home.fragments.manageOrder.orderDetail.OrderDetailActivity;
import com.krishigadmin.android.ui.home.view.HomeActivity;
import com.krishigadmin.android.ui.login.view.LoginActivity;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.utilities.activity.ActivityUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    HomeAdapter homeAdapter;
    ArrayList<Home.OrderResponseDto.Content> homeArrayList = new ArrayList<Home.OrderResponseDto.Content>();
    ArrayList<Home.OrderResponseDto.Content> DataArrayList = new ArrayList<Home.OrderResponseDto.Content>();

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    private ApiService apiService;

    String date = "", userName = "";

    String cardSelected = "";


    int totalPages = 0, count = 1;
    String itemPerPageInProduct = "15";

    @Override
    protected FragmentHomeBinding getViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initializeViewModel() {

    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
        userName = extractUsernameFromJWT(sharedPreferencesHelper.getKeyToken());

        System.out.println(userName);
        System.out.println(sharedPreferencesHelper.getKeyToken());
        viewBinding.nameTextView.setText(userName);

        setRecyclerView(viewBinding.recyclerView);

    }

    public static String extractUsernameFromJWT(String jwt) {
        try {
            String[] jwtParts = jwt.split("\\.");
            if (jwtParts.length != 3) {
                return null;
            }
            // Decode the payload
            String payloadJson = new String(Base64.decode(jwtParts[1], Base64.URL_SAFE), "UTF-8");
            // Parse the payload as a JSON object
            JSONObject payload = new JSONObject(payloadJson);
            System.out.println("payload " + payload);
            // Extract the "username" claim
            String username = payload.optString("username", null);
            // Return the "username" value as a String
            return username;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        homeAdapter = new HomeAdapter(getContext());
        homeAdapter.addArrayList(homeArrayList);
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    protected void addTextChangedListener() {

    }

    @Override
    protected void setOnFocusChangeListener() {

    }

    @Override
    protected void observeViewModel() {
        cardSelected = "outOfDelivered";
        Calendar calendar;
        SimpleDateFormat dateFormat;
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            viewBinding.dateDetailTextView.setText("0" + String.valueOf(day));
        } else {
            viewBinding.dateDetailTextView.setText(String.valueOf(day));
        }
        if (month < 10) {
            dateFormat = new SimpleDateFormat(year + "-" + "0" + (month + 1) + "-" + day);
        } else {
            dateFormat = new SimpleDateFormat(year + "-" + (month + 1) + "-" + day);
        }
        date = dateFormat.format(calendar.getTime());

        viewBinding.outOfDeliveryDetailTextView.setTextColor(getResources().getColor(R.color.white));
        viewBinding.outOfDeliveryTextView.setTextColor(getResources().getColor(R.color.white));
        viewBinding.deliveredDetailTextView.setTextColor(getResources().getColor(R.color.black));
        viewBinding.placedOrderDetailTextView.setTextColor(getResources().getColor(R.color.black));
        viewBinding.pendingOrderDetailTextView.setTextColor(getResources().getColor(R.color.black));
        viewBinding.deliveredTextView.setTextColor(getResources().getColor(R.color.home_grey));
        viewBinding.placedOrderTextView.setTextColor(getResources().getColor(R.color.home_grey));
        viewBinding.pendingOrderTextView.setTextColor(getResources().getColor(R.color.home_grey));


        viewBinding.recentOrdersTextView.setText("Out Of Delivery");
        viewBinding.outOfDeliveryLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_red_stroke_background));
        viewBinding.deliveredLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
        viewBinding.totalCashLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
        viewBinding.placedOrderLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
        viewBinding.contentLinearLayout3.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
        DataArrayList.clear();
        count = 1;
        getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "OUT_OF_DELIVERED", "application/json", "application/json",
                sharedPreferencesHelper.getKeyToken());
    }

    @Override
    protected void setOnClickListener() {
        viewBinding.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (dayOfMonth < 10) {
                            viewBinding.dateDetailTextView.setText("0" + String.valueOf(dayOfMonth));
                        } else {
                            viewBinding.dateDetailTextView.setText(String.valueOf(dayOfMonth));
                        }
                        if (monthOfYear < 10) {
                            date = year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth;
                        } else {
                            date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        }
                        DataArrayList.clear();
                        count = 1;

                        if (cardSelected.equalsIgnoreCase("outOfDelivered")) {
                            getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "OUT_OF_DELIVERED", "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (cardSelected.equalsIgnoreCase("delivered")) {
                            getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "DELIVERED", "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (cardSelected.equalsIgnoreCase("placedOrder")) {
                            getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "OPEN", "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (cardSelected.equalsIgnoreCase("pendingOrder")) {
                            getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "PENDING_DELIVER_ORDER", "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        }

                    }
                },
                        year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        viewBinding.outOfDeliveryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardSelected = "outOfDelivered";
                viewBinding.recentOrdersTextView.setText("Out Of Delivery");
                viewBinding.outOfDeliveryDetailTextView.setTextColor(getResources().getColor(R.color.white));
                viewBinding.outOfDeliveryTextView.setTextColor(getResources().getColor(R.color.white));
                viewBinding.deliveredDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.placedOrderDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.pendingOrderDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.deliveredTextView.setTextColor(getResources().getColor(R.color.home_grey));
                viewBinding.placedOrderTextView.setTextColor(getResources().getColor(R.color.home_grey));
                viewBinding.pendingOrderTextView.setTextColor(getResources().getColor(R.color.home_grey));


                viewBinding.outOfDeliveryLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_red_stroke_background));
                viewBinding.deliveredLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.totalCashLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.placedOrderLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.contentLinearLayout3.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                DataArrayList.clear();
                count = 1;
                getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "OUT_OF_DELIVERED", "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
            }
        });
        viewBinding.deliveredLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardSelected = "delivered";
                viewBinding.recentOrdersTextView.setText("Delivered");

                viewBinding.deliveredDetailTextView.setTextColor(getResources().getColor(R.color.white));
                viewBinding.deliveredTextView.setTextColor(getResources().getColor(R.color.white));
                viewBinding.outOfDeliveryDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.placedOrderDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.pendingOrderDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.outOfDeliveryTextView.setTextColor(getResources().getColor(R.color.home_grey));
                viewBinding.placedOrderTextView.setTextColor(getResources().getColor(R.color.home_grey));
                viewBinding.pendingOrderTextView.setTextColor(getResources().getColor(R.color.home_grey));


                viewBinding.outOfDeliveryLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.deliveredLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_red_stroke_background));
                viewBinding.totalCashLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.placedOrderLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.contentLinearLayout3.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                DataArrayList.clear();
                count = 1;
                getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "DELIVERED", "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
            }
        });
        viewBinding.placedOrderLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardSelected = "placedOrder";
                viewBinding.recentOrdersTextView.setText("Placed Order");

                viewBinding.placedOrderDetailTextView.setTextColor(getResources().getColor(R.color.white));
                viewBinding.placedOrderTextView.setTextColor(getResources().getColor(R.color.white));
                viewBinding.outOfDeliveryDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.deliveredDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.pendingOrderDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.outOfDeliveryTextView.setTextColor(getResources().getColor(R.color.home_grey));
                viewBinding.deliveredTextView.setTextColor(getResources().getColor(R.color.home_grey));
                viewBinding.pendingOrderTextView.setTextColor(getResources().getColor(R.color.home_grey));


                viewBinding.outOfDeliveryLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.deliveredLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.totalCashLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.placedOrderLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_red_stroke_background));
                viewBinding.contentLinearLayout3.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                DataArrayList.clear();
                count = 1;
                getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "OPEN", "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
            }
        });
        viewBinding.contentLinearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardSelected = "pendingOrder";
                viewBinding.recentOrdersTextView.setText("Pending Order");

                viewBinding.pendingOrderDetailTextView.setTextColor(getResources().getColor(R.color.white));
                viewBinding.pendingOrderTextView.setTextColor(getResources().getColor(R.color.white));
                viewBinding.outOfDeliveryDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.deliveredDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.placedOrderDetailTextView.setTextColor(getResources().getColor(R.color.black));
                viewBinding.outOfDeliveryTextView.setTextColor(getResources().getColor(R.color.home_grey));
                viewBinding.deliveredTextView.setTextColor(getResources().getColor(R.color.home_grey));
                viewBinding.placedOrderTextView.setTextColor(getResources().getColor(R.color.home_grey));

                viewBinding.outOfDeliveryLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.deliveredLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.totalCashLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.placedOrderLinearLayout.setBackground(getResources().getDrawable(R.drawable.card_green_stroke_background));
                viewBinding.contentLinearLayout3.setBackground(getResources().getDrawable(R.drawable.card_red_stroke_background));
                DataArrayList.clear();
                count = 1;
                getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "PENDING_DELIVER_ORDER", "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
            }
        });

        viewBinding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    ++count;
                    if (count <= totalPages) {
                        viewBinding.idPBLoading.setVisibility(View.VISIBLE);
                        if (cardSelected.equalsIgnoreCase("outOfDelivered")) {
                            getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "OUT_OF_DELIVERED", "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (cardSelected.equalsIgnoreCase("delivered")) {
                            getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "DELIVERED", "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (cardSelected.equalsIgnoreCase("placedOrder")) {
                            getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "OPEN", "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        } else if (cardSelected.equalsIgnoreCase("pendingOrder")) {
                            getProductFilterFertiliser(itemPerPageInProduct, String.valueOf(count), date, "PENDING_DELIVER_ORDER", "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                        }
                    }

                }
            }
        });

        homeAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<Home.OrderResponseDto.Content>() {
            @Override
            public void OnItemChildClick(View viewChild, Home.OrderResponseDto.Content content, int position) {
                switch (viewChild.getId()) {
                    case R.id.viewDetailLinearLayout:
                        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                        intent.putExtra(AppConstants.Extras.ORDER_ID, content.getId());
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

   /*     viewBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewBinding.refreshLayout.setRefreshing(false);
            }
        });*/
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(getActivity());
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
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
//            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void hideProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            //getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void getProductFilterFertiliser(String items_per_page, String page_number, String date, String status, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Home>> call = apiService.getHomeData(date, items_per_page, page_number, status, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Home>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Home>> call, Response<ApiResponseObject<Home>> response) {
                ApiResponseObject<Home> categories = response.body();
                hideProgressDialog();
                viewBinding.idPBLoading.setVisibility(View.GONE);
                if (categories.getData() != null) {
                    if (categories.getData().getOrderResponseDto() != null) {
                        totalPages = categories.getData().getOrderResponseDto().getTotalPages();
                        int size = categories.getData().getOrderResponseDto().getContent().size();
                        if (size == 0) {
                            viewBinding.recyclerView.setVisibility(View.GONE);
                            viewBinding.errorImageViewSeeds.setVisibility(View.VISIBLE);
                            viewBinding.errorTextViewSeeds.setVisibility(View.VISIBLE);
                        } else {
                            viewBinding.recyclerView.setVisibility(View.VISIBLE);
                            viewBinding.errorImageViewSeeds.setVisibility(View.GONE);
                            viewBinding.errorTextViewSeeds.setVisibility(View.GONE);
                            homeArrayList = categories.getData().getOrderResponseDto().getContent();
                            DataArrayList.addAll(homeArrayList);
                            homeAdapter.clearAllItem();
                            homeAdapter.replaceArrayList(DataArrayList);
                        }
                        viewBinding.outOfDeliveryDetailTextView.setText(categories.getData().getOutOfDeliveryCount());
                        viewBinding.deliveredDetailTextView.setText(categories.getData().getDeliveredCount());
                        viewBinding.totalCashDetailTextView.setText(categories.getData().getTotalCash());
                        viewBinding.placedOrderDetailTextView.setText(categories.getData().getTotalOrderCount());
                        viewBinding.pendingOrderDetailTextView.setText(categories.getData().getPendingDeliveryCount());
                    }
                } else {
                    viewBinding.recyclerView.setVisibility(View.GONE);
                    viewBinding.errorImageViewSeeds.setVisibility(View.VISIBLE);
                    viewBinding.errorTextViewSeeds.setVisibility(View.VISIBLE);
                    sharedPreferencesHelper.setRemember(false);
                    Intent intent = ActivityUtils.launchActivityWithClearBackStack(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseObject<Home>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}