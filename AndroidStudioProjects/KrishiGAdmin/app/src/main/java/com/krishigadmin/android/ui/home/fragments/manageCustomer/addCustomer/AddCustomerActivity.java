package com.krishigadmin.android.ui.home.fragments.manageCustomer.addCustomer;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.RequestUtils;
import com.krishigadmin.android.data.remote.api.ApiService;
import com.krishigadmin.android.data.remote.helper.RetrofitClient;
import com.krishigadmin.android.databinding.ActivityAddCustomerBinding;
import com.krishigadmin.android.model.Customer;
import com.krishigadmin.android.model.PostalPincodeResponse;
import com.krishigadmin.android.searchdialog.Search;
import com.krishigadmin.android.searchdialog.SearchUtils;
import com.krishigadmin.android.searchdialog.SelectListener;
import com.krishigadmin.android.ui.AppConstants;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.library.utilities.ValidationUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class AddCustomerActivity extends BaseActivity<ActivityAddCustomerBinding> {

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    private ApiService postalPincodeApi;

    String customerId = "", from = "", addressId = "";
    private ApiService apiService;
    String data = "";
    private ArrayList<Search> vehicleTypeSearchArrayList = new ArrayList<Search>();
    private ArrayList<PostalPincodeResponse.PostOffice> vehicleTypeArraylist = new ArrayList<>();
    @Override
    protected ActivityAddCustomerBinding getViewBinding() {
        return ActivityAddCustomerBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(AddCustomerActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void initializeViewModel() {

    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
        if (getIntent().getExtras() != null) {
            customerId = getIntent().getExtras().getString(AppConstants.Extras.CUSTOMER_ID);
            from = getIntent().getExtras().getString(AppConstants.Extras.CUSTOMER_FROM);
            addressId = getIntent().getExtras().getString(AppConstants.Extras.ADDRESS_ID);
            Log.e("", "ok==" + from);
            if (customerId != null) {
                getCustomerWithId(customerId, "application/json", "application/json",
                        sharedPreferencesHelper.getKeyToken());
                showProgressDialog();
            }
        }

        if (from.equalsIgnoreCase("1")) {
            viewBinding.addButton.setText("Update");
        } else {
            viewBinding.addButton.setText("Add");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, gender);
        viewBinding.genderAppCompatAutoCompleteTextView.setAdapter(adapter);
    }

    @Override
    protected void addTextChangedListener() {
        viewBinding.pinCodeInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() < 1) {
                    viewBinding.pinCodeInputLayout.setErrorEnabled(true);
                    viewBinding.pinCodeInputLayout.setError("Please Enter PinCode Number");
                }
                if (text.length() > 0) {
                    viewBinding.pinCodeInputLayout.setError(null);
                    viewBinding.pinCodeInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                data = s.toString();
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
        viewBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = viewBinding.fullNameTextInputEditText.getText().toString().trim();
                String mobileNumber = viewBinding.mobileNumberTextInputEditText.getText().toString().trim();
                String gender = viewBinding.genderAppCompatAutoCompleteTextView.getText().toString().trim();
                String houseNumber = viewBinding.houseNumberTextInputEditText.getText().toString().trim();
                String streetName = viewBinding.streetNameTextInputEditText.getText().toString().trim();
                String villageName = viewBinding.villageNameTextInputEditText.getText().toString().trim();
                String pinCode = viewBinding.pinCodeTextInputEditText.getText().toString().trim();
                String district = viewBinding.districtTextInputEditText.getText().toString().trim();
                String state = viewBinding.stateTextInputEditText.getText().toString().trim();
                String block = viewBinding.blockTextInputEditText.getText().toString().trim();

                if (validation(fullName, mobileNumber, gender, houseNumber, streetName,
                        villageName, pinCode, district,block, state)) {
                    if (from.equalsIgnoreCase("1")) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("fullName", fullName);
                            jsonObject.put("mobileNumber", mobileNumber);
                            jsonObject.put("gender", gender);

                            JSONArray jsonArray = new JSONArray();
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("id", addressId);
                            jsonObject1.put("houseNumber", houseNumber);
                            jsonObject1.put("streetName", streetName);
                            jsonObject1.put("villageName", villageName);
                            jsonObject1.put("district", district);
                            jsonObject1.put("block", block);
                            jsonObject1.put("state", state);
                            jsonObject1.put("postalCode", pinCode);
                            jsonArray.put(jsonObject1);
                            jsonObject.put("address", jsonArray);

                            RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject.toString());

                            putCustomer(customerId, requestBody, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                        } catch (Exception exception) {
                            Log.e("", "" + exception);
                        }
                    } /*else if (from.equalsIgnoreCase("0")) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("fullName", fullName);
                            jsonObject.put("mobileNumber", mobileNumber);
                            jsonObject.put("gender", gender);

                            JSONArray jsonArray = new JSONArray();
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("houseNumber", houseNumber);
                            jsonObject1.put("streetName", streetName);
                            jsonObject1.put("villageName", villageName);
                            jsonObject1.put("district", district);
                            jsonObject1.put("state", state);
                            jsonObject1.put("postalCode", pinCode);
                            jsonArray.put(jsonObject1);
                            jsonObject.put("address", jsonArray);

                            RequestBody requestBody = RequestUtils.createRequestBodyForString(jsonObject.toString());

                            viewModel.addCustomer(requestBody, "application/json", "application/json",
                                    sharedPreferencesHelper.getKeyToken());
                            showProgressDialog();
                        } catch (Exception exception) {
                            Log.e("", "" + exception);
                        }
                    }*/

                }

            }
        });
        viewBinding.icBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewBinding.pinCodeInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSuperHeroes(data);
            }
        });

        viewBinding.villageNameInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vehicleTypeSearchArrayList.size() != 0) {
                    SearchUtils.searchDialog(AddCustomerActivity.this, "Select Village Name", vehicleTypeSearchArrayList, new SelectListener() {
                        @Override
                        public void onSelected(Search search, int position) {
                            viewBinding.villageNameTextInputEditText.setText(search.getItemName());

                        }
                    });
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

    private boolean validation(String fullName, String mobileNumber, String gender, String houseNumber,
                               String streetName, String villageName, String pinCode, String district,String block, String state) {
        boolean isValid = true;
        int phoneValidCode = ValidationUtils.isPhoneNumberValid(mobileNumber);

        if (fullName.equalsIgnoreCase("")) {
            viewBinding.fullNameInputLayout.setError("Please Enter ");
            viewBinding.fullNameInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.fullNameInputLayout.setError("");
            viewBinding.fullNameInputLayout.setErrorEnabled(false);
        }
        if (phoneValidCode > 0) {
            if (phoneValidCode == 1) {
                viewBinding.mobileNumberInputLayout.setError(getString(R.string.login_register_error_phone_number_one));
                showSnackBar(viewBinding.addCustomer, getString(R.string.login_register_error_phone_number_one));
                isValid = false;
            } else if (phoneValidCode == 2) {
                viewBinding.mobileNumberInputLayout.setError(getString(R.string.login_register_error_phone_number_two));
                showSnackBar(viewBinding.addCustomer, getString(R.string.login_register_error_phone_number_two));
                isValid = false;
            }
        } else {
            viewBinding.mobileNumberInputLayout.setError(null);
            viewBinding.mobileNumberInputLayout.setErrorEnabled(false);
        }
        if (gender.equalsIgnoreCase("")) {
            viewBinding.genderTextInputLayout.setError("Please Select Gender");
            viewBinding.genderTextInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.genderTextInputLayout.setError("");
            viewBinding.genderTextInputLayout.setErrorEnabled(false);
        }
        if (houseNumber.equalsIgnoreCase("")) {
            viewBinding.houseNumberInputLayout.setError("Please Enter House Number");
            viewBinding.houseNumberInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.houseNumberInputLayout.setError("");
            viewBinding.houseNumberInputLayout.setErrorEnabled(false);
        }
        if (streetName.equalsIgnoreCase("")) {
            viewBinding.streetNameInputLayout.setError("Please Enter Street Name");
            viewBinding.streetNameInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.streetNameInputLayout.setError("");
            viewBinding.streetNameInputLayout.setErrorEnabled(false);
        }
        if (villageName.equalsIgnoreCase("")) {
            viewBinding.villageNameInputLayout.setError("Please Enter Village Name");
            viewBinding.villageNameInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.villageNameInputLayout.setError("");
            viewBinding.villageNameInputLayout.setErrorEnabled(false);
        }
        if (pinCode.equalsIgnoreCase("")) {
            viewBinding.pinCodeInputLayout.setError("Please Enter PinCode Number");
            viewBinding.pinCodeInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.pinCodeInputLayout.setError("");
            viewBinding.pinCodeInputLayout.setErrorEnabled(false);
        }
        if (block.equalsIgnoreCase("")) {
            viewBinding.blockInputLayout.setError("Please Enter District");
            viewBinding.blockInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.blockInputLayout.setError("");
            viewBinding.blockInputLayout.setErrorEnabled(false);
        }
        if (district.equalsIgnoreCase("")) {
            viewBinding.districtInputLayout.setError("Please Enter District");
            viewBinding.districtInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.districtInputLayout.setError("");
            viewBinding.districtInputLayout.setErrorEnabled(false);
        }
        if (state.equalsIgnoreCase("")) {
            viewBinding.stateInputLayout.setError("Please Enter State");
            viewBinding.stateInputLayout.setErrorEnabled(true);
            isValid = false;
        } else {
            viewBinding.stateInputLayout.setError("");
            viewBinding.stateInputLayout.setErrorEnabled(false);
        }
        return isValid;
    }

    private static final String[] gender = new String[]{
            "Male", "Female", "Other"
    };


    private void getSuperHeroes(String data) {
        postalPincodeApi = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        // Example API call
        Call<ArrayList<PostalPincodeResponse>> call = postalPincodeApi.getPincodeDetails(data);
        call.enqueue(new Callback<ArrayList<PostalPincodeResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PostalPincodeResponse>> call, Response<ArrayList<PostalPincodeResponse>> response) {

                ArrayList<PostalPincodeResponse> postalPincodeResponse = response.body();

                if (postalPincodeResponse != null) {
                    if (postalPincodeResponse.get(0).getPostOffice() != null) {
                        viewBinding.villageNameTextInputEditText.setText("");
                        viewBinding.blockTextInputEditText.setText(postalPincodeResponse.get(0).getPostOffice().get(0).getBlock());
                        viewBinding.districtTextInputEditText.setText(postalPincodeResponse.get(0).getPostOffice().get(0).getDistrict());
                        viewBinding.stateTextInputEditText.setText(postalPincodeResponse.get(0).getPostOffice().get(0).getState());

                        for (int i = 0; i < postalPincodeResponse.size(); i++) {
                            vehicleTypeArraylist = postalPincodeResponse.get(i).getPostOffice();
                            vehicleTypeSearchArrayList.clear();
                            for (PostalPincodeResponse.PostOffice employees1 : vehicleTypeArraylist) {
                                vehicleTypeSearchArrayList.add(new Search(employees1.getDistrict(), employees1.getName()));
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostalPincodeResponse>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }
    private void getSuperHeroes2(String data) {
        postalPincodeApi = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        // Example API call
        Call<ArrayList<PostalPincodeResponse>> call = postalPincodeApi.getPincodeDetails(data);
        call.enqueue(new Callback<ArrayList<PostalPincodeResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PostalPincodeResponse>> call, Response<ArrayList<PostalPincodeResponse>> response) {

                ArrayList<PostalPincodeResponse> postalPincodeResponse = response.body();

                if (postalPincodeResponse != null) {
                    if (postalPincodeResponse.get(0).getPostOffice() != null) {
                        for (int i = 0; i < postalPincodeResponse.size(); i++) {
                            vehicleTypeArraylist = postalPincodeResponse.get(i).getPostOffice();
                            vehicleTypeSearchArrayList.clear();
                            for (PostalPincodeResponse.PostOffice employees1 : vehicleTypeArraylist) {
                                vehicleTypeSearchArrayList.add(new Search(employees1.getDistrict(), employees1.getName()));
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostalPincodeResponse>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }

    private void getCustomerWithId(String customerId, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Customer.Content>> call = apiService.getCustomerWithId(customerId, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Customer.Content>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Customer.Content>> call, Response<ApiResponseObject<Customer.Content>> response) {
                ApiResponseObject<Customer.Content> list = response.body();
                hideProgressDialog();
                if (list.getData() != null) {
                    viewBinding.fullNameTextInputEditText.setText(list.getData().getFullName());
                    viewBinding.mobileNumberTextInputEditText.setText(list.getData().getMobileNumber());
                    viewBinding.genderAppCompatAutoCompleteTextView.setText(list.getData().getGender());

                    if (list.getData().getAddress().size() == 0) {

                    } else {
                        viewBinding.houseNumberTextInputEditText.setText(list.getData().getAddress().get(0).getHouseNumber());
                        viewBinding.streetNameTextInputEditText.setText(list.getData().getAddress().get(0).getStreetName());
                        viewBinding.villageNameTextInputEditText.setText(list.getData().getAddress().get(0).getVillageName());
                        viewBinding.pinCodeTextInputEditText.setText(list.getData().getAddress().get(0).getPostalCode());
                        viewBinding.districtTextInputEditText.setText(list.getData().getAddress().get(0).getDistrict());
                        viewBinding.stateTextInputEditText.setText(list.getData().getAddress().get(0).getState());
                        viewBinding.blockTextInputEditText.setText(list.getData().getAddress().get(0).getBlock());
                        getSuperHeroes2(list.getData().getAddress().get(0).getPostalCode());
                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCustomerActivity.this, android.R.layout.simple_dropdown_item_1line, gender);
                    viewBinding.genderAppCompatAutoCompleteTextView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ApiResponseObject<Customer.Content>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("", "onFailure  =" + call.toString());
                Log.e("", "onFailure  =" + t);

            }
        });
    }

    private void putCustomer(String customerId, RequestBody body, String accept, String authorisation, String token) {
        apiService = RetrofitClient.getRetrofitInstance2().create(ApiService.class);
        // Example API call
        Call<ApiResponseObject<Customer>> call = apiService.putCustomer(customerId, body, accept, authorisation, token);
        call.enqueue(new Callback<ApiResponseObject<Customer>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Customer>> call, Response<ApiResponseObject<Customer>> response) {
                ApiResponseObject<Customer> list = response.body();
                hideProgressDialog();
                finish();
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