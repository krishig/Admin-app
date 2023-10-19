package com.krishigadmin.android.ui.forgetPassword.view;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.RequestUtils;
import com.krishigadmin.android.databinding.ActivityForgetPasswordBinding;
import com.krishigadmin.android.model.User;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.login.viewmodel.LoginViewModel;
import com.krishigadmin.android.ui.verifyotp.view.VerifyOtpActivity;
import com.library.utilities.ValidationUtils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class ForgetPasswordActivity extends BaseActivity<ActivityForgetPasswordBinding> {

    LoginViewModel viewModel;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected ActivityForgetPasswordBinding getViewBinding() {
        return ActivityForgetPasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(ForgetPasswordActivity.this);
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

    }

    @Override
    protected void addTextChangedListener() {

        viewBinding.emailTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() < 1) {
                    viewBinding.emailTextInputLayout.setErrorEnabled(true);
                    viewBinding.emailTextInputLayout.setError(getString(R.string.email_message_one));
                } else if (text.length() > 0) {
                    viewBinding.emailTextInputLayout.setError(null);
                    viewBinding.emailTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int emailValidCode = ValidationUtils.isValidEmail(viewBinding.emailTextInputEditText.getText().toString());
                if (emailValidCode > 0) {
                    if (emailValidCode == 1) {
                        viewBinding.emailTextInputLayout.setError(getString(R.string.email_message_one));
                    } else if (emailValidCode == 2) {
                        viewBinding.emailTextInputLayout.setError(getString(R.string.email_message_two));
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
        Observer<String> userOtpError = new Observer<String>() {
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
        viewModel.userOtpError().observe(this, userOtpError);

        final Observer<User> userOtpSuccessObserver = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                hideProgressDialog();
       /*         Intent intent = new Intent(ForgetPasswordActivity.this, VerifyOtpActivity.class);
                intent.putExtra(AppConstants.Extras.EXTRA_EMAIL, user.getEmail());
                intent.putExtra(AppConstants.Extras.EXTRA_ID, user.getId());
                startActivity(intent);*/
            }
        };
        viewModel.userOtpSuccess().observe(this, userOtpSuccessObserver);

    }

    @Override
    protected void setOnClickListener() {
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

        viewBinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = viewBinding.emailTextInputEditText.getText().toString();
                RequestBody emailRequestBody = RequestUtils.createRequestBodyForString(email);
                if (forgetPasswordValidation(email)) {
               /*     showProgressDialog();
                    viewModel.otpSend(emailRequestBody);*/
                    Intent intent = new Intent(ForgetPasswordActivity.this, VerifyOtpActivity.class);
                    startActivity(intent);
                }


            }
        });
    }

    private boolean forgetPasswordValidation(String email) {
        boolean isValid = true;
        int emailValidCode = ValidationUtils.isValidEmail(email);

        if (emailValidCode > 0) {
            if (emailValidCode == 1) {
                viewBinding.emailTextInputLayout.setError(getString(R.string.email_message_one));
                isValid = false;
            } else if (emailValidCode == 2) {
                viewBinding.emailTextInputLayout.setError(getString(R.string.email_message_two));
                isValid = false;
            }
        } else {
            viewBinding.emailTextInputLayout.setError(null);
            viewBinding.emailTextInputLayout.setErrorEnabled(false);
        }
        return isValid;
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