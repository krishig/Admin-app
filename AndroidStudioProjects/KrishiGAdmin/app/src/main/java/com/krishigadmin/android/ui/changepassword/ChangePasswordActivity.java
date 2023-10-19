package com.krishigadmin.android.ui.changepassword;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.RequestUtils;
import com.krishigadmin.android.databinding.ActivityChangePasswordBinding;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.login.viewmodel.LoginViewModel;
import com.library.utilities.ValidationUtils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding> {
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    LoginViewModel viewModel;
    private String newPassword;
    private String reEnterNewPassword;
    String employeeID;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ActivityChangePasswordBinding getViewBinding() {
        return ActivityChangePasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(ChangePasswordActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void initializeViewModel() {
        viewModel = createViewModel(LoginViewModel.class);
    }

    @Override
    protected void initializeToolBar() {
    }

    @Override
    protected void initializeObject() {
       /* if (getIntent().getExtras() != null) {
            employeeID = getIntent().getExtras().getString(AppConstants.Extras.EXTRA_ID);
        }*/
    }

    @Override
    protected void addTextChangedListener() {
        viewBinding.newPasswordTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

                if (text.length() < 1) {
                    viewBinding.newPasswordTextInputLayout.setErrorEnabled(true);
                    viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_one));
                }
                if (text.length() > 0) {
                    viewBinding.newPasswordTextInputLayout.setError(null);
                    viewBinding.newPasswordTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                int passwordValidCode = ValidationUtils.isValidPassword(viewBinding.newPasswordTextInputEditText.getText().toString());
                if (passwordValidCode > 0) {
                    if (passwordValidCode == 1) {
                        viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_one));
                    }
                    if (passwordValidCode == 2) {
                        viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_two));
                    }
                    if (passwordValidCode == 3) {
                        viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_three));
                    }
                }
            }
        });

        viewBinding.reEnterNewPasswordTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

                if (text.length() < 1) {
                    viewBinding.reEnterNewPasswordTextInputLayout.setErrorEnabled(true);
                    viewBinding.reEnterNewPasswordTextInputLayout.setError(getString(R.string.confirm_password_message_one));
                }
                if (text.length() > 0) {
                    viewBinding.reEnterNewPasswordTextInputLayout.setError(null);
                    viewBinding.reEnterNewPasswordTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                int confirmPasswordValidCode = ValidationUtils.isValidConfirmPassword(viewBinding.newPasswordTextInputEditText.getText().toString().trim(), viewBinding.reEnterNewPasswordTextInputEditText.getText().toString().trim());
                if (confirmPasswordValidCode > 0) {
                    if (confirmPasswordValidCode == 1) {
                        viewBinding.reEnterNewPasswordTextInputLayout.setError(getString(R.string.password_message_one));
                    }
                    if (confirmPasswordValidCode == 2) {
                        viewBinding.reEnterNewPasswordTextInputLayout.setError(getString(R.string.confirm_password_message_one));
                    }
                    if (confirmPasswordValidCode == 3) {
                        viewBinding.reEnterNewPasswordTextInputLayout.setError(getString(R.string.confirm_password_message_two));
                    }
                    if (confirmPasswordValidCode == 4) {
                        viewBinding.reEnterNewPasswordTextInputLayout.setError(getString(R.string.confirm_password_message_three));
                    }
                }
            }
        });
    }

    @Override
    protected void setOnFocusChangeListener() {
    }

    @Override
    protected void observeViewModel() {
  /*      Observer<String> changePasswordErrorObserver = new Observer<String>() {
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
        viewModel.changePasswordError().observe(this, changePasswordErrorObserver);

        final Observer<User> changePasswordSuccessObserver = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                hideProgressDialog();
                Intent intent = ActivityUtils.launchActivityWithClearBackStack(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
        viewModel.changePasswordSuccess().observe(this, changePasswordSuccessObserver);*/
    }

    @Override
    protected void setOnClickListener() {
        viewBinding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        viewBinding.icBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewBinding.scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewBinding.scrollView.post(new Runnable() {
                    public void run() {
                        viewBinding.scrollView.scrollTo(0, viewBinding.scrollView.getBottom() + viewBinding.scrollView.getScrollY());
                    }
                });
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    /***********************************************************************************************
     ********************************************Permission*****************************************
     **********************************************************************************************/

    /***********************************************************************************************
     *******************************************Validations*****************************************
     **********************************************************************************************/
    private boolean changePasswordButtonValidation(String newPassword, String reEnterNewPassword) {
        int passwordValidCode = ValidationUtils.isValidPassword(newPassword);
        int confirmPasswordValidCode = ValidationUtils.isValidConfirmPassword(newPassword, reEnterNewPassword);

        if (passwordValidCode > 0) {
            if (passwordValidCode == 1) {
                viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_one));
                return false;
            } else if (passwordValidCode == 2) {
                viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_two));
                return false;
            } else if (passwordValidCode == 3) {
                viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_three));
                return false;
            } else if (passwordValidCode == 4) {
                viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_four));
                return false;
            } else if (passwordValidCode == 5) {
                viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_five));
                return false;
            } else if (passwordValidCode == 6) {
                viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_six));
                return false;
            } else if (passwordValidCode == 7) {
                viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_seven));
                return false;
            } else if (passwordValidCode == 8) {
                viewBinding.newPasswordTextInputLayout.setError(getString(R.string.password_message_eight));
                return false;
            }
        } else if (confirmPasswordValidCode > 0) {
            if (confirmPasswordValidCode == 1) {
                viewBinding.reEnterNewPasswordTextInputLayout.setError(getString(R.string.password_message_one));
                return false;
            } else if (confirmPasswordValidCode == 2) {
                viewBinding.reEnterNewPasswordTextInputLayout.setError(getString(R.string.confirm_password_message_one));
                return false;
            } else if (confirmPasswordValidCode == 3) {
                viewBinding.reEnterNewPasswordTextInputLayout.setError(getString(R.string.confirm_password_message_two));
                return false;
            } else if (confirmPasswordValidCode == 4) {
                viewBinding.reEnterNewPasswordTextInputLayout.setError(getString(R.string.confirm_password_message_three));
                return false;
            }
        }
        return true;
    }

    /***********************************************************************************************
     *******************************************Open Other Activity*********************************
     *********************************************************************************************
     * @param userType*/
    private void openLoginActivity(String userType) {
  /*      Intent intent = ActivityUtils.launchActivityWithClearBackStack(ChangePasswordActivity.this, HomeActivity.class);
        startActivity(intent);*/
    }

    /***********************************************************************************************
     *******************************************API Call********************************************
     **********************************************************************************************/
    private void changePassword() {
        newPassword = viewBinding.newPasswordTextInputEditText.getText().toString();
        reEnterNewPassword = viewBinding.reEnterNewPasswordTextInputEditText.getText().toString();

        RequestBody requestBodyNewPassword = RequestUtils.createRequestBodyForString(newPassword);
        RequestBody requestBodyReEnterNewPassword = RequestUtils.createRequestBodyForString(reEnterNewPassword);

        if (changePasswordButtonValidation(newPassword, reEnterNewPassword)) {
            if (employeeID != null) {
                RequestBody requestBodyEmployeeID = RequestUtils.createRequestBodyForString(employeeID);
                showProgressDialog();
                // viewModel.changePassword(requestBodyEmployeeID, requestBodyNewPassword, requestBodyReEnterNewPassword);
            }

        }
    }
}